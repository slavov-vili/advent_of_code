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

    /*
     * Finds all permutations of the given list of elements.
     * 
     * The method follows Heap's algorithm (aka recursively finds the permutations
     * of the first k-1 elements).
     */
    public static <T> List<List<T>> findPermutations(int permutationSize, List<T> curPermutation,
            List<List<T>> curPermutationsList) {
        List<List<T>> newPermutationsList = new ArrayList<>(curPermutationsList);
        if (permutationSize == 1) {
            newPermutationsList.add(curPermutation);
            return newPermutationsList;
        }

        // Adds all permutations where the last element is untouched
        newPermutationsList = findPermutations(permutationSize - 1, curPermutation, curPermutationsList);

        List<T> nextPermutation = curPermutation;

        for (int i = 0; i < permutationSize - 1; i++) {
            nextPermutation = swap(nextPermutation, i, permutationSize - 1);
            newPermutationsList = findPermutations(permutationSize - 1, nextPermutation, newPermutationsList);
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
    
    public static <T> int countInMatrix(List<List<T>> matrix, T valueToCount) {
        int count = 0;
        for (List<T> row : matrix)
            for (T curValue : row)
                if (curValue.equals(valueToCount))
                    count++;
        
        return count;
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

    public static <T> T getLastElement(List<T> inputList) {
        return inputList.get(inputList.size() - 1);
    }
}
