package com.bnpp.kata.book.price.service;

import com.bnpp.kata.book.price.dto.Book;
import com.bnpp.kata.book.price.dto.BookPriceResponse;
import com.bnpp.kata.book.price.dto.BookResponse;
import com.bnpp.kata.book.price.exception.InvalidBookException;
import com.bnpp.kata.book.price.mapper.BookMapper;
import com.bnpp.kata.book.price.store.BookEnum;
import com.bnpp.kata.book.price.util.BookUtils;
import com.bnpp.kata.book.price.util.DiscountUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
public class BookService {

    private static final double BOOK_PRICE = 50.0;

    private final BookMapper mapper;

    public BookService(BookMapper mapper) {
        this.mapper = mapper;
    }

    public List<BookResponse> getAllBooks() {
        return Stream.of(BookEnum.values())
                .map(mapper :: toResponse)
                .toList();
    }

    public BookPriceResponse calculatePrice(List<Book> bookList) {
        BookUtils.validateBasket(bookList);

        Map<String, Integer> merged = BookUtils.mergeDuplicateTitles(bookList);
        List<Integer> sortedCounts = BookUtils.extractSortedCounts(merged);

        if (sortedCounts.isEmpty()) {
            throw new InvalidBookException("Basket must contain at least one book with quantity > 0");
        }

        double discountPrice = DiscountUtils.computeOptimalPrice(sortedCounts);
        double totalPrice = merged.values().stream()
                .mapToInt(Integer::intValue)
                .sum() * BOOK_PRICE;

        List<Book> mergedBooks = merged.entrySet().stream()
                .map(entry -> new Book(entry.getKey(), entry.getValue()))
                .toList();

        return new BookPriceResponse(mergedBooks, totalPrice, discountPrice);
    }
}
