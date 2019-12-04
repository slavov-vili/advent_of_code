package day04;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.AdventOfCodeUtils;

public class Day04Main {

    public static void main(String[] args) {
        //Integer valueToCheck = 1111122;
        //System.out.println("Checking: " + valueToCheck + " - " + PasswordChecker.checkPartA(valueToCheck));
        
        System.out.println("Solution A: " + solveA());
        
        System.out.println("Solution B: " + solveB());

    }
    
    public static int solveA() {
        return getRange().stream()
                .filter(value -> PasswordChecker.checkPartA(value))
                .toArray()
                .length;
    }
    
    public static int solveB() {
        return getRange().stream()
                .filter(value -> PasswordChecker.checkPartB(value))
                .toArray()
                .length;
    }

    private static List<Integer> getRange() {
        String input = AdventOfCodeUtils.readClasspathFileLines(Day04Main.class, "input.txt").get(0);
        String[] inputSplit = input.split("-");
        return AdventOfCodeUtils.generateRange(Integer.parseInt(inputSplit[0]), Integer.parseInt(inputSplit[1]));
    }
}
