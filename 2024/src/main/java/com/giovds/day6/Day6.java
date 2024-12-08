package com.giovds.day6;

import com.giovds.Day;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day6 extends Day {
    public static void main(String[] args) {
        new Day6();
    }

    @Override
    protected Number part1() {
        final List<String> rows = inputRows;
        final char[][] map = new char[rows.size()][rows.getFirst().length()];
        final List<Position> obstaclePositions = new ArrayList<>();

        Guard guard = null;
        for (int i = 0; i < rows.size(); i++) {
            map[i] = rows.get(i).toCharArray();
            final String startDirection = PatrolDirection.UP.directionIndicator();
            if (rows.get(i).contains(startDirection)) {
                guard = new Guard(new Position(i, rows.get(i).indexOf(startDirection)), PatrolDirection.getDirection(startDirection));
            }
            if (rows.get(i).contains("#")) {
                for (int j = 0; j < rows.get(i).length(); j++) {
                    if (rows.get(i).charAt(j) == '#') {
                        obstaclePositions.add(new Position(i, j));
                    }
                }
            }
        }

        boolean guardLeftArea = false;
        final Set<Position> visited = new HashSet<>();
        while (!guardLeftArea) {
            final PatrolDirection currentDirection = guard.direction();
            final Position nextPosition = guard.getNextPosition();

            if (obstaclePositions.contains(nextPosition)) {
                guard = new Guard(guard.position(), Guard.nextDirection(currentDirection));
            } else {
                visited.add(guard.position());
                guard = new Guard(nextPosition, currentDirection);
            }

            if (leftArea(guard.position(), map)) {
                guardLeftArea = true;
            }
        }

        return visited.size();
    }

    private static boolean leftArea(final Position position, final char[][] map) {
        return position.x() < 0 || position.x() >= map.length ||
                position.y() < 0 || position.y() >= map[0].length;
    }

    @Override
    protected Number part2() {
        final List<String> rows = inputRows;
        final char[][] map = new char[rows.size()][rows.getFirst().length()];
        final List<Position> possibleObstaclePositions = new ArrayList<>();

        Guard guard = null;
        for (int i = 0; i < rows.size(); i++) {
            map[i] = rows.get(i).toCharArray();
            final String startDirection = PatrolDirection.UP.directionIndicator();
            if (rows.get(i).contains(startDirection)) {
                guard = new Guard(new Position(i, rows.get(i).indexOf(startDirection)), PatrolDirection.getDirection(startDirection));
            }
            for (int j = 0; j < rows.get(i).length(); j++) {
                if (rows.get(i).charAt(j) == '.') {
                    possibleObstaclePositions.add(new Position(i, j));
                }
            }
        }

        int sum = 0;
        for (final Position obstacleCandidate : possibleObstaclePositions) {
            map[obstacleCandidate.x()][obstacleCandidate.y()] = '#';

            if (walksInCircles(map, guard)) {
                sum++;
            }

            map[obstacleCandidate.x()][obstacleCandidate.y()] = '.';
        }

        return sum;
    }

    private static boolean walksInCircles(final char[][] map, final Guard startingState) {
        final Set<Guard> visitedStates = new HashSet<>();
        Guard guard = startingState;

        while (true) {
            if (visitedStates.contains(guard)) {
                return true;
            }
            visitedStates.add(guard);

            final PatrolDirection currentDirection = guard.direction();
            final Position nextPosition = guard.getNextPosition();

            if (leftArea(nextPosition, map)) {
                return false;
            }

            if (map[nextPosition.x()][nextPosition.y()] == '#') {
                guard = new Guard(guard.position(), Guard.nextDirection(currentDirection));
            } else {
                guard = new Guard(nextPosition, currentDirection);
            }
        }
    }

}
