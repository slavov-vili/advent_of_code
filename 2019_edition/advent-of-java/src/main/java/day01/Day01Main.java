package day01;

import java.util.List;
import java.util.stream.Collectors;

import utils.AdventOfCodeUtils;

public class Day01Main {

    public static void main(String[] args) {
	List<String> massesString = AdventOfCodeUtils.readClasspathFileLines(Day01Main.class, "input.txt");
	List<Integer> massesInt = AdventOfCodeUtils.parseAllStringsToInt(massesString);
	
	int solutionA = Day01Solver.solveA(massesInt);
	System.out.println(solutionA);
	
	int solutionB = Day01Solver.solveB(massesInt);
	System.out.println(solutionB);
    }

}
