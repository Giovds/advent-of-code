package com.giovds.day7;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static com.giovds.FileUtil.readFile;

public class Day7 {
    public static void main(String[] args) throws IOException, URISyntaxException {
        part1(false);
        part2(true);
    }

    private static void part1(final boolean useConcatenation) throws IOException, URISyntaxException {
        final List<String> equations = readFile(Day7.class, "day7/values.txt");

        long sum = 0;
        for (final String line : equations) {
            final String[] split = line.split(": ");
            long testValue = Long.parseLong(split[0]);
            long[] numbers = Arrays.stream(split[1].split(" ")).mapToLong(Long::parseLong).toArray();

            if (combinationOfOperationsIsTestValue(numbers, 0, numbers[0], testValue, useConcatenation)) {
                sum += testValue;
            }
        }

        System.out.println(sum);
    }

    private static boolean combinationOfOperationsIsTestValue(final long[] numbers, final int index, final long currentValue, final long testValue, boolean useConcatenation) {
        if (index == numbers.length - 1) {
            return currentValue == testValue;
        }

        if (useConcatenation &&
                combinationOfOperationsIsTestValue(numbers, index + 1, concatenate(currentValue, numbers[index + 1]), testValue, useConcatenation)) {
            return true;
        }

        if (combinationOfOperationsIsTestValue(numbers, index + 1, currentValue + numbers[index + 1], testValue, useConcatenation)) {
            return true;
        }

        return combinationOfOperationsIsTestValue(numbers, index + 1, currentValue * numbers[index + 1], testValue, useConcatenation);
    }

    private static void part2(final boolean useConcatenation) throws IOException, URISyntaxException {
        part1(useConcatenation);
    }

    private static long concatenate(final long currentValue, final long number) {
        return Long.parseLong(String.format("%d%d", currentValue, number));
    }
}
