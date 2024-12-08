package com.giovds.day5;

import com.giovds.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day5 extends Day {
    public static void main(String[] args) {
        new Day5();
    }

    @Override
    protected Number part1() {
        final List<UpdateWithRules> updatesWithRules = parseUpdatesWithRules(inputRows);

        int sum = 0;
        for (final UpdateWithRules updatesWithRule : updatesWithRules) {
            final Update update = updatesWithRule.update();
            final List<Rule> rulesToApply = updatesWithRule.rules();
            if (updateIsCorrect(update, rulesToApply)) {
                sum += getMiddlePage(update);
            }
        }
        return sum;
    }

    private static int getMiddlePage(final Update update) {
        final int middleIndex = update.pages().size() / 2;
        return Integer.parseInt(update.pages().get(middleIndex));
    }

    private static boolean updateIsCorrect(final Update update, final List<Rule> rulesToApply) {
        return rulesToApply.stream()
                .allMatch(rule -> update.pages().indexOf(rule.firstPage()) < update.pages().indexOf(rule.secondPage()));
    }

    private static List<UpdateWithRules> parseUpdatesWithRules(final List<String> strings) {
        final List<Rule> allRules = new ArrayList<>();
        final List<Update> allUpdates = new ArrayList<>();

        boolean doneReadingRules = false;
        for (final String string : strings) {
            if (string.isEmpty()) {
                doneReadingRules = true;
                continue;
            }

            if (doneReadingRules) {
                allUpdates.add(createUpdateFromInput(string));
            } else {
                allRules.add(createRuleFromInput(string));
            }
        }

        return allUpdates.stream()
                .map(update -> new UpdateWithRules(
                        update,
                        allRules.stream()
                                .filter(rule -> ruleMatchesWithUpdate(update, rule))
                                .toList()
                ))
                .toList();
    }

    private static Update createUpdateFromInput(final String updateString) {
        return new Update(Arrays.asList(updateString.split(",")));
    }

    private static Rule createRuleFromInput(final String ruleString) {
        final String[] pages = ruleString.split("\\|");
        return new Rule(pages[0], pages[1]);
    }

    private static boolean ruleMatchesWithUpdate(final Update update, final Rule rule) {
        return update.pages().contains(rule.firstPage()) && update.pages().contains(rule.secondPage());
    }

    @Override
    protected Number part2() {
        final List<UpdateWithRules> updatesWithRules = parseUpdatesWithRules(inputRows);

        int sum = 0;
        for (final UpdateWithRules updatesWithRule : updatesWithRules) {
            final Update update = updatesWithRule.update();
            final List<Rule> rulesToApply = updatesWithRule.rules();
            if (!updateIsCorrect(update, rulesToApply)) {
                final Update correctedUpdate = createCorrectedUpdate(update, rulesToApply);
                sum += getMiddlePage(correctedUpdate);
            }
        }
        return sum;
    }

    private static Update createCorrectedUpdate(final Update update, final List<Rule> rulesToApply) {
        List<String> correctedPages = update.pages()
                .stream()
                .sorted((page1, page2) -> {
                    for (final Rule rule : rulesToApply) {
                        if (matchesRule(page1, page2, rule)) {
                            // page1 should be before page2
                            return -1;
                        }
                        if (matchesRule(page2, page1, rule)) {
                            // page1 should be after page2
                            return 1;
                        }
                    }
                    // page1 and page2 do not have any rule and are therefore equal
                    return 0;
                })
                .toList();
        return new Update(correctedPages);
    }

    private static boolean matchesRule(final String firstPage,
                                       final String secondPage,
                                       final Rule rule) {
        return rule.firstPage().equals(firstPage) && rule.secondPage().equals(secondPage);
    }
}
