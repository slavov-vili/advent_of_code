package day01;

import java.util.List;

import utils.AdventOfCodeUtils;

public class Day01Main {

    public static void main(String[] args) {
        List<Integer> massesInt = getInput();

        int solutionA = Day01Solver.solveA(massesInt);
        System.out.println("Solution A: " + solutionA);

        int solutionB = Day01Solver.solveB(massesInt);
        System.out.println("Solution B: " + solutionB);
    }
    
    protected static List<Integer> getInput() {
        List<String> massesString = AdventOfCodeUtils.readClasspathFileLines(Day01Main.class, "input.txt");
        return AdventOfCodeUtils.parseAllStringsToInt(massesString);
    }

}
