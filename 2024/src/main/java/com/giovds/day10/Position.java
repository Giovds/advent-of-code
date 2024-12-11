package com.giovds.day10;

record Position(int x, int y) {
    Position neighborUp() {
        return new Position(x - 1, y);
    }

    Position neighborDown() {
        return new Position(x + 1, y);
    }

    Position neighborLeft() {
        return new Position(x, y - 1);
    }

    Position neighborRight() {
        return new Position(x, y + 1);
    }
}
