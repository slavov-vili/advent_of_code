package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public static <T> List<T> addPaddingTo(List<T> inputList, int paddingSize, T paddingValue) {
        List<T> outputList = new ArrayList<>(inputList);
        outputList.addAll(Collections.nCopies(paddingSize, paddingValue));
        return outputList;
    }

    public static <T> List<T> cloneList(List<T> listToClone) {
        return new ArrayList<>(listToClone);
    }

    public static <T> List<T> getListElementsAt(List<T> inputList, List<Integer> indices) {
        List<T> outputList = new ArrayList<>(inputList.size());
        for (int idx : indices)
            outputList.add(inputList.get(idx));
        return outputList;
    }
}
