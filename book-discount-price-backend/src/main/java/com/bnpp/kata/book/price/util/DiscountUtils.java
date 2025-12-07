package com.bnpp.kata.book.price.util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public final class DiscountUtils {

    private static final double BOOK_PRICE = 50.0;

    private static final Map<Integer, Double> DISCOUNTS = Map.of(
            1, 0.00,
            2, 0.05,
            3, 0.10,
            4, 0.20,
            5, 0.25
    );

    private DiscountUtils() {}

    public static double computeOptimalPrice(List<Integer> counts) {
        return compute(counts, new HashMap<>());
    }

    private static double compute(List<Integer> counts, Map<String, Double> cache) {

        List<Integer> normalized = normalize(counts);

        if (normalized.isEmpty()) return 0.0;

        String key = normalized.toString();

        if (cache.containsKey(key)) return cache.get(key);

        double best = IntStream.rangeClosed(1, normalized.size())
                .mapToDouble(size -> computeCost(size, normalized, cache))
                .min()
                .orElse(Double.MAX_VALUE);

        cache.put(key, best);
        return best;
    }

    private static List<Integer> normalize(List<Integer> counts) {
        return counts.stream()
                .filter(c -> c > 0)
                .sorted(Comparator.reverseOrder())
                .toList();
    }

    private static double computeCost(int groupSize, List<Integer> state, Map<String, Double> cache) {

        List<Integer> nextState = IntStream.range(0, state.size())
                .map(i -> i < groupSize ? state.get(i) - 1 : state.get(i))
                .boxed()
                .toList();

        double discount = DISCOUNTS.getOrDefault(groupSize, 0.0);
        double groupCost = groupSize * BOOK_PRICE * (1 - discount);

        return groupCost + compute(nextState, cache);
    }
}
