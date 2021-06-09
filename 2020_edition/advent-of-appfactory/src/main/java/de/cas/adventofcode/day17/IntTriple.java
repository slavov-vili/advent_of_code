package de.cas.adventofcode.day17;

import org.apache.commons.lang3.tuple.MutableTriple;

public class IntTriple extends MutableTriple<Integer, Integer, Integer> {

	/**
	 * Create a new triple instance.
	 *
	 * @param left   the left value, may be null
	 * @param middle the middle value, may be null
	 * @param right  the right value, may be null
	 */
	public IntTriple(Integer left, Integer middle, Integer right) {
		super(left, middle, right);
	}
}
