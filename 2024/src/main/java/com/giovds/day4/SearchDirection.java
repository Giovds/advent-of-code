package com.giovds.day4;

enum SearchDirection {
    RIGHT(0, 1),
    LEFT(0, -1),
    DOWN(1, 0),
    UP(-1, 0),
    DOWN_RIGHT(1, 1),
    DOWN_LEFT(1, -1),
    UP_RIGHT(-1, 1),
    UP_LEFT(-1, -1);

    private final int rowOffset;
    private final int columnOffset;

    SearchDirection(int rowOffset, int columnOffset) {
        this.rowOffset = rowOffset;
        this.columnOffset = columnOffset;
    }

    int rowOffset() {
        return rowOffset;
    }

    int columnOffset() {
        return columnOffset;
    }
}