package day04;

import java.util.stream.Stream;

import utils.IntegerUtils;
import utils.StringUtils;

public class PasswordUtils {
    private static final int PASSWORD_LENGTH = 6;
    private static final Integer RANGE_START = 246515;
    private static final Integer RANGE_END = 739105;
    private static final int CONSECUTIVE_DIGIT_COUNT = 2;

    public static Stream<Integer> filterForPartA(Stream<Integer> passwords) {
        return filterByBaseRequirements(passwords).filter(IntegerUtils::hasSequenceOfSameDigits)
                .filter(PasswordUtils::isAlwaysIncreasing);
    }

    public static Stream<Integer> filterForPartB(Stream<Integer> passwords) {
        return filterByBaseRequirements(passwords)
                .filter(password -> IntegerUtils.hasSequenceOfSameDigits(password, CONSECUTIVE_DIGIT_COUNT))
                .filter(PasswordUtils::isAlwaysIncreasing);
    }

    protected static Stream<Integer> filterByBaseRequirements(Stream<Integer> passwords) {
        return passwords.filter(password -> IntegerUtils.checkIntegerLength(password, PASSWORD_LENGTH))
                .filter(password -> IntegerUtils.integerIsWithinRange(password, RANGE_START, RANGE_END));
    }

    public static boolean isAlwaysIncreasing(Integer password) {
        return StringUtils.isAlwaysIncreasing(String.valueOf(password));
    }
}
