package de.cas.adventofcode.shared;

import java.util.Arrays;
import java.util.stream.IntStream;

public class ArrayUtils {
    public static char[][] copy2DArray(final char[][] in) {
        return Arrays.stream(in)
                .map(chars -> Arrays.copyOf(chars, chars.length))
                .toArray(char[][]::new);
    }

    public static boolean are2DArraysEqual(final char[][] a, final char[][] b) {
        return IntStream.range(0, a.length)
                .allMatch(i -> Arrays.equals(a[i], b[i]));
    }
}
