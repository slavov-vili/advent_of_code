package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ListUtils {
    public static List<Integer> generateRange(int startInclusive, int endInclusive) {
        List<Integer> range = new ArrayList<>();
        int increment = -IntegerUtils.compareInts(startInclusive, endInclusive);
        int endExclusive = endInclusive + increment;

        int curValue = startInclusive;
        do {
            range.add(curValue);
            curValue += increment;
        } while (curValue != endExclusive);

        return range;
    }

    public static <T> List<T> cloneList(List<T> listToClone) {
        return new ArrayList<>(listToClone);
    }

    public static <T> Stream<T> getListElementsAt(List<T> inputList, List<Integer> indices) {
        return indices.stream().map(idx -> inputList.get(idx));
    }
}
