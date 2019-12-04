package day04;

import java.util.stream.Stream;

import utils.AdventOfCodeUtils;

public class Day04Main {

    public static void main(String[] args) {

        System.out.println("Solution A: " + solveA(getRangeAsStream()));

        System.out.println("Solution B: " + solveB(getRangeAsStream()));

    }

    public static int solveA(Stream<Integer> range) {
        return PasswordUtils.filterForPartA(range).toArray().length;
    }

    public static int solveB(Stream<Integer> range) {
        return PasswordUtils.filterForPartB(range).toArray().length;
    }

    private static Stream<Integer> getRangeAsStream() {
        String input = AdventOfCodeUtils.readClasspathFileLines(Day04Main.class, "input.txt").get(0);
        String[] inputSplit = input.split("-");
        return AdventOfCodeUtils.generateRange(Integer.parseInt(inputSplit[0]), Integer.parseInt(inputSplit[1]))
                .stream();
    }
}
