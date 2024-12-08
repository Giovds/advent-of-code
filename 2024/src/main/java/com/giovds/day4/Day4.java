package com.giovds.day4;

import com.giovds.Day;

import java.util.List;

public class Day4 extends Day {
    private final static String XMAS = "XMAS";
    private final static String XMAS_DIAGONAL = "MAS";
    private final static String XMAS_DIAGONAL_REVERSE = new StringBuilder(XMAS_DIAGONAL).reverse().toString();

    public static void main(String[] args) {
        new Day4();
    }

    @Override
    protected Number part1() {
        final String[] rows = inputRows.toArray(String[]::new);

        int sum = 0;
        for (int row = 0; row < rows.length; row++) {
            for (int column = 0; column < rows[row].length(); column++) {
                for (final var direction : SearchDirection.values()) {
                    if (searchXmasInDirection(rows, row, column, direction)) {
                        sum++;
                    }
                }
            }
        }

        return sum;
    }

    private static boolean searchXmasInDirection(final String[] grid, final int startRow, final int startCol,
                                                 final SearchDirection direction) {
        int rows = grid.length;
        int cols = grid[0].length();

        for (int xmasIndex = 0; xmasIndex < XMAS.length(); xmasIndex++) {
            int nextLetterRow = startRow + xmasIndex * direction.rowOffset();
            int nextLetterColumn = startCol + xmasIndex * direction.columnOffset();

            if (isOutOfBounds(nextLetterRow, rows, nextLetterColumn, cols)) {
                return false;
            }

            char nextLetter = grid[nextLetterRow].charAt(nextLetterColumn);
            char nextCorrectLetter = XMAS.charAt(xmasIndex);
            if (!letterMatchesWord(nextLetter, nextCorrectLetter)) {
                return false;
            }
        }

        return true;
    }

    private static boolean letterMatchesWord(final char nextLetter, final char validNextLetter) {
        return nextLetter == validNextLetter;
    }

    private static boolean isOutOfBounds(final int rowIndex, final int rows,
                                         final int columnIndex, final int columns) {
        return rowIndex < 0 || rowIndex >= rows || columnIndex < 0 || columnIndex >= columns;
    }

    @Override
    protected Number part2() {
        final String[] rows = inputRows.toArray(String[]::new);

        int sum = 0;
        // Iterate over 3x3 grids, starting in the center
        for (int row = 1; row < rows.length - 1; row++) {
            for (int column = 1; column < rows[row].length() - 1; column++) {
                if (isXMASPattern(rows, row, column)) {
                    sum++;
                }
            }
        }

        return sum;
    }

    private static boolean isXMASPattern(final String[] grid, final int startRow, final int startCol) {
        final char center = grid[startRow].charAt(startCol);
        if (center != 'A') {
            return false;
        }

        final String topLeftToBottomRight =
                "" + grid[startRow - 1].charAt(startCol - 1) + center + grid[startRow + 1].charAt(startCol + 1);
        final String topRightToBottomLeft =
                "" + grid[startRow - 1].charAt(startCol + 1) + center + grid[startRow + 1].charAt(startCol - 1);

        final List<String> diagonals = List.of(topLeftToBottomRight, topRightToBottomLeft);
        return List.of(XMAS_DIAGONAL, XMAS_DIAGONAL_REVERSE).containsAll(diagonals);
    }
}
