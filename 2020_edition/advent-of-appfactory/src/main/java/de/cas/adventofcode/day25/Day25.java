package de.cas.adventofcode.day25;

import java.util.List;

import de.cas.adventofcode.shared.Day;

public class Day25 extends Day<Long> {
	protected Day25() {
		super(25);
	}

	public static void main(final String[] args) {
		new Day25().run();
	}

	@Override
	public Long solvePart1(List<String> input) {
		long pubKeyDoor = Long.parseLong(input.get(0));
		long pubKeyCard = Long.parseLong(input.get(1));
		
		long loopSizeCard = findLoopSize(7, pubKeyCard);
		
		return encryptKey(pubKeyDoor, loopSizeCard);
	}

	@Override
	public Long solvePart2(List<String> input) {
		return null;
	}
	
	private long encryptKey(long publicKey, long loopSize) {
		long encryptedKey = 1;
		for (int i=0; i<loopSize; i++) {
			encryptedKey = calcLoopValue(encryptedKey, publicKey);
		}
		return encryptedKey;
	}
	
	private long findLoopSize(long subjectNumber, long publicKey) {
		long loopValue = 1;
		long loopSize = 0;
		while(loopValue != publicKey) {
			loopSize++;
			loopValue = calcLoopValue(loopValue, subjectNumber);
		}
		return loopSize;
	}

	private long calcLoopValue(long currentValue, long subjectNumber) {
		long nextValue = currentValue;
		nextValue *= subjectNumber;
		nextValue %= 20201227L;
		return nextValue;
	}
}
