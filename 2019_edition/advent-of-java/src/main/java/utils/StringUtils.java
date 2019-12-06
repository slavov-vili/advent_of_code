package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class StringUtils {
    public static Stream<String> getAllMatches(String inputString, String regex) {
        List<String> results = new ArrayList<>();
        Matcher matcher = Pattern.compile(regex).matcher(inputString);
        while (matcher.find())
            results.add(matcher.group());
        return results.stream();
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
