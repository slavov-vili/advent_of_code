package day02;

import java.util.List;

import utils.AdventOfCodeUtils;

public class Day02Main {

	public static void main(String[] args) {
		List<String> input = AdventOfCodeUtils.readInput(Day02Main.class);
		
		System.out.println(solveA(input));
		
		System.out.println(solveB(input));
	}
	
	public static int solveA(List<String> input) {
		int x = 0;
		int depth = 0;
		for (String instruction : input) {
			String[] split = instruction.split(" ");
			int value = Integer.parseInt(split[1]);
			if (split[0].equals("forward")) {
				x += value;
			} else if (split[0].equals("down")) {
				depth += value;
			} else {
				depth -= value;
			}
		}
		
		return x * depth;
	}
	
	public static int solveB(List<String> input) {
		int x = 0;
		int depth = 0;
		int aim = 0;
		
		for (String instruction : input) {
			String[] split = instruction.split(" ");
			int value = Integer.parseInt(split[1]);
			if ("forward".equals(split[0])) {
				x += value;
				depth += (aim * value);
			} else if ("down".equals(split[0])) {
				aim += value;
			} else {
				aim -= value;
			}
		}
		
		return x * depth;
	}
}
