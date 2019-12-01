package day01;

import java.util.List;
import java.util.stream.Collectors;

import utils.AdventOfCodeUtils;

public class Day01Main {

    public static void main(String[] args) {
	List<String> massesString = AdventOfCodeUtils.readClasspathFileLines(Day01Solver.class, "input.txt");
	List<Integer> massesInt = massesString.stream().map(Integer::parseInt).collect(Collectors.toList());
	
	Day01Solver solver = new Day01Solver();
	
	int solutionA = solver.solveA(massesInt);
	System.out.println(solutionA);
	
	int solutionB = solver.solveB(massesInt);
	System.out.println(solutionB);
    }

}
