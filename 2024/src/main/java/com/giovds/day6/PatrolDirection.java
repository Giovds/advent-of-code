package com.giovds.day6;

import java.util.Arrays;

enum PatrolDirection {
    RIGHT(0, 1, ">"),
    LEFT(0, -1, "<"),
    DOWN(1, 0, "v"),
    UP(-1, 0, "^");

    private final int rowOffset;
    private final int columnOffset;
    private final String directionChar;

    PatrolDirection(final int rowOffset, final int columnOffset, final String directionChar) {
        this.rowOffset = rowOffset;
        this.columnOffset = columnOffset;
        this.directionChar = directionChar;
    }

    int rowOffset() {
        return rowOffset;
    }

    int columnOffset() {
        return columnOffset;
    }

    String directionIndicator() {
        return directionChar;
    }

    static PatrolDirection getDirection(final String direction) {
        return Arrays.stream(values())
                .filter(patrolDirection -> patrolDirection.directionChar.equals(direction))
                .findFirst()
                .orElseThrow();
    }
}