package com.bnpp.kata.book.price.dto;

import java.util.List;

public record BookBasketRequest(
       List<Book> bookList
) {
}
