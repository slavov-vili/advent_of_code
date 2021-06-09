package de.cas.adventofcode.shared;

import java.util.List;
import java.util.stream.Collectors;

public class ConversionUtils {

    public static List<Integer> listStringToListInt(final List<String> listString) {
        return listString.stream().map(Integer::parseInt).collect(Collectors.toList());
    }
}
