package day08;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import utils.AdventOfCodeUtils;
import utils.IntegerUtils;

public class Day08Main {

	public static Pattern NODE_PATTERN = Pattern.compile("([A-Z]+) = \\(([A-Z]+), ([A-Z]+)\\)");

	public static void main(String[] args) {
		CamelDoc document = parseDocument();

		System.out.println("Steps to ZZZ: " + solveA(document));

		System.out.println("Steps to all ends: " + solveB(document));
	}

	public static long solveA(CamelDoc document) {
		return document.countSteps(List.of("AAA"), "ZZZ"::equals);
	}

	public static long solveB(CamelDoc document) {
		return document.countSteps(document.findStarts(), CamelDoc::isEnd);
	}

	record CamelDoc(List<Integer> instructions, Map<String, List<String>> nodes) {

		public long countSteps(List<String> starts, Predicate<String> endChecker) {
			var stepsToEnd = starts.stream().map(s -> 0).collect(Collectors.toList());
			List<String> curNodes = new ArrayList<>(starts);

			while (!curNodes.stream().allMatch(endChecker)) {
				for (int i = 0; i < curNodes.size(); i++) {
					if (!isEnd(curNodes.get(i))) {
						String curNode = curNodes.get(i);
						int curSteps = stepsToEnd.get(i);
						curNodes.set(i, this.followInstruction(curSteps, curNode));
						stepsToEnd.set(i, curSteps + 1);
					}

				}
			}

			return stepsToEnd.stream().mapToLong(s -> s).reduce(IntegerUtils::findLCM).getAsLong();
		}

		public String followInstruction(int i, String curNode) {
			int instruction = this.instructions.get(i % this.instructions.size());
			return this.nodes.get(curNode).get(instruction);
		}

		public List<String> findStarts() {
			return this.nodes.keySet().stream().filter(CamelDoc::isStart).toList();
		}

		public static boolean isStart(String node) {
			return node.endsWith("A");
		}

		public static boolean isEnd(String node) {
			return node.endsWith("Z");
		}
	}

	public static CamelDoc parseDocument() {
		List<String> input = AdventOfCodeUtils.readInput(Day08Main.class);
		var rlInstructions = input.get(0);
		var instructions = IntStream.range(0, rlInstructions.length()).mapToObj(i -> rlToInt(rlInstructions.charAt(i)))
				.toList();

		Map<String, List<String>> nodes = new HashMap<>();

		for (String line : input.subList(2, input.size())) {
			Matcher matcher = NODE_PATTERN.matcher(line);
			matcher.find();
			nodes.put(matcher.group(1), List.of(matcher.group(2), matcher.group(3)));
		}

		return new CamelDoc(instructions, nodes);
	}

	public static int rlToInt(char rl) {
		if (rl == 'R')
			return 1;
		else if (rl == 'L')
			return 0;
		else
			return -1;
	}
}
