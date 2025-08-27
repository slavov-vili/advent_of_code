package day24;

import java.util.List;
import java.util.Optional;

import utils.AdventOfCodeUtils;

public class Day24Main {

    public static void main(String[] args) {
        List<String> input = AdventOfCodeUtils.readInput(Day24Main.class);

		System.out.println("Largest valid model number: " + solveA(input));
    }

    // TODO: try changing from the most significant downwards
    public static long solveA(List<String> lines) {
        long cur = 99999999999999L;
//        cur = 11111111111111L;
        var alu = new ParsingALU(lines, cur);
        long lastResult = -1;
        long lastMin = Long.MAX_VALUE;
        while (lastResult != 0) {
            alu.reset(Optional.of(cur));
            lastResult = alu.process();
//            System.out.println("Process(" + cur + ") = " + lastResult);

            if (lastResult < lastMin) {
                System.out.println("Min Result (" + cur + ") = " + lastResult);
                lastMin = lastResult;
            }
            cur = calcNextModelNumber(cur, -1);
        }
        return cur;
    }

    public static long calcNextModelNumber(long cur, long offset) {
		long nextNumber;
		long zeroFactor;
        int i = 1;
        do {
            nextNumber = cur + i * offset;
            zeroFactor = findZeroFactor(nextNumber);
            i++;
        } while (1L != zeroFactor);

        return nextNumber;
    }

    public static long findZeroFactor(long num) {
        long factor = 1L;

        while (num / factor != 0L) {
			long nextFactor = factor * 10L;
            long digit = (num % nextFactor) / factor;
            if (0L == digit)
                return nextFactor;
            factor = nextFactor;
        }

        return 1L;
    }
}
