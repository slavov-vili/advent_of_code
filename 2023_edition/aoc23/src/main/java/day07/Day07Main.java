package day07;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import utils.AdventOfCodeUtils;

public class Day07Main {

	public static final char JOKER = 'J';

	public static List<Character> CARD_RANKING = new ArrayList<>(
			List.of('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'));

	public static void main(String[] args) {
		List<Hand> hands = parseHands();

		System.out.println("Total winnings: " + solveA(hands));

		System.out.println("Total winnings with special Js: " + solveB(hands));
	}

	public static long solveA(List<Hand> hands) {
		return calcWinnings(hands, Hand::compareTo);
	}

	// modifying static variable :barf:
	public static long solveB(List<Hand> hands) {
		CARD_RANKING.remove((Character) JOKER);
		CARD_RANKING.add(0, JOKER);
		return calcWinnings(hands, Hand::specialCompareTo);
	}

	public static long calcWinnings(List<Hand> hands, Comparator<Hand> comparator) {
		List<Hand> sortedHands = hands.stream().sorted(comparator).toList();
		return IntStream.range(0, sortedHands.size()).mapToLong(i -> (i + 1) * sortedHands.get(i).bid).sum();
	}

	record Hand(String cards, long bid) implements Comparable<Hand> {
		public static Hand fromString(String str) {
			String[] parts = str.split(" ");
			return new Hand(parts[0], Long.parseLong(parts[1]));
		}

		@Override
		public int compareTo(Hand other) {
			int typeCompare = HandType.fromCards(this.cards).compareTo(HandType.fromCards(other.cards));
			if (typeCompare != 0)
				return typeCompare;

			return this.compareCardsWith(other.cards);
		}

		public int specialCompareTo(Hand other) {
			int typeCompare = HandType.findStrongest(this.cards).compareTo(HandType.findStrongest(other.cards));
			if (typeCompare != 0)
				return typeCompare;

			return this.compareCardsWith(other.cards);
		}

		public int compareCardsWith(String otherCards) {
			int result = 0;
			int i = 0;

			while (i < this.cards.length() && result == 0) {
				result = compareCards(this.cards.charAt(i), otherCards.charAt(i));
				i++;
			}

			return result;
		}
	}

	public static enum HandType {
		HIGH_CARD, ONE_PAIR, TWO_PAIR, THREE_OF_A_KIND, FULL_HOUSE, FOUR_OF_A_KIND, FIVE_OF_A_KIND;

		public static HandType fromCards(String cards) {
			var cardCounts = countCards(cards);
			var groups = new HashSet<>(cardCounts.values());

			var result = HIGH_CARD;
			if (groups.contains(5))
				result = FIVE_OF_A_KIND;
			else if (groups.contains(4))
				result = FOUR_OF_A_KIND;
			else if (groups.contains(3)) {
				if (groups.contains(2))
					result = FULL_HOUSE;
				else
					result = THREE_OF_A_KIND;
			} else if (groups.contains(2)) {
				int singles = (int) cardCounts.keySet().stream().filter(card -> cardCounts.get(card) == 1).count();
				if (singles == 1)
					result = TWO_PAIR;
				else
					result = ONE_PAIR;
			}

			return result;
		}

		public static HandType findStrongest(String cards) {
			int jCount = (int) IntStream.range(0, cards.length()).filter(i -> cards.charAt(i) == JOKER).count();
			var maxType = fromCards(cards);

			if (jCount == 0)
				return maxType;

			for (char card : CARD_RANKING) {
				var curType = fromCards(cards.replace(JOKER, card));

				if (curType.compareTo(maxType) > 0)
					maxType = curType;
			}

			return maxType;
		}
	}

	public static Map<Character, Integer> countCards(String cards) {
		var cardCounts = new HashMap<Character, Integer>();
		for (char card : cards.toCharArray())
			cardCounts.put(card, cardCounts.getOrDefault(card, 0) + 1);

		return cardCounts;
	}

	public static int compareCards(char a, char b) {
		return Integer.compare(CARD_RANKING.indexOf(a), CARD_RANKING.indexOf(b));
	}

	public static List<Hand> parseHands() {
		List<String> input = AdventOfCodeUtils.readInput(Day07Main.class);
		return input.stream().map(Hand::fromString).toList();
	}
}
