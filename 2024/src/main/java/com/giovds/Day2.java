package com.giovds;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.giovds.FileUtil.readFile;

public class Day2 {
    public static void main(final String... args) throws IOException, URISyntaxException {
        part1();
        part2();
    }

    private static void part1() throws IOException, URISyntaxException {
        final List<List<Integer>> reports = getReports();

        long safeReportCounter = reports.stream()
                .filter(Day2::isSafe)
                .count();
        System.out.println(safeReportCounter);
    }

    private static void part2() throws IOException, URISyntaxException {
        final List<List<Integer>> reports = getReports();

        int safeReportCounter = 0;
        for (final List<Integer> report : reports) {
            if (isSafe(report)) {
                safeReportCounter++;
            } else if (canBeSafeByRemovingOneLevel(report)) {
                safeReportCounter++;
            }
        }

        System.out.println(safeReportCounter);
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

    private static List<List<Integer>> getReports() throws IOException, URISyntaxException {
        final List<String> reports = readFile(Day2.class, "day2/values.txt");
        return reports.stream()
                .map(report -> report.split(" "))
                .map(reportsAsString -> Arrays.stream(reportsAsString).mapToInt(Integer::parseInt).boxed().toList())
                .toList();
    }

}
