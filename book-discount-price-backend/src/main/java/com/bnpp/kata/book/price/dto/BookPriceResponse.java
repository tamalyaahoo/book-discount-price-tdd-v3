package com.bnpp.kata.book.price.dto;

import java.util.List;

public record BookPriceResponse(
        List<Book> bookList,
        double totalPrice,
        double discountPrice
) {
}
