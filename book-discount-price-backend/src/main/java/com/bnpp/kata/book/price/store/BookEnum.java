package com.bnpp.kata.book.price.store;


public enum BookEnum {

    CLEAN_CODE(1, "Clean Code", "Robert Martin", 2008, 50.0),
    THE_CLEAN_CODER(2, "The Clean Coder", "Robert Martin", 2011, 50.0),
    CLEAN_ARCHITECTURE(3, "Clean Architecture", "Robert Martin", 2017, 50.0),
    TDD_BY_EXAMPLE(4, "Test Driven Development by Example", "Kent Beck", 2003, 50.0),
    LEGACY_CODE(5, "Working Effectively with Legacy Code", "Michael Feathers", 2004, 50.0);

    public final int id;
    public final String title;
    public final String author;
    public final int year;
    public final double price;

    BookEnum(int id, String title, String author, int year, double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.price = price;
    }
}