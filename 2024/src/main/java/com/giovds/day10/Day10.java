package com.giovds.day10;

import com.giovds.Day;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Stream;

public class Day10 extends Day {
    public static void main(String[] args) {
        new Day10();
    }

    @Override
    protected Number part1() {
        final int[][] grid = new int[inputRows.size()][inputRows.getFirst().length()];
        final List<Position> trailStartingPositions = new ArrayList<>();
        for (int i = 0; i < inputRows.size(); i++) {
            final String row = inputRows.get(i);
            for (int j = 0; j < row.length(); j++) {
                final int number = Character.getNumericValue(row.charAt(j));
                if (number == 0) {
                    trailStartingPositions.add(new Position(i, j));
                }
                grid[i][j] = number;
            }
        }

        return trailStartingPositions.stream()
                .mapToInt(start -> amountOfTrails(grid, start))
                .sum();
    }

    private int amountOfTrails(final int[][] grid, final Position start) {
        final Set<Position> visited = new HashSet<>();
        final Queue<Position> positionsLeft = new ArrayDeque<>();
        positionsLeft.add(start);
        visited.add(start);

        int sum = 0;
        while (!positionsLeft.isEmpty()) {
            final Position current = positionsLeft.poll();
            if (current == null) break;

            if (grid[current.x()][current.y()] == 9) {
                sum++;
                continue;
            }

            final List<Position> adjacentPositions = getAdjacentPositions(grid, current);
            for (final Position adjacent : adjacentPositions) {
                if (!visited.contains(adjacent) && isValidNextPosition(grid, adjacent, current)) {
                    visited.add(adjacent);
                    positionsLeft.add(adjacent);
                }
            }
        }

        return sum;
    }

    @Override
    protected Number part2() {
        final int[][] grid = new int[inputRows.size()][inputRows.getFirst().length()];
        final List<Position> trailStartingPositions = new ArrayList<>();
        for (int i = 0; i < inputRows.size(); i++) {
            final String row = inputRows.get(i);
            for (int j = 0; j < row.length(); j++) {
                final int number = Character.getNumericValue(row.charAt(j));
                if (number == 0) {
                    trailStartingPositions.add(new Position(i, j));
                }
                grid[i][j] = number;
            }
        }

        return trailStartingPositions.stream()
                .mapToInt(start -> amountOfDistinctTrails(grid, start))
                .sum();
    }

    private int amountOfDistinctTrails(final int[][] grid, final Position start) {
        final Queue<List<Position>> paths = new ArrayDeque<>();
        paths.add(List.of(start));

        int sum = 0;
        while (!paths.isEmpty()) {
            final List<Position> currentPath = paths.poll();
            final Position current = currentPath.getLast();

            if (grid[current.x()][current.y()] == 9) {
                sum++;
                continue;
            }

            final List<Position> adjacentPositions = getAdjacentPositions(grid, current);
            for (final Position adjacent : adjacentPositions) {
                if (!currentPath.contains(adjacent) && isValidNextPosition(grid, adjacent, current)) {
                    final List<Position> newPath = new ArrayList<>(currentPath);
                    newPath.addLast(adjacent);
                    paths.add(newPath);
                }
            }
        }

        return sum;
    }


    private static boolean isValidNextPosition(final int[][] grid, final Position position, final Position current) {
        return grid[position.x()][position.y()] == grid[current.x()][current.y()] + 1;
    }

    private List<Position> getAdjacentPositions(final int[][] grid, final Position position) {
        return Stream.of(position.neighborUp(), position.neighborDown(), position.neighborLeft(), position.neighborRight())
                .filter(p -> isOutOfBounds(grid, p))
                .toList();
    }

    private static boolean isOutOfBounds(final int[][] grid, final Position position) {
        return position.x() >= 0 && position.x() < grid.length && position.y() >= 0 && position.y() < grid[0].length;
    }
}