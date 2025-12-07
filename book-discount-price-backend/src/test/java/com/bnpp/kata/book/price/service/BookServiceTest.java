package com.bnpp.kata.book.price.service;

import com.bnpp.kata.book.price.dto.Book;
import com.bnpp.kata.book.price.dto.BookPriceResponse;
import com.bnpp.kata.book.price.dto.BookResponse;
import com.bnpp.kata.book.price.exception.InvalidBookException;
import com.bnpp.kata.book.price.mapper.BookMapper;
import com.bnpp.kata.book.price.store.BookEnum;
import com.bnpp.kata.book.price.util.BookUtils;
import com.bnpp.kata.book.price.util.DiscountUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class BookServiceTest {

    @Mock
    private BookMapper mapper;

    @InjectMocks
    private BookService service;

    private AutoCloseable closeable;

    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    // ------------------------------------------------------
    // 1. getAllBooks()
    // ------------------------------------------------------
    @Test
    @DisplayName("getAllBooks() → maps all 5 enum items using mapper")
    void testGetAllBooks() {

        // arrange
        for (BookEnum bookEnum : BookEnum.values()) {
            when(mapper.toResponse(bookEnum))
                    .thenReturn(new BookResponse(
                            bookEnum.id,
                            bookEnum.title,
                            bookEnum.author,
                            bookEnum.year,
                            bookEnum.price
                    ));
        }

        // act
        List<BookResponse> result = service.getAllBooks();

        // assert
        assertEquals(5, result.size());
        verify(mapper, times(5)).toResponse(any(BookEnum.class));
    }

    // ------------------------------------------------------
    // 2. calculatePrice() – Valid basket
    // ------------------------------------------------------
    @Test
    @DisplayName("calculatePrice() → successful flow returns mergedBooks, totalPrice, discountPrice")
    void testCalculatePrice_success() {

        List<Book> input = List.of(
                new Book("Clean Code", 1),
                new Book("The Clean Coder", 1)
        );

        Map<String, Integer> merged = Map.of(
                "clean code", 1,
                "the clean coder", 1
        );

        List<Integer> sortedCounts = List.of(1, 1);

        try (MockedStatic<BookUtils> bookUtils = mockStatic(BookUtils.class);
             MockedStatic<DiscountUtils> discountUtils = mockStatic(DiscountUtils.class)) {

            // 1. Validate basket
            bookUtils.when(() -> BookUtils.validateBasket(input))
                    .thenAnswer(inv -> null);

            // 2. Merge duplicates
            bookUtils.when(() -> BookUtils.mergeDuplicateTitles(input))
                    .thenReturn(merged);

            // 3. Extract sorted counts
            bookUtils.when(() -> BookUtils.extractSortedCounts(merged))
                    .thenReturn(sortedCounts);

            // 4. Discount engine response
            discountUtils.when(() -> DiscountUtils.computeOptimalPrice(sortedCounts))
                    .thenReturn(95.0);

            BookPriceResponse response = service.calculatePrice(input);

            // Expected total price = 2 * 50 = 100
            assertEquals(100.0, response.totalPrice());
            assertEquals(95.0, response.discountPrice());
            assertEquals(2, response.bookList().size());
        }
    }

    // ------------------------------------------------------
    // 3. calculatePrice() – sortedCounts is empty
    // ------------------------------------------------------
    @Test
    @DisplayName("calculatePrice() → throws InvalidBookException when sortedCounts is empty")
    void testCalculatePrice_emptySortedCounts() {

        List<Book> input = List.of(new Book("Clean Code", 1));

        try (MockedStatic<BookUtils> bookUtils = mockStatic(BookUtils.class)) {

            // Validation should pass
            bookUtils.when(() -> BookUtils.validateBasket(input))
                    .thenAnswer(inv -> null);

            // Merge result
            bookUtils.when(() -> BookUtils.mergeDuplicateTitles(input))
                    .thenReturn(Map.of("clean code", 1));

            // Sorted counts → EMPTY → Should trigger exception
            bookUtils.when(() -> BookUtils.extractSortedCounts(anyMap()))
                    .thenReturn(List.of());

            assertThrows(
                    InvalidBookException.class,
                    () -> service.calculatePrice(input)
            );
        }
    }


    // ------------------------------------------------------
    // 4. calculatePrice() → handles BookUtils validation exception correctly
    // ------------------------------------------------------
    @Test
    @DisplayName("calculatePrice() → propagates validation exceptions thrown by BookUtils")
    void testCalculatePrice_validationError() {

        List<Book> input = List.of(new Book("", -1)); // invalid

        try (MockedStatic<BookUtils> bookUtils = mockStatic(BookUtils.class)) {

            bookUtils.when(() -> BookUtils.validateBasket(input))
                    .thenThrow(new InvalidBookException("Invalid basket"));

            assertThrows(
                    InvalidBookException.class,
                    () -> service.calculatePrice(input)
            );
        }
    }
}