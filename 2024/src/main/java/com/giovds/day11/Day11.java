package com.giovds.day11;

import com.giovds.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day11 extends Day {
    public static void main(String[] args) {
        new Day11();
    }

    @Override
    protected Number part1() {
        return blink(25);
    }

    private int bruteForce() {
        List<Stone> stones = Arrays.stream(inputRows.getFirst()
                        .split(" "))
                .map(Long::parseLong)
                .map(Stone::new)
                .toList();

        for (int i = 0; i < 25; i++) {
            final List<Stone> newStones = new ArrayList<>();
            for (final Stone stone : stones) {
                final List<Stone> nextStones = stone.getNextStones();
                newStones.addAll(nextStones);
            }
            stones = newStones;
        }

        return stones.size();
    }

    @Override
    protected Number part2() {
        return blink(75);
    }

    private long blink(final int iterations) {
        Map<Stone, Long> stoneCount = Arrays.stream(inputRows.getFirst()
                        .split(" "))
                .map(Long::parseLong)
                .map(Stone::new)
                .collect(Collectors.toMap(stone -> stone, _ -> 1L, Long::sum));

        for (int i = 0; i < iterations; i++) {
            final Map<Stone, Long> newStoneCount = new HashMap<>();
            for (final var entry : stoneCount.entrySet()) {
                final Stone stone = entry.getKey();
                long count = entry.getValue();
                for (final Stone nextStone : stone.getNextStones()) {
                    newStoneCount.merge(nextStone, count, Long::sum);
                }
            }
            stoneCount = newStoneCount;
        }

        return stoneCount.values().stream().mapToLong(Long::longValue).sum();
    }
}