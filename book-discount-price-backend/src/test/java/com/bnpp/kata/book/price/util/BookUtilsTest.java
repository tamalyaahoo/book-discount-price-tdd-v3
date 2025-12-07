package com.bnpp.kata.book.price.util;

import com.bnpp.kata.book.price.dto.Book;
import com.bnpp.kata.book.price.exception.InvalidBookException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class BookUtilsTest {

    @Test
    @DisplayName("extractSortedCounts() → filters out null values")
    void testExtractSortedCounts_nullValue() {
        Map<String, Integer> input = new HashMap<>();
        input.put("clean code", null);
        List<Integer> result = BookUtils.extractSortedCounts(input);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("extractSortedCounts() → filters out zero or negative values")
    void testExtractSortedCounts_zeroOrNegative() {
        Map<String, Integer> input = new HashMap<>();
        input.put("clean code", 0);
        input.put("tdd", -3);

        List<Integer> result = BookUtils.extractSortedCounts(input);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("extractSortedCounts() → returns sorted positive values")
    void testExtractSortedCounts_positiveValues() {
        Map<String, Integer> input = new HashMap<>();
        input.put("a", 1);
        input.put("b", 4);
        input.put("c", 2);
        List<Integer> result = BookUtils.extractSortedCounts(input);
        assertEquals(List.of(4, 2, 1), result);
    }

    // --------------------------
    // normalizeTitle()
    // --------------------------
    @Test
    @DisplayName("normalizeTitle() → trims and lowercases")
    void testNormalizeTitle() {
        assertEquals("clean code", BookUtils.normalizeTitle("  Clean Code  "));
    }

    // --------------------------
    // mergeDuplicateTitles()
    // --------------------------
    @Test
    @DisplayName("mergeDuplicateTitles() → merges case-insensitive titles")
    void testMergeDuplicateTitles() {
        List<Book> books = List.of(
                new Book("Clean Code", 1),
                new Book("clean code", 2),
                new Book(" CLEAN CODE ", 3)
        );

        Map<String, Integer> result = BookUtils.mergeDuplicateTitles(books);

        assertEquals(1, result.size());
        assertEquals(6, result.get("clean code"));
    }

    // --------------------------
    // extractSortedCounts()
    // --------------------------
    @Test
    @DisplayName("extractSortedCounts() → filters null and non-positive values")
    void testExtractSortedCounts_filtersCorrectly() {
        Map<String, Integer> input = new HashMap<>();
        input.put("a", 3);
        input.put("b", null);
        input.put("c", 0);
        input.put("d", -5);
        input.put("e", 2);
        List<Integer> result = BookUtils.extractSortedCounts(input);
        assertEquals(List.of(3, 2), result);
    }

    // --------------------------
    // validateBasket() — null basket
    // --------------------------
    @Test
    @DisplayName("validateBasket() → throws when basket is null")
    void testValidateBasket_nullList() {
        assertThrows(InvalidBookException.class, () ->
                BookUtils.validateBasket(null));
    }

    // --------------------------
    // validateBasket() — empty basket
    // --------------------------
    @Test
    @DisplayName("validateBasket() → throws when basket is empty")
    void testValidateBasket_emptyList() {
        assertThrows(InvalidBookException.class, () ->
                BookUtils.validateBasket(Collections.emptyList()));
    }

    // --------------------------
    // validateBasket() — null title
    // --------------------------
    @Test
    @DisplayName("validateBasket() → throws when title is null")
    void testValidateBasket_nullTitle() {
        List<Book> items = List.of(new Book(null, 1));

        InvalidBookException ex = assertThrows(
                InvalidBookException.class,
                () -> BookUtils.validateBasket(items)
        );

        assertEquals("Book title must not be null or empty", ex.getMessage());
    }

    // --------------------------
    // validateBasket() — blank title
    // --------------------------
    @Test
    @DisplayName("validateBasket() → throws when title is blank")
    void testValidateBasket_blankTitle() {
        List<Book> items = List.of(new Book("   ", 1));

        InvalidBookException ex = assertThrows(
                InvalidBookException.class,
                () -> BookUtils.validateBasket(items)
        );

        assertEquals("Book title must not be null or empty", ex.getMessage());
    }

    // --------------------------
    // validateBasket() — negative quantity
    // --------------------------
    @Test
    @DisplayName("validateBasket() → throws when quantity is negative")
    void testValidateBasket_negativeQuantity() {
        List<Book> items = List.of(new Book("Clean Code", -1));

        InvalidBookException ex = assertThrows(
                InvalidBookException.class,
                () -> BookUtils.validateBasket(items)
        );

        assertEquals("Quantity for book 'Clean Code' must not be negative", ex.getMessage());
    }

    // --------------------------
    // validateBasket() — no positive quantities
    // --------------------------
    @Test
    @DisplayName("validateBasket() → throws when all quantities are zero")
    void testValidateBasket_noPositiveQuantity() {
        List<Book> items = List.of(
                new Book("Clean Code", 0),
                new Book("TDD", 0)
        );

        InvalidBookException ex = assertThrows(
                InvalidBookException.class,
                () -> BookUtils.validateBasket(items)
        );

        assertEquals("Basket must contain at least one book with quantity > 0", ex.getMessage());
    }

    // --------------------------
    // validateBasket() — valid basket
    // --------------------------
    @Test
    @DisplayName("validateBasket() → passes for valid items")
    void testValidateBasket_valid() {
        List<Book> items = List.of(
                new Book("Clean Code", 1),
                new Book("TDD", 0)
        );

        assertDoesNotThrow(() -> BookUtils.validateBasket(items));
    }
}