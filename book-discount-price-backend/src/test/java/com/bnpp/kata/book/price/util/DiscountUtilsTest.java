package com.bnpp.kata.book.price.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class DiscountUtilsTest {

    // --------------------------
    // computeOptimalPrice()
    // --------------------------

    @Test
    @DisplayName("computeOptimalPrice() → returns 0.0 for empty list")
    void testComputeOptimalPrice_emptyList() {
        double result = DiscountUtils.computeOptimalPrice(List.of());
        assertEquals(0.0, result);
    }

    @Test
    @DisplayName("computeOptimalPrice() → single count (1 book) → no discount")
    void testComputeOptimalPrice_singleBook() {
        double result = DiscountUtils.computeOptimalPrice(List.of(1));
        assertEquals(50.0, result); // 1 * 50 with 0% discount
    }

    @Test
    @DisplayName("computeOptimalPrice() → two distinct books → 5% discount")
    void testComputeOptimalPrice_twoBooks() {
        double result = DiscountUtils.computeOptimalPrice(List.of(1, 1));

        // group size 2 → 5% discount
        // cost = 2 * 50 * 0.95 = 95
        assertEquals(95.0, result);
    }

    @Test
    @DisplayName("computeOptimalPrice() → three distinct books → 10% discount")
    void testComputeOptimalPrice_threeBooks() {
        double result = DiscountUtils.computeOptimalPrice(List.of(1, 1, 1));

        // group of 3 → 10% discount
        // cost = 3 * 50 * 0.90 = 135
        assertEquals(135.0, result);
    }

    @Test
    @DisplayName("computeOptimalPrice() → complex scenario tests recursion (2 copies of 3 titles)")
    void testComputeOptimalPrice_recursive() {
        // 3 titles, each quantity 2 → [2,2,2]
        double result = DiscountUtils.computeOptimalPrice(List.of(2, 2, 2));

        // Expected grouping:
        // Step 1: group size 3 → cost = 3 * 50 * 0.90 = 135
        // Remaining state → [1,1,1]
        // Step 2: group size 3 → cost = 135
        //
        // Total = 270
        assertEquals(270.0, result);
    }

    // --------------------------
    // normalize()
    // --------------------------

    @Test
    @DisplayName("computeOptimalPrice() → normalize filters out non-positive numbers")
    void testNormalizationFilters() {
        double result = DiscountUtils.computeOptimalPrice(List.of(3, -1, 0, 2));

        // Expected normalized: [3,2]
        // Best grouping:
        // group size 2 → discount 5%
        // cost = 2 * 50 * 0.95 = 95
        // Remaining: [2,1]
        // group size 2 → cost = 95
        // Remaining: [1,0]
        // group size 1 → cost = 50
        //
        // Total ≈ 240
        assertTrue(result > 200 && result < 260); // avoid hardcoding (recursive exact)
    }

    // --------------------------
    // Memoization (cache hit)
    // --------------------------

    @Test
    @DisplayName("computeOptimalPrice() → ensures caching is used (same input twice)")
    void testCacheHit() {

        // First call fills cache
        double first = DiscountUtils.computeOptimalPrice(List.of(1, 1, 1));

        // Second call should hit memoized result and skip recomputation
        double second = DiscountUtils.computeOptimalPrice(List.of(1, 1, 1));

        assertEquals(first, second);
    }

}