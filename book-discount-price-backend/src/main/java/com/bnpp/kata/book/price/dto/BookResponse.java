package com.bnpp.kata.book.price.dto;

public record BookResponse(int id,
                           String title,
                           String author,
                           int year,
                           double price) {
}
