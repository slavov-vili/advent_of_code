package day05;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import utils.AdventOfCodeUtils;

public class Day05Main {
	public static final int STACK_COUNT = 9;
	public static final Pattern INSTRUCTION_PATTERN = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");

	public static void main(String[] args) {
		List<String> input = AdventOfCodeUtils.readInput(Day05Main.class);
		int separatorLine = findSeparatorLine(input);
		var instructions = parseInstructions(input.subList(separatorLine + 1, input.size()));

		System.out.println("Message after instructions done: "
				+ solveA(parseStacks(input.subList(0, separatorLine)), instructions));

		System.out.println("Real message after instructions done: "
				+ solveB(parseStacks(input.subList(0, separatorLine)), instructions));
	}

	public static String solveA(List<Deque<Character>> stacks, List<Instruction> instructions) {
		for (var instr : instructions) {
			var fromStack = stacks.get(instr.from - 1);
			var toStack = stacks.get(instr.to - 1);
			IntStream.range(0, instr.count).forEach(i -> toStack.push(fromStack.pop()));
		}

		return stacks.stream().map(Deque::pop).reduce("", (str, ch) -> str + ch, String::concat);
	}

	public static String solveB(List<Deque<Character>> stacks, List<Instruction> instructions) {
		for (var instr : instructions) {
			var fromStack = stacks.get(instr.from - 1);
			var toStack = stacks.get(instr.to - 1);
			var tempStack = new ArrayDeque<Character>();
			IntStream.range(0, instr.count).forEach(i -> tempStack.push(fromStack.pop()));
			IntStream.range(0, tempStack.size()).forEach(i -> toStack.push(tempStack.pop()));
		}

		return stacks.stream().map(Deque::pop).reduce("", (str, ch) -> str + ch, String::concat);
	}

	public static int findSeparatorLine(List<String> lines) {
		return IntStream.range(0, lines.size()).filter(i -> lines.get(i).isEmpty()).findFirst().getAsInt();
	}

	public static List<Deque<Character>> parseStacks(List<String> lines) {
		var stacks = new ArrayList<Deque<Character>>();
		IntStream.range(0, STACK_COUNT).forEach(i -> stacks.add(new ArrayDeque<Character>()));
		for (int lineIndex = 0; lineIndex < lines.size() - 1; lineIndex++) {
			var curLine = lines.get(lineIndex);
			for (int stackIndex = 0; stackIndex < curLine.length(); stackIndex += 4) {
				var curStackChar = curLine.charAt(stackIndex + 1);
				if (!Character.isWhitespace(curStackChar)) {
					stacks.get(stackIndex / 4).add(curStackChar);
				}
			}
		}
		return stacks;
	}

	public static List<Instruction> parseInstructions(List<String> lines) {
		return lines.stream().map(Instruction::fromLine).toList();
	}

	public record Instruction(int count, int from, int to) {
		public static Instruction fromLine(String line) {
			Matcher matcher = INSTRUCTION_PATTERN.matcher(line);
			matcher.matches();
			int count = Integer.parseInt(matcher.group(1));
			int from = Integer.parseInt(matcher.group(2));
			int to = Integer.parseInt(matcher.group(3));

			return new Instruction(count, from, to);
		}
	};
}
