package de.cas.adventofcode.shared;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MathUtils {
	// aka Chinese Remainder Theorem
	// Based on: https://brilliant.org/wiki/chinese-remainder-theorem/
	public static long solveCongruenceEquations(Map<Long, Long> modToRemainder) {
		long lcm = modToRemainder.keySet().stream().reduce(1L, (x1, x2) -> x1 * x2);
		Map<Long, Long> modToReducedLCM = modToRemainder.keySet().stream()
				.collect(Collectors.toMap(Function.identity(), key -> lcm / key));
		Map<Long, Long> modToInverse = modToReducedLCM.keySet().stream()
				.collect(Collectors.toMap(Function.identity(),
						key -> findModularMultiplicativeInverseOf(modToReducedLCM.get(key), key).get()));
		return modToRemainder.keySet().stream()
				.mapToLong(key -> modToRemainder.get(key) * modToReducedLCM.get(key) * modToInverse.get(key))
				.sum() % lcm;
	}
	
	public static Optional<Long> findModularMultiplicativeInverseOf(long number, long modulo) {
		return findGCDExtended(new EEData(number, modulo)).getModularMultiplicativeInverse();
	}
	
	// use the Extended Euclidean Algorithm (solves the equation a*x + b*y = gcd(a, b)
	// TODO: figure out why TF this works (thanks Tutorialspoint)
	// https://www.tutorialspoint.com/c-program-for-extended-euclidean-algorithms
	public static EEData findGCDExtended(EEData eeData) {
		if (eeData.modulo == 0) {
			EEData result = new EEData(eeData.number, eeData.modulo);
			result.gcd = result.number;
			return result;
		}
		
		long remainder = eeData.number % eeData.modulo;
		
		EEData result = findGCDExtended(new EEData(eeData.modulo, remainder));
		long curQuotient = eeData.number / eeData.modulo;
		long newQuotientModulo = result.quotientNumber - (curQuotient * result.quotientModulo);
		long newQuotientNumber = result.quotientModulo;
		
		return new EEData(eeData.number, eeData.modulo,
				newQuotientNumber, newQuotientModulo, result.gcd);
	}
	
	// aka Euclidean Algorithm
	public static long findGCDOf(long num1, long num2) {
		long bigger = Math.abs(Long.max(num1, num2));
		long smaller = Math.abs(Long.min(num1, num2));

		if (smaller == 0)
			return bigger;
		
		return findGCDOf(smaller, bigger % smaller);
	}
	
	// Represents data of the form a*x + b*y = gcd(a,b) where b > a
	// since b*y is always divisibly by b:
	// (a*x = gcd(a,b)) (mod b)
	// if gcd(a,b) == 1: x is the modular multiplicative inverse of a under mod b
	public static class EEData {
		public long number;
		public long modulo;
		public long quotientNumber;
		public long quotientModulo;
		public long gcd;

		public EEData(long number, long modulo) {
			this(number, modulo, 1, 0, 1);
		}

		public EEData(EEData otherData) {
			this(otherData.number, otherData.modulo,
					otherData.quotientNumber, otherData.quotientModulo,
					otherData.gcd);
		}

		public EEData(long number, long modulo, long quotientNumber, long quotientModulo, long gcd) {
			this.number = number;
			this.modulo = modulo;
			this.quotientNumber = quotientNumber;
			this.quotientModulo = quotientModulo;
			this.gcd = gcd;
		}
		
		public boolean containsModularMultiplicativeInverse() {
			return this.gcd == 1L;
		}
		
		public Optional<Long> getModularMultiplicativeInverse() {
			if (this.containsModularMultiplicativeInverse())
				return Optional.of(Math.floorMod(this.quotientNumber, this.modulo));
			else
				return Optional.empty();
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("EEData(");
			sb.append("number=" + this.number);
			sb.append("; ");
			sb.append("quotientNumber=" + this.quotientNumber);
			sb.append("; ");
			sb.append("modulo=" + this.modulo);
			sb.append("; ");
			sb.append("quotientModulo=" + this.quotientModulo);
			sb.append("; ");
			sb.append("gcd=" + this.gcd);
			return sb.toString();
		}
	}

}
