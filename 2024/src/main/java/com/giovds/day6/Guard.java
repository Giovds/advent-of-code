package com.giovds.day6;

record Guard(Position position, PatrolDirection direction) {
    static PatrolDirection nextDirection(final PatrolDirection direction) {
        return switch (direction) {
            case UP -> PatrolDirection.RIGHT;
            case RIGHT -> PatrolDirection.DOWN;
            case DOWN -> PatrolDirection.LEFT;
            case LEFT -> PatrolDirection.UP;
        };
    }

    Position getNextPosition() {
        return new Position(position.x() + direction.rowOffset(),
                position.y() + direction.columnOffset());
    }
}
