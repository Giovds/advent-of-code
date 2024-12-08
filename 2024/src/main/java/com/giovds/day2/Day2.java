package com.giovds.day2;

import com.giovds.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day2 extends Day {
    public static void main(String... args) {
        new Day2();
    }

    @Override
    protected Number part1() {
        final List<List<Integer>> reports = getReports();

        return reports.stream()
                .filter(Day2::isSafe)
                .count();
    }

    @Override
    protected Number part2() {
        final List<List<Integer>> reports = getReports();

        int safeReportCounter = 0;
        for (final List<Integer> report : reports) {
            if (isSafe(report)) {
                safeReportCounter++;
            } else if (canBeSafeByRemovingOneLevel(report)) {
                safeReportCounter++;
            }
        }

        return safeReportCounter;
    }

    private static boolean canBeSafeByRemovingOneLevel(final List<Integer> report) {
        for (int i = 0; i < report.size(); i++) {
            List<Integer> modifiedReport = new ArrayList<>(report);
            modifiedReport.remove(i);
            if (isSafe(modifiedReport)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isSafe(final List<Integer> report) {
        boolean isIncreasing = false;
        boolean isDecreasing = false;
        for (int i = 0; i < report.size(); i++) {
            if (i == 0) {
                continue;
            }
            final int diff = report.get(i) - report.get(i - 1);
            final int absoluteDiff = Math.abs(diff);
            if (absoluteDiff < 1 || absoluteDiff > 3) {
                return false;
            }

            if (diff > 0) {
                isIncreasing = true;
            }
            if (diff < 0) {
                isDecreasing = true;
            }

            if (isIncreasing && isDecreasing) {
                return false;
            }
        }

        return true;
    }

    private List<List<Integer>> getReports() {
        return inputRows.stream()
                .map(report -> report.split(" "))
                .map(reportsAsString -> Arrays.stream(reportsAsString).mapToInt(Integer::parseInt).boxed().toList())
                .toList();
    }

}
