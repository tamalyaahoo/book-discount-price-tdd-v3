package com.bnpp.kata.book.price.controller;

import com.bnpp.kata.book.price.dto.BookBasketRequest;
import com.bnpp.kata.book.price.dto.BookPriceResponse;
import com.bnpp.kata.book.price.dto.BookResponse;
import com.bnpp.kata.book.price.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Book API", description = "APIs for fetching development books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/getbooks")
    @Operation(summary = "Fetch all books", description = "Returns all development books stored in enum")
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PostMapping("/price/calculate")
    public ResponseEntity<BookPriceResponse> calculatePrice(@RequestBody BookBasketRequest request) {
        BookPriceResponse bookPriceResponse = bookService.calculatePrice(request.bookList());
        return ResponseEntity.ok(bookPriceResponse);
    }
}
