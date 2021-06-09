package de.cas.adventofcode.day22;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

import de.cas.adventofcode.shared.Day;

public class Day22 extends Day<Long> {

	public static final String PLAYER_PATTERN = "Player (\\d+):";

	protected Day22() {
		super(22);
	}

	public static void main(final String[] args) {
		new Day22().run();
	}

	@Override
	public Long solvePart1(List<String> input) {
		return solvePart(input, this::doCombat);
	}

	private int doCombat(LinkedList<Integer> player1Deck, LinkedList<Integer> player2Deck) {
		while(player1Deck.size()!=0 && player2Deck.size()!=0) {
			int player1Card = player1Deck.pollFirst();
			int player2Card = player2Deck.pollFirst();

			updateWinnerDeck(player1Card > player2Card,
				player1Deck, player1Card,
				player2Deck, player2Card);
		}

		return (player2Deck.size() == 0) ? 1 : 2;
	}

	@Override
	public Long solvePart2(List<String> input) {
        return solvePart(input, this::doRecursiveCombat);
	}

	private int doRecursiveCombat(LinkedList<Integer> player1Deck, LinkedList<Integer> player2Deck) {
		Set<String> previousRounds = new HashSet<>();
		Optional<Integer> optionalWinner = Optional.empty();
		while(player1Deck.size()!=0 && player2Deck.size()!=0) {
			String currentRound = encodeCurrentRound(player1Deck, player2Deck);
			if (previousRounds.contains(currentRound)) {
				optionalWinner = Optional.of(1);
				break;
			}

			previousRounds.add(currentRound);
			int player1Card = player1Deck.pollFirst();
			int player2Card = player2Deck.pollFirst();

			if (player1Deck.size() >= player1Card && player2Deck.size() >= player2Card) {
				LinkedList<Integer> newPlayer1Deck = new LinkedList<>(player1Deck.subList(0, player1Card));
				LinkedList<Integer> newPlayer2Deck = new LinkedList<>(player2Deck.subList(0, player2Card));
				optionalWinner = Optional.of(doRecursiveCombat(newPlayer1Deck, newPlayer2Deck));
			} else
				optionalWinner = Optional.of((player1Card > player2Card) ? 1 : 2);

			updateWinnerDeck(optionalWinner.get().equals(1),
				player1Deck, player1Card,
				player2Deck, player2Card);
		}
		return optionalWinner.orElseGet(() -> (player2Deck.size() == 0) ? 1 : 2);
	}

	private <T> void updateWinnerDeck(boolean player1Wins,
		LinkedList<T> player1Deck, T player1Card,
		LinkedList<T> player2Deck, T player2Card) {
		if (player1Wins) {
			player1Deck.addLast(player1Card);
			player1Deck.addLast(player2Card);
		} else {
			player2Deck.addLast(player2Card);
			player2Deck.addLast(player1Card);
		}
	}

	private String encodeCurrentRound(LinkedList<Integer> player1Deck,
		LinkedList<Integer> player2Deck) {
		return player1Deck.toString() + player2Deck.toString();
	}

	private Long solvePart(List<String> input, BiFunction<LinkedList<Integer>, LinkedList<Integer>, Integer> combatDoer) {
		Map<Integer, LinkedList<Integer>> playerDeckMap = parseDecks(input);

		int winner = combatDoer.apply(playerDeckMap.get(1), playerDeckMap.get(2));

		List<Integer> winnerDeck = playerDeckMap.get(winner);
		return IntStream.range(0, winnerDeck.size())
			.mapToLong(i -> winnerDeck.get(winnerDeck.size() - i - 1) * (i + 1))
			.sum();
	}

	private Map<Integer, LinkedList<Integer>> parseDecks(List<String> input) {
		Map<Integer, LinkedList<Integer>> playerDeckMap = new HashMap<>();
		Integer currentPlayer = 0;
		for (String line : input) {
			if (line.matches(PLAYER_PATTERN)) {
				currentPlayer = Integer.parseInt(line.replaceAll(PLAYER_PATTERN, "$1"));
				LinkedList<Integer> currentDeck = new LinkedList<>();
				playerDeckMap.put(currentPlayer, currentDeck);
			} else if (!line.isEmpty()) {
				playerDeckMap.get(currentPlayer).add(Integer.parseInt(line));
			}
			
		}
		return playerDeckMap;
	}

}
