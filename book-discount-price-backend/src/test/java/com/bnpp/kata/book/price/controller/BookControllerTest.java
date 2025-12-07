package com.bnpp.kata.book.price.controller;

import com.bnpp.kata.book.price.dto.BookPriceResponse;
import com.bnpp.kata.book.price.dto.BookResponse;
import com.bnpp.kata.book.price.exception.InvalidBookException;
import com.bnpp.kata.book.price.service.BookService;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @Test
    @DisplayName("GET /api/books/getbooks → returns 200 OK and all books")
    void testGetBooksSuccess() throws Exception {

        when(bookService.getAllBooks()).thenReturn(
                List.of(
                        new BookResponse(1, "Clean Code", "Robert Martin", 2008, 50.0),
                        new BookResponse(2, "The Clean Coder", "Robert Martin", 2011, 50.0),
                        new BookResponse(3, "Clean Architecture", "Robert Martin", 2017, 50.0),
                        new BookResponse(4, "Test Driven Development by Example", "Kent Beck", 2003, 50.0),
                        new BookResponse(5, "Working Effectively with Legacy Code", "Michael Feathers", 2004, 50.0)
                )
        );

        mockMvc.perform(get("/api/books/getbooks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(jsonPath("$[0].title").value("Clean Code"))
                .andExpect(jsonPath("$[4].title").value("Working Effectively with Legacy Code"));
    }

    @Test
    @DisplayName("POST /api/books/price/calculate → returns 200 OK with correct totalPrice")
    void testCalculatePriceSuccess() throws Exception {

        // Mock service response
          Mockito.when(bookService.calculatePrice(anyList()))
                 .thenReturn(new BookPriceResponse(List.of(), 100.0,95.0));

        // Create request JSON
        String requestJson = """
                {
                   "bookList": [
                       { "title": "Clean Code", "quantity": 1 },
                       { "title": "The Clean Coder", "quantity": 1 }
                   ]
                }
                """;

        // Perform request & validate response
        mockMvc.perform(
                        post("/api/books/price/calculate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.discountPrice").value(95.0));
    }

    @Test
    @DisplayName("POST /api/books/price/calculate → returns 400 Bad Request when bookList is missing")
    void testCalculatePrice_missingBookList() throws Exception {

        // Service should throw when receiving null bookList
        Mockito.when(bookService.calculatePrice(null))
                .thenThrow(new IllegalArgumentException("bookList must not be null"));

        String badRequestJson = """
        {
            "unknownField": "invalid"
        }
        """;

        mockMvc.perform(post("/api/books/price/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(badRequestJson))
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("POST /api/books/price/calculate → returns 400 Bad Request when quantity is negative")
    void testCalculatePrice_negativeQuantity() throws Exception {

        Mockito.when(bookService.calculatePrice(anyList()))
                .thenThrow(new IllegalArgumentException("Quantity must be >= 0"));

        String invalidJson = """
        {
          "bookList": [
            { "title": "Clean Code", "quantity": -1 }
          ]
        }
        """;

        mockMvc.perform(post("/api/books/price/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/books/price/calculate → Handle MethodArgumentNotValidException")
    void testHandleValidationException() throws Exception {

        Mockito.when(bookService.calculatePrice(anyList()))
                .thenThrow(new IllegalArgumentException("Validation failed"));

        String json = """
      {
        "bookList": [
          { "title": "", "quantity": 1 }
        ]
      }
      """;

        mockMvc.perform(post("/api/books/price/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation failed"));
    }

    @Test
    void testHandleInvalidBookException() throws Exception {
        when(bookService.calculatePrice(anyList()))
                .thenThrow(new InvalidBookException("Qty invalid"));

        String json = """
      { "bookList": [ { "title": "Clean Code", "quantity": -5 } ] }
      """;

        mockMvc.perform(post("/api/books/price/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Qty invalid"));
    }
}