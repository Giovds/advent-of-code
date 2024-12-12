package com.giovds.day11;

import java.util.List;

record Stone(long value) {
    List<Stone> getNextStones() {
        if (value == 0) {
            return List.of(new Stone(1));
        }

        final String stringValue = String.valueOf(value);
        if (stringValue.length() % 2 == 0) {
            final int half = stringValue.length() / 2;
            return List.of(
                    new Stone(Long.parseLong(stringValue.substring(0, half))),
                    new Stone(Long.parseLong(stringValue.substring(half)))
            );
        }

        return List.of(new Stone(value * 2024));
    }
}
