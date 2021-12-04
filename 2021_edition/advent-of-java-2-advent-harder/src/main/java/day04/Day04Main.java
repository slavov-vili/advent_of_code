package day04;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import utils.AdventOfCodeUtils;

public class Day04Main {

	public static void main(String[] args) {
		List<String> input = AdventOfCodeUtils.readInput(Day04Main.class);
		if (!input.get(input.size() - 1).isEmpty())
			input.add("");

		List<Integer> numbers = Arrays.stream(input.get(0).split(",")).map(Integer::parseInt)
				.collect(Collectors.toList());

		System.out.println("First board score: "
				+ findFirstBingoBoardScore(numbers, extractBingoBoards(input.subList(2, input.size()))));

		System.out.println("Last board score: "
				+ findLastBingoBoardScore(numbers, extractBingoBoards(input.subList(2, input.size()))));
	}

	public static int findFirstBingoBoardScore(List<Integer> numbers, List<BingoBoard<Integer>> bingoBoards) {
		int lastNumber = numbers.get(0);
		Optional<BingoBoard<Integer>> boardWithFirstBingo = Optional.empty();
		for (int number : numbers) {
			for (BingoBoard<Integer> board : bingoBoards) {
				Optional<Point> lastCheckedNumberPosition = board.checkValue(number);

				if (lastCheckedNumberPosition.isPresent() && board.hasBingo(lastCheckedNumberPosition.get())) {
					boardWithFirstBingo = Optional.of(board);
					break;
				}
			}

			lastNumber = number;
			if (boardWithFirstBingo.isPresent())
				break;
		}

		int unmarkedSum = 0;
		if (boardWithFirstBingo.isPresent())
			unmarkedSum = boardWithFirstBingo.get().getUnmarkedValues().stream().mapToInt(value -> value).sum();
		return unmarkedSum * lastNumber;
	}

	public static int findLastBingoBoardScore(List<Integer> numbers, List<BingoBoard<Integer>> bingoBoards) {
		int lastNumber = numbers.get(0);
		Optional<BingoBoard<Integer>> boardWithLastBingo = Optional.empty();
		Set<Integer> boardsWithBingo = new HashSet<>();

		for (int number : numbers) {
			for (int i = 0; i < bingoBoards.size(); i++) {
				if (!boardsWithBingo.contains(i)) {
					BingoBoard<Integer> curBoard = bingoBoards.get(i);
					Optional<Point> lastCheckedNumberPosition = curBoard.checkValue(number);

					if (lastCheckedNumberPosition.isPresent() && curBoard.hasBingo(lastCheckedNumberPosition.get())) {
						boardWithLastBingo = Optional.of(curBoard);
						boardsWithBingo.add(i);
					}
				}
			}

			lastNumber = number;
			if (boardsWithBingo.size() == bingoBoards.size())
				break;
		}

		int unmarkedSum = 0;
		if (boardWithLastBingo.isPresent())
			unmarkedSum = boardWithLastBingo.get().getUnmarkedValues().stream().mapToInt(value -> value).sum();
		return unmarkedSum * lastNumber;
	}

	public static List<BingoBoard<Integer>> extractBingoBoards(List<String> input) {
		List<BingoBoard<Integer>> bingoBoards = new ArrayList<>();
		String curBingoBoard = "";
		for (String curLine : input) {
			if (curLine.isEmpty()) {
				List<Integer> curBingoBoardNumbers = Arrays.stream(curBingoBoard.trim().split("\\s+"))
						.map(Integer::parseInt).collect(Collectors.toList());
				bingoBoards.add(new BingoBoard<>(curBingoBoardNumbers));
				curBingoBoard = "";
			} else
				curBingoBoard += " " + curLine;
		}

		return bingoBoards;
	}
}
