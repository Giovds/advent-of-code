package com.giovds;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static com.giovds.FileUtil.readFile;

public abstract class Day {
    protected boolean printAnswers;
    protected final List<String> inputRows;

    protected abstract Number part1();

    protected abstract Number part2();

    protected Day(final boolean printAnswers, final boolean useExample) {
        this.printAnswers = printAnswers;
        final String fileName = useExample ? "examples" : "values";
        try {
            inputRows = readFile(getClass(), "%s/%s.txt".formatted(getClass().getSimpleName(), fileName));
            run();
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    protected Day() {
        this(true, false);
    }

    public void run() {
        System.out.printf("☕️ JDK %s %s%n", System.getProperty("java.version"), System.getProperty("java.vendor"));
        System.out.printf("💻 %s, %s%n", System.getProperty("os.name"), System.getProperty("os.version"));
        System.out.printf("🎄 %s 🎄%n", getClass().getSimpleName());
        final long startPart1 = System.nanoTime();
        final Number firstAnswer = part1();
        final long endPart1 = System.nanoTime();
        final double timeElapsedPart1 = (endPart1 - startPart1) / 1_000_000.0;
        System.out.printf("⭐️ Part 1 completed in: %.2f ms%n", timeElapsedPart1);

        final long startPart2 = System.nanoTime();
        final Number secondAnswer = part2();
        final long endPart2 = System.nanoTime();
        final double timeElapsedPart2 = (endPart2 - startPart2) / 1_000_000.0;
        System.out.printf("⭐️ Part 2 completed in: %.2f ms%n", timeElapsedPart2);
        System.out.printf("⏳ Total time: %.2f ms%n%n", (timeElapsedPart1 + timeElapsedPart2));

        if (printAnswers) {
            System.out.printf("✔️ Answer 1: %s%n", firstAnswer);
            System.out.printf("✔️ Answer 2: %s%n", secondAnswer);
        }
    }
}