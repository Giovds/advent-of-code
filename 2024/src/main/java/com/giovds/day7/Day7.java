package com.giovds.day7;

import com.giovds.Day;

import java.util.Arrays;

public class Day7 extends Day {
    public static void main(String[] args) {
        new Day7();
    }

    @Override
    protected Number part1() {
        long sum = 0;
        for (final String line : inputRows) {
            final String[] split = line.split(": ");
            long testValue = Long.parseLong(split[0]);
            long[] numbers = Arrays.stream(split[1].split(" ")).mapToLong(Long::parseLong).toArray();

            if (combinationOfOperationsIsTestValue(numbers, 0, numbers[0], testValue, false)) {
                sum += testValue;
            }
        }

        return sum;
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

    @Override
    protected Number part2() {
        long sum = 0;
        for (final String line : inputRows) {
            final String[] split = line.split(": ");
            long testValue = Long.parseLong(split[0]);
            long[] numbers = Arrays.stream(split[1].split(" ")).mapToLong(Long::parseLong).toArray();

            if (combinationOfOperationsIsTestValue(numbers, 0, numbers[0], testValue, true)) {
                sum += testValue;
            }
        }

        return sum;
    }

    private static long concatenate(final long currentValue, final long number) {
        return Long.parseLong(String.format("%d%d", currentValue, number));
    }
}
