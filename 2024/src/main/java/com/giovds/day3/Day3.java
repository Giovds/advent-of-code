package com.giovds.day3;

import com.giovds.Day;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 extends Day {
    private final static Pattern multiplyPattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");

    public static void main(String[] args) {
        new Day3();
    }

    @Override
    protected Number part1() {
        return inputRows.stream()
                .flatMap(memory -> multiplyPattern.matcher(memory).results())
                .mapToInt(Day3::multiplyMatches)
                .sum();
    }

    private static int multiplyMatches(final MatchResult result) {
        final int firstNumber = Integer.parseInt(result.group(1));
        final int secondNumber = Integer.parseInt(result.group(2));
        return firstNumber * secondNumber;
    }

    @Override
    protected Number part2() {
        int sum = 0;
        boolean enableMultiplier = true;
        for (final String memory : inputRows) {
            final StringBuilder buffer = new StringBuilder();
            for (final char nextChar : memory.toCharArray()) {
                buffer.append(nextChar);
                final String currentValue = buffer.toString();

                if (containsEnablePattern(currentValue)) {
                    enableMultiplier = true;
                    clearBuffer(buffer);
                    continue;
                }

                if (containsDisablePattern(currentValue)) {
                    enableMultiplier = false;
                    clearBuffer(buffer);
                    continue;
                }

                if (enableMultiplier) {
                    final Matcher matcher = multiplyPattern.matcher(currentValue);
                    if (matcher.find()) {
                        sum += multiplyMatches(matcher.toMatchResult());
                        clearBuffer(buffer);
                    }
                }
            }
        }
        return sum;
    }

    private static void clearBuffer(final StringBuilder buffer) {
        buffer.setLength(0);
    }

    private static boolean containsEnablePattern(final String buffer) {
        return buffer.contains("do()");
    }

    private static boolean containsDisablePattern(final String buffer) {
        return buffer.contains("don't()");
    }
}
