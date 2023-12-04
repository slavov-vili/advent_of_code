package day04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import utils.AdventOfCodeUtils;

public class Day04Main {

	public static Pattern CARD_PATTERN = Pattern.compile("Card +([0-9]+): ([0-9 ]+) \\| ([0-9 ]+)");

	public static void main(String[] args) {
		List<Card> cards = parseCards();

		System.out.println("Total points from cards: " + solveA(cards));

		System.out.println("Total scratchcards: " + solveB(cards));
	}

	public static int solveA(List<Card> cards) {
		return cards.stream().mapToInt(Card::calcPoints).sum();
	}

	public static int solveB(List<Card> cards) {
		var cardCounts = new ArrayList<Integer>(cards.size());
		IntStream.range(0, cards.size()).forEach(i -> cardCounts.add(1));
		for (int i = 0; i < cards.size(); i++) {
			int curCardCount = cardCounts.get(i);
			int matches = cards.get(i).calcMatches();
			IntStream.range(i + 1, i + matches + 1).forEach(j -> cardCounts.set(j, cardCounts.get(j) + curCardCount));
		}
		return cardCounts.stream().reduce((a, b) -> a + b).get();
	}

	record Card(int id, Set<Integer> winningNumbers, List<Integer> containsNumbers) {

		public static Card fromString(String str) {
			Matcher matcher = CARD_PATTERN.matcher(str);
			matcher.find();
			int id = Integer.parseInt(matcher.group(1));
			var winningNumbers = new HashSet<>(parseNumbers(matcher.group(2)));
			var containsNumbers = parseNumbers(matcher.group(3));
			return new Card(id, winningNumbers, containsNumbers);
		}

		public int calcPoints() {
			int matches = this.calcMatches();
			return (matches == 0) ? 0 : (int) Math.pow(2, matches - 1);
		}

		public int calcMatches() {
			return (int) this.containsNumbers.stream().filter(this.winningNumbers::contains).count();
		}
	}

	public static List<Card> parseCards() {
		return AdventOfCodeUtils.readInput(Day04Main.class).stream().map(Card::fromString).toList();
	}

	public static List<Integer> parseNumbers(String nums) {
		return Arrays.stream(nums.split(" +")).filter(num -> !num.isBlank()).map(Integer::parseInt).toList();
	}
}
