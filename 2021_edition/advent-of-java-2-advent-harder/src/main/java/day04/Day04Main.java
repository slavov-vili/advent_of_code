package day04;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
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

		List<BingoBoard<Integer>> boardsInBingoOrder = findBoardsInBingoOrder(numbers,
				extractBingoBoards(input.subList(2, input.size())));

		System.out.println("First board score: " + calcBoardScore(boardsInBingoOrder.get(0)));

		System.out
				.println("Last board score: " + calcBoardScore(boardsInBingoOrder.get(boardsInBingoOrder.size() - 1)));
	}

	public static List<BingoBoard<Integer>> findBoardsInBingoOrder(List<Integer> numbers,
			List<BingoBoard<Integer>> boards) {
		if (boards.size() == 0)
			return boards;
		else {
			List<BingoBoard<Integer>> boardsInOrder = boards.stream().filter(board -> board.markValue(numbers.get(0)))
					.collect(Collectors.toList());

			List<BingoBoard<Integer>> nextBoards = boards;
			nextBoards.removeAll(boardsInOrder);

			boardsInOrder.addAll(findBoardsInBingoOrder(numbers.subList(1, numbers.size()), nextBoards));
			return boardsInOrder;
		}
	}

	public static int calcBoardScore(BingoBoard<Integer> board) {
		int unmarkedSum = board.getUnmarkedValues().stream().mapToInt(value -> value).sum();
		Set<Integer> valueMarkOrder = board.getValueMarkOrder();
		return unmarkedSum * board.getValueByMarkIndex(valueMarkOrder.size() - 1);
	}

	public static List<BingoBoard<Integer>> extractBingoBoards(List<String> input) {
		List<BingoBoard<Integer>> bingoBoards = new LinkedList<>();
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
