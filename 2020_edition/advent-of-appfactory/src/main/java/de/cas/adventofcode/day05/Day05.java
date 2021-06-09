package de.cas.adventofcode.day05;

import de.cas.adventofcode.shared.Day;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day05 extends Day<Integer> {
    protected Day05() {
        super(5);
    }

    public static void main(final String[] args) {
        new Day05().run();
    }

    @Override
    public Integer solvePart1(final List<String> input) {
        int highestSeatId = 0;

        for (final String line : input) {
            final int row = this.getRow(line);
            final int column = this.getColumn(line);
            final int seatId = this.getSeatId(row, column);

            if (seatId > highestSeatId) {
                highestSeatId = seatId;
            }
        }

        return highestSeatId;
    }

    @Override
    public Integer solvePart2(final List<String> input) {
        final Set<Integer> allSeatIds = this.getAllSeatIds();

        for (final String line : input) {
            final int row = this.getRow(line);
            final int column = this.getColumn(line);
            final int seatId = this.getSeatId(row, column);

            allSeatIds.remove(seatId);
        }

        int previousSeatId = -1;

        for (final int seatId : allSeatIds) {
            if (seatId - previousSeatId > 1) {
                return seatId;
            }

            previousSeatId = seatId;
        }

        return 0;
    }

    private int getRow(final String line) {
        String rowInstructions = line.substring(0, 7);
        rowInstructions = rowInstructions.replace('F', '0');
        rowInstructions = rowInstructions.replace('B', '1');
        return Integer.parseInt(rowInstructions, 2);
    }

    private int getColumn(final String line) {
        String columnInstructions = line.substring(7, 10);
        columnInstructions = columnInstructions.replace('L', '0');
        columnInstructions = columnInstructions.replace('R', '1');
        return Integer.parseInt(columnInstructions, 2);
    }

    private int getSeatId(final int row, final int column) {
        return row * 8 + column;
    }

    private Set<Integer> getAllSeatIds() {
        final Set<Integer> allSeatIds = new HashSet<>();

        for (int row = 0; row < 128; row++) {
            for (int column = 0; column < 8; column++) {
                allSeatIds.add(this.getSeatId(row, column));
            }
        }

        return allSeatIds;
    }
}
