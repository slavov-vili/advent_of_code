package utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class GenerateRangeTest {
    @Test
    void generateRange_sameValueZeroTest() {
        Integer valueToAdd = 0;
        List<Integer> expected = Arrays.asList(valueToAdd);
        List<Integer> actual = ListUtils.generateRange(valueToAdd, valueToAdd);

        assertEquals(expected, actual,
                "When the first and last values are the same, the range should only consist of that value!");
    }

    @Test
    void generateRange_sameValueNegativeTest() {
        Integer valueToAdd = -100;
        List<Integer> expected = Arrays.asList(valueToAdd);
        List<Integer> actual = ListUtils.generateRange(valueToAdd, valueToAdd);

        assertEquals(expected, actual,
                "When the first and last values are the same, the range should only consist of that value!");
    }

    @Test
    void generateRange_sameValuePositiveTest() {
        Integer valueToAdd = 20;
        List<Integer> expected = Arrays.asList(valueToAdd);
        List<Integer> actual = ListUtils.generateRange(valueToAdd, valueToAdd);

        assertEquals(expected, actual,
                "When the first and last values are the same, the range should only consist of that value!");
    }

    @Test
    void generateRange_positiveIncreasingFromZeroTest() {
        Integer startInclusive = 0;
        Integer endInclusive = 5;
        List<Integer> expected = Arrays.asList(0, 1, 2, 3, 4, 5);
        List<Integer> actual = ListUtils.generateRange(startInclusive, endInclusive);

        assertEquals(expected, actual);
    }

    @Test
    void generateRange_positiveIncreasingFromIntTest() {
        Integer startInclusive = 1;
        Integer endInclusive = 5;
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> actual = ListUtils.generateRange(startInclusive, endInclusive);

        assertEquals(expected, actual);
    }

    @Test
    void generateRange_positiveDecreasingToZeroTest() {
        Integer startInclusive = 5;
        Integer endInclusive = 0;
        List<Integer> expected = Arrays.asList(5, 4, 3, 2, 1, 0);
        List<Integer> actual = ListUtils.generateRange(startInclusive, endInclusive);

        assertEquals(expected, actual);
    }

    @Test
    void generateRange_positiveDecreasingToIntTest() {
        Integer startInclusive = 5;
        Integer endInclusive = 1;
        List<Integer> expected = Arrays.asList(5, 4, 3, 2, 1);
        List<Integer> actual = ListUtils.generateRange(startInclusive, endInclusive);

        assertEquals(expected, actual);
    }

    @Test
    void generateRange_negativeDecreasingFromZeroTest() {
        Integer startInclusive = 0;
        Integer endInclusive = -5;
        List<Integer> expected = Arrays.asList(0, -1, -2, -3, -4, -5);
        List<Integer> actual = ListUtils.generateRange(startInclusive, endInclusive);

        assertEquals(expected, actual);
    }

    @Test
    void generateRange_negativeDecreasingFromIntTest() {
        Integer startInclusive = -1;
        Integer endInclusive = -5;
        List<Integer> expected = Arrays.asList(-1, -2, -3, -4, -5);
        List<Integer> actual = ListUtils.generateRange(startInclusive, endInclusive);

        assertEquals(expected, actual);
    }

    @Test
    void generateRange_negativeIncreasingToZeroTest() {
        Integer startInclusive = -5;
        Integer endInclusive = 0;
        List<Integer> expected = Arrays.asList(-5, -4, -3, -2, -1, 0);
        List<Integer> actual = ListUtils.generateRange(startInclusive, endInclusive);

        assertEquals(expected, actual);
    }

    @Test
    void generateRange_negativeIncreasingToIntTest() {
        Integer startInclusive = -5;
        Integer endInclusive = -1;
        List<Integer> expected = Arrays.asList(-5, -4, -3, -2, -1);
        List<Integer> actual = ListUtils.generateRange(startInclusive, endInclusive);

        assertEquals(expected, actual);
    }

    @Test
    void generateRange_negativeToPositiveTest() {
        Integer startInclusive = -3;
        Integer endInclusive = 3;
        List<Integer> expected = Arrays.asList(-3, -2, -1, 0, 1, 2, 3);
        List<Integer> actual = ListUtils.generateRange(startInclusive, endInclusive);

        assertEquals(expected, actual);
    }

    @Test
    void generateRange_PositiveToNegativeTest() {
        Integer startInclusive = 3;
        Integer endInclusive = -3;
        List<Integer> expected = Arrays.asList(3, 2, 1, 0, -1, -2, -3);
        List<Integer> actual = ListUtils.generateRange(startInclusive, endInclusive);

        assertEquals(expected, actual);
    }
}
