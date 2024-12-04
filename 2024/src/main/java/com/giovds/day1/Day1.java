package com.giovds.day1;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static com.giovds.FileUtil.readFile;

public class Day1 {
    public static void main(final String... args) throws IOException, URISyntaxException {
        part1();
        part2();
    }

    private static void part1() throws IOException, URISyntaxException {
        final List<String> lines = readFile(Day1.class, "day1/values.txt");
        final List<Integer> leftList = new ArrayList<>();
        final List<Integer> rightList = new ArrayList<>();

        lines.stream()
                .map(line -> line.split(" "))
                .map(Day1::removeEmptyEntries)
                .filter(entry -> entry.length == 2)
                .forEach(result -> {
                    leftList.add(Integer.parseInt(result[0]));
                    rightList.add(Integer.parseInt(result[1]));
                });

        leftList.sort(Comparator.naturalOrder());
        rightList.sort(Comparator.naturalOrder());

        int sum = IntStream.range(0, rightList.size())
                .map(i -> calculateAbsoluteDiff(rightList.get(i), leftList.get(i)))
                .sum();

        System.out.println(sum);
    }

    private static int calculateAbsoluteDiff(int first, int second) {
        return Math.abs(first - second);
    }

    private static void part2() throws IOException, URISyntaxException {
        final List<String> lines = readFile(Day1.class, "day1/values.txt");
        final List<Integer> leftList = new ArrayList<>();
        final Map<Integer, Integer> numberCountByNumber = new HashMap<>();

        lines.stream()
                .map(line -> line.split(" "))
                .map(Day1::removeEmptyEntries)
                .filter(entry -> entry.length == 2)
                .forEach(entries -> {
                    leftList.add(Integer.parseInt(entries[0]));
                    final int nextValue = numberCountByNumber.getOrDefault(Integer.parseInt(entries[1]), 0) + 1;
                    numberCountByNumber.put(Integer.parseInt(entries[1]), nextValue);
                });

        int sum = leftList.stream()
                .filter(numberCountByNumber::containsKey)
                .mapToInt(i -> calculateOccurrence(numberCountByNumber.get(i), i))
                .sum();

        System.out.println(sum);
    }

    private static int calculateOccurrence(final int occurrence, final int number) {
        return occurrence * number;
    }

    private static String[] removeEmptyEntries(final String[] s) {
        return Arrays.stream(s).filter(str -> !str.isEmpty()).toArray(String[]::new);
    }

}