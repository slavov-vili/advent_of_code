package utils;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class StringUtils {
    public static Stream<String> getAllMatches(String inputString, String regex) {
        Matcher matcher = Pattern.compile(regex).matcher(inputString);
        return matcher.results().map(MatchResult::group);
    }

    public static boolean hasSequenceOfSameChars(String stringToCheck, String sequenceOfSameCharsPattern) {
        return stringToCheck.matches(".*" + sequenceOfSameCharsPattern + ".*");
    }

    public static boolean hasSequenceOfSameChars(String stringToCheck, String sequenceOfSameCharsPattern,
            int sequenceLength) {
        return StringUtils.getAllMatches(stringToCheck, sequenceOfSameCharsPattern)
                .anyMatch(seq -> seq.length() == sequenceLength);
    }

    public static boolean isAlwaysIncreasing(String stringToCheck) {
        boolean result = true;
        for (int i = 0; i < stringToCheck.length() - 1; i++)
            if (stringToCheck.charAt(i) > stringToCheck.charAt(i + 1))
                return false;
        return result;
    }
}
