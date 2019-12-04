package day01;

import java.util.stream.Stream;

import utils.AdventOfCodeUtils;

public class Day01Main {

    public static void main(String[] args) {

        int solutionA = Day01Solver.solveA(getInputAsStream());
        System.out.println("Solution A: " + solutionA);

        int solutionB = Day01Solver.solveB(getInputAsStream());
        System.out.println("Solution B: " + solutionB);
    }

    protected static Stream<Integer> getInputAsStream() {
        return AdventOfCodeUtils.readClasspathFileLines(Day01Main.class, "input.txt").stream().map(Integer::parseInt);
    }

}
