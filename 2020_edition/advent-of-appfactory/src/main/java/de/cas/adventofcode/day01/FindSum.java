package de.cas.adventofcode.day01;

import java.util.List;

public class FindSum {
    public static int findSumOfTwo(final List<Integer> lines) {
        for (int i = 0; i < lines.size(); i++) {
            for (int j = lines.size() - 1; j > 0; j--) {
                final int a = lines.get(i);
                final int b = lines.get(j);

                if (a + b == 2020) {
                    return a * b;
                }
            }
        }

        throw new RuntimeException("Cannot find sum.");
    }

    public static int findSumOfThree(final List<Integer> lines) {
        for (int i = 0; i < lines.size(); i++) {
            for (int j = lines.size() - 1; j > 0; j--) {
                for (int n = lines.size() - 1; n > 0; n--) {
                    final int a = lines.get(i);
                    final int b = lines.get(j);
                    final int c = lines.get(n);

                    if (a + b + c == 2020) {
                        return a * b * c;
                    }
                }
            }
        }

        throw new RuntimeException("Cannot find sum.");
    }
}
