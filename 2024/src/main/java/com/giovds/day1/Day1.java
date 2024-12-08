package com.giovds.day1;

import com.giovds.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Day1 extends Day {

    public static void main(String... args) {
        new Day1();
    }

    @Override
    protected Number part1() {
        final List<Integer> leftList = new ArrayList<>();
        final List<Integer> rightList = new ArrayList<>();

        inputRows.stream()
                .map(line -> line.split(" "))
                .map(Day1::removeEmptyEntries)
                .filter(entry -> entry.length == 2)
                .forEach(result -> {
                    leftList.add(Integer.parseInt(result[0]));
                    rightList.add(Integer.parseInt(result[1]));
                });

        leftList.sort(Comparator.naturalOrder());
        rightList.sort(Comparator.naturalOrder());

        return IntStream.range(0, rightList.size())
                .map(i -> calculateAbsoluteDiff(rightList.get(i), leftList.get(i)))
                .sum();
    }

    private static int calculateAbsoluteDiff(int first, int second) {
        return Math.abs(first - second);
    }

    @Override
    protected Number part2() {
        final List<Integer> leftList = new ArrayList<>();
        final Map<Integer, Integer> numberCountByNumber = new HashMap<>();

        inputRows.stream()
                .map(line -> line.split(" "))
                .map(Day1::removeEmptyEntries)
                .filter(entry -> entry.length == 2)
                .forEach(entries -> {
                    leftList.add(Integer.parseInt(entries[0]));
                    final int nextValue = numberCountByNumber.getOrDefault(Integer.parseInt(entries[1]), 0) + 1;
                    numberCountByNumber.put(Integer.parseInt(entries[1]), nextValue);
                });

        return leftList.stream()
                .filter(numberCountByNumber::containsKey)
                .mapToInt(i -> calculateOccurrence(numberCountByNumber.get(i), i))
                .sum();
    }

    private static int calculateOccurrence(final int occurrence, final int number) {
        return occurrence * number;
    }

    private static String[] removeEmptyEntries(final String[] s) {
        return Arrays.stream(s).filter(str -> !str.isEmpty()).toArray(String[]::new);
    }

}