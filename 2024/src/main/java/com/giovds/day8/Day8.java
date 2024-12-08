package com.giovds.day8;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static com.giovds.FileUtil.readFile;

public class Day8 {
    public static void main(String[] args) throws IOException, URISyntaxException {
        part1();
        part2();
    }

    private static void part1() throws IOException, URISyntaxException {
        final List<String> strings = readFile(Day8.class, "day8/values.txt");
        final int xBound = strings.getFirst().length();
        final int yBound = strings.size();

        final List<Antenna> antennas = getAntennas(strings);
        final Set<Position> antinodes = new HashSet<>();
        for (final Antenna antenna : antennas) {
            for (final Antenna otherAntenna : antennas) {
                if (antenna.equals(otherAntenna) || antenna.frequency() != otherAntenna.frequency()) {
                    continue;
                }

                final Position antinode = getAntinodePosition(antenna, otherAntenna);
                if (isInBounds(antinode.x(), xBound, antinode.y(), yBound)) {
                    antinodes.add(antinode);
                }
            }
        }

        System.out.println(antinodes.size());
    }

    private static List<Antenna> getAntennas(final List<String> strings) {
        return strings.stream()
                .flatMap(row -> IntStream.range(0, row.length())
                        .filter(column -> row.charAt(column) != '.')
                        .mapToObj(column -> new Antenna(new Position(column, strings.indexOf(row)), row.charAt(column))))
                .toList();
    }

    private static Position getAntinodePosition(final Antenna antenna, final Antenna otherAntenna) {
        final int xDiff = Math.abs(antenna.position().x() - otherAntenna.position().x());
        final int yDiff = Math.abs(antenna.position().y() - otherAntenna.position().y());
        final int xDirection = antenna.position().x() < otherAntenna.position().x() ? 1 : -1;
        final int yDirection = antenna.position().y() < otherAntenna.position().y() ? 1 : -1;

        return new Position(
                (xDiff * xDirection) + otherAntenna.position().x(),
                (yDiff * yDirection) + otherAntenna.position().y()
        );
    }

    private static boolean isInBounds(final int x, final int xMax, final int y, final int yMax) {
        return x >= 0 && x < xMax && y >= 0 && y < yMax;
    }

    private static void part2() throws IOException, URISyntaxException {
        final List<String> strings = readFile(Day8.class, "day8/values.txt");
        final int xBound = strings.getFirst().length();
        final int yBound = strings.size();

        final List<Antenna> antennas = getAntennas(strings);
        final Set<Position> antinodes = new HashSet<>();
        for (final Antenna antenna : antennas) {
            for (final Antenna otherAntenna : antennas) {
                if (antenna.equals(otherAntenna) || antenna.frequency() != otherAntenna.frequency()) {
                    continue;
                }

                final Set<Position> antinodePositions = getAntinodePositions(antenna, otherAntenna, xBound, yBound);
                antinodes.addAll(antinodePositions);
                antinodes.add(antenna.position());
                antinodes.add(otherAntenna.position());
            }
        }

        System.out.println(antinodes.size());
    }

    private static Set<Position> getAntinodePositions(final Antenna antenna, final Antenna otherAntenna, final int xBound, final int yBound) {
        final int xDiff = Math.abs(antenna.position().x() - otherAntenna.position().x());
        final int yDiff = Math.abs(antenna.position().y() - otherAntenna.position().y());
        final int xDirection = antenna.position().x() < otherAntenna.position().x() ? 1 : -1;
        final int yDirection = antenna.position().y() < otherAntenna.position().y() ? 1 : -1;

        final Set<Position> positions = new HashSet<>();

        int nextXPosition = (xDiff * xDirection) + otherAntenna.position().x();
        int nextYPosition = (yDiff * yDirection) + otherAntenna.position().y();
        while (isInBounds(nextXPosition, xBound, nextYPosition, yBound)) {
            positions.add(new Position(nextXPosition, nextYPosition));
            nextXPosition = (xDiff * xDirection) + nextXPosition;
            nextYPosition = (yDiff * yDirection) + nextYPosition;
        }
        return positions;
    }
}