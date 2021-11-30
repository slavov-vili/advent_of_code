package utils;

public class IntegerUtils {
    private static final String consecutiveDigitsPattern = "(\\d)\\1+";

    /*
     * Compares two integers and returns: 1 if the first integer is bigger; -1 if
     * the first integer is smaller; 0 if the two integers are equal
     */
    public static int compareInts(int a, int b) {
        if (a > b)
            return 1;
        else if (a < b)
            return -1;
        else
            return 0;
    }
    
    public static long findLCM(long a, long b) {
        long gcd = findGCD(a, b);
        if (gcd == 0)
            return 0;
        
        return Math.abs((long)a * (long)b) / (long)gcd;
    }
    
    public static long findGCD(long a, long b) {
        if (a == b)
            return a;
        
        if ((a == 0) || (b == 0))
            return (a == 0) ? b : a;
        
        long bigger = (a > b) ? a : b;
        long smaller = (bigger == a) ? b : a;
        return findGCD(smaller, bigger % smaller);
    }

    public static boolean checkIntegerLength(Integer value, int expectedLength) {
        return String.valueOf(value).length() == expectedLength;
    }

    public static boolean integerIsWithinRange(Integer value, Integer rangeStartInclusive, Integer rangeEndInclusive) {
        return (rangeStartInclusive <= value) && (value <= rangeEndInclusive);
    }

    public static boolean hasSequenceOfSameDigits(Integer password) {
        return StringUtils.hasSequenceOfSameChars(String.valueOf(password), consecutiveDigitsPattern);

    }

    public static boolean hasSequenceOfSameDigits(Integer password, int sequenceLength) {
        return StringUtils.hasSequenceOfSameChars(String.valueOf(password), consecutiveDigitsPattern, sequenceLength);
    }
}
