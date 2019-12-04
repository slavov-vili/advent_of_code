package day04;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordChecker {
    private static final int PASSWORD_LENGTH = 6;
    private static final Integer RANGE_START = 246515;
    private static final Integer RANGE_END = 739105;

    public static boolean checkPartA(Integer password) {
        return isCorrectLength(password) && isWithingRange(password)
                && hasSameAdjacentDigits(password) && isAlwaysIncreasing(password);
    }
    
    public static boolean checkPartB(Integer password) {
        return isCorrectLength(password) && isWithingRange(password)
                && hasSameAdjacentDigitsOfLength(password) && isAlwaysIncreasing(password);
    }
    
    private static boolean isCorrectLength(Integer password) {
        return String.valueOf(password).length() == PASSWORD_LENGTH;
    }
    
    private static boolean isWithingRange(Integer password) {
        return (RANGE_START <= password) && (password <= RANGE_END);
    }
    
    // TODO: change to matcher?
    private static boolean hasSameAdjacentDigits(Integer password) {
        return String.valueOf(password)
                .matches("(.*?)(\\d)\\2(.*?)");
    }
    
    // TODO: Add util function that returns list of matches
    private static boolean hasSameAdjacentDigitsOfLength(Integer password) {
        List<String> sequencesOfConsecutiveDigits = new ArrayList();
        Matcher matcher = Pattern.compile("(\\d)\\1+").matcher(String.valueOf(password));
        while(matcher.find())
          sequencesOfConsecutiveDigits.add(matcher.group());
        return sequencesOfConsecutiveDigits.stream()
                .anyMatch(seq -> seq.length() == 2);
    }
    
    private static boolean isAlwaysIncreasing(Integer password) {
        String passwordString = String.valueOf(password);
        boolean result = true;
        
        for (int i=0; i<passwordString.length()-1; i++) {
            if (passwordString.charAt(i) > passwordString.charAt(i+1)) {
                result = false;
                break;
            }
        }
        
        return result;
    }
}
