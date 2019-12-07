package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    /*
     * Finds all permutations of the given list of elements.
     * 
     * The method follows Heap's algorithm (aka recursively finds the permutations
     * of the first k-1 elements).
     */
    public static <T> List<List<T>> findPermutations(int permutationEndExclusive, List<T> curPermutation,
            List<List<T>> curPermutationsList) {
        List<List<T>> newPermutationsList = new ArrayList<>(curPermutationsList);
        if (permutationEndExclusive == 1) {
            newPermutationsList.add(curPermutation);
            return newPermutationsList;
        }

        // Adds all permutations where the last element is untouched
        newPermutationsList = findPermutations(permutationEndExclusive-1, curPermutation, curPermutationsList);

        List<T> nextPermutation = curPermutation;

        for (int i = 0; i < permutationEndExclusive - 1; i++) { 
            nextPermutation = swap(nextPermutation, i, permutationEndExclusive - 1);
            newPermutationsList = findPermutations(permutationEndExclusive - 1, nextPermutation, newPermutationsList);
        }

        return newPermutationsList;
    }

    public static <T> List<T> swap(List<T> inputList, int firstIndex, int secondIndex) {
        List<T> swappedList = new ArrayList<>(inputList);
        T temp = swappedList.get(firstIndex);
        swappedList.set(firstIndex, swappedList.get(secondIndex));
        swappedList.set(secondIndex, temp);
        return swappedList;
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
