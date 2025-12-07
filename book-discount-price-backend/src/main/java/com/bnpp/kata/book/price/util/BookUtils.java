package com.bnpp.kata.book.price.util;

import com.bnpp.kata.book.price.dto.Book;
import com.bnpp.kata.book.price.exception.InvalidBookException;

import java.util.*;
import java.util.stream.Collectors;

public final class BookUtils {

    private BookUtils() {
    }

    public static String normalizeTitle(String title) {
        return title.trim().toLowerCase();
    }

    public static Map<String, Integer> mergeDuplicateTitles(List<Book> items) {
        return items.stream()
                .collect(Collectors.toMap(
                        item -> normalizeTitle(item.title()),
                        Book::quantity,
                        Integer::sum
                ));
    }

    public static List<Integer> extractSortedCounts(Map<String, Integer> merged) {
        return merged.values().stream()
                .filter(Objects::nonNull)
                .filter(qty -> qty > 0)
                .sorted(Comparator.reverseOrder())
                .toList();
    }

    public static void validateBasket(List<Book> items) {

        List<Book> safeItems = Optional.ofNullable(items)
                .orElseThrow(() -> new InvalidBookException("Basket must not be null"));

        Optional.of(safeItems)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new InvalidBookException("Basket must contain at least one entry"));

        safeItems.forEach(item -> {
            String title = Optional.ofNullable(item.title())
                    .map(String::trim)
                    .filter(name -> !name.isEmpty())
                    .orElseThrow(() -> new InvalidBookException("Book title must not be null or empty"));

            int qty = item.quantity();

            if (qty < 0)
                throw new InvalidBookException("Quantity for book '%s' must not be negative".formatted(title));
        });

        safeItems.stream()
                .map(Book::quantity)
                .filter(qty -> qty > 0)
                .findAny()
                .orElseThrow(() ->
                        new InvalidBookException("Basket must contain at least one book with quantity > 0"));
    }
}
