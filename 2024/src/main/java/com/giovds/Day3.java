package com.giovds;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.giovds.FileUtil.readFile;

public class Day3 {
    private final static Pattern multiplyPattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");

    public static void main(String... args) throws IOException, URISyntaxException {
        part1();
        part2();
    }

    private static void part1() throws IOException, URISyntaxException {
        final List<String> corruptedMemory = readFile(Day3.class, "day3/values.txt");

        final int sum = corruptedMemory.stream()
                .flatMap(memory -> multiplyPattern.matcher(memory).results())
                .mapToInt(Day3::multiplyMatches)
                .sum();

        System.out.println(sum);
    }

    private static int multiplyMatches(final MatchResult result) {
        final int firstNumber = Integer.parseInt(result.group(1));
        final int secondNumber = Integer.parseInt(result.group(2));
        return firstNumber * secondNumber;
    }

    private static void part2() throws IOException, URISyntaxException {
        final List<String> corruptedMemory = readFile(Day3.class, "day3/values.txt");

        int sum = 0;
        boolean enableMultiplier = true;
        for (final String memory : corruptedMemory) {
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
        System.out.println(sum);
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
