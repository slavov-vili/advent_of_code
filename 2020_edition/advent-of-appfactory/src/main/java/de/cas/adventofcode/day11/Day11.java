package de.cas.adventofcode.day11;

import de.cas.adventofcode.shared.ArrayUtils;
import de.cas.adventofcode.shared.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day11 extends Day<Integer> {

    public static final char OCCUPIED_SEAT = '#';
    public static final char EMPTY_SEAT = 'L';
    public static final char FLOOR = '.';

    protected Day11() {
        super(11);
    }

    public static void main(final String[] args) {
        new Day11().run();
    }

    @Override
    public Integer solvePart1(final List<String> input) {
        final char[][] seat2DArray = this.parseInput(input);
        char[][] previousSeat2DArray = new char[seat2DArray.length][seat2DArray[0].length];
        while (!ArrayUtils.are2DArraysEqual(previousSeat2DArray, seat2DArray)) {
            previousSeat2DArray = ArrayUtils.copy2DArray(seat2DArray);
            this.addNewCustomerToSeat(seat2DArray, previousSeat2DArray);
        }
        int countOccupied = 0;
        for (int i = 0; i < seat2DArray.length; i++) {
            for (int j = 0; j < seat2DArray[i].length; j++) {
                if (seat2DArray[i][j] == OCCUPIED_SEAT) {
                    countOccupied++;
                }
            }
        }
        return countOccupied;
    }

    private void addNewCustomerToSeat(final char[][] seat2DArray, final char[][] previous2DArray) {
        for (int i = 0; i < seat2DArray.length; i++) {
            for (int j = 0; j < seat2DArray[i].length; j++) {
                final List<Character> neighbourList = this.getNeighbourSeat(previous2DArray, i, j);
                final char currentSeat = seat2DArray[i][j];
                if (currentSeat == EMPTY_SEAT && neighbourList.stream().noneMatch(seat -> seat == OCCUPIED_SEAT)) {
                    seat2DArray[i][j] = OCCUPIED_SEAT;
                } else if (currentSeat == OCCUPIED_SEAT && neighbourList.stream().filter(seat -> seat == OCCUPIED_SEAT).count() >= 4) {
                    seat2DArray[i][j] = EMPTY_SEAT;
                }
            }
        }
    }

    private List<Character> getNeighbourSeat(final char[][] seat2DArray, final int row, final int column) {
        final List<Character> neighbours = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                final int nextRow = row + i;
                final int nextColumn = column + j;
                if (nextRow < 0 || nextRow >= seat2DArray.length || nextColumn < 0 || nextColumn >= seat2DArray[0].length) {
                    continue;
                }
                neighbours.add(seat2DArray[nextRow][nextColumn]);
            }
        }
        return neighbours;
    }

    private char[][] parseInput(final List<String> input) {
        final char[][] seat2DArray = new char[input.size()][input.get(0).length()];

        for (int lineIndex = 0; lineIndex < input.size(); lineIndex++) {
            final String line = input.get(lineIndex);

            for (int charIndex = 0; charIndex < line.length(); charIndex++) {
                seat2DArray[lineIndex][charIndex] = line.charAt(charIndex);
            }
        }
        return seat2DArray;
    }

	@Override
	public Integer solvePart2(final List<String> input) {
        final char[][] seat2DArray = this.parseInput(input);
        final char[][] previousSeat2DArray = new char[seat2DArray.length][seat2DArray[0].length];
        while (!ArrayUtils.are2DArraysEqual(previousSeat2DArray, seat2DArray)) {
            for (int i = 0; i < seat2DArray.length; i++) {
                previousSeat2DArray[i] = Arrays.copyOf(seat2DArray[i], seat2DArray[i].length);
            }
            this.addNewCustomerToSeatPart2(seat2DArray, previousSeat2DArray);
        }
        int countOccupied = 0;
        for (int i = 0; i < seat2DArray.length; i++) {
            for (int j = 0; j < seat2DArray[i].length; j++) {
                if (seat2DArray[i][j] == OCCUPIED_SEAT) {
                    countOccupied++;
                }
            }
        }
        return countOccupied;
    }

    private void addNewCustomerToSeatPart2(final char[][] seat2DArray, final char[][] previousSeat2DArray) {
        for (int i = 0; i < seat2DArray.length; i++) {
            for (int j = 0; j < seat2DArray[i].length; j++) {
                final List<Character> neighbourList = this.getNeighbourSeatPart2(previousSeat2DArray, i, j);
                final char currentSeat = seat2DArray[i][j];
                if (currentSeat == EMPTY_SEAT && neighbourList.stream().noneMatch(seat -> seat == OCCUPIED_SEAT)) {
                    seat2DArray[i][j] = OCCUPIED_SEAT;
                } else if (currentSeat == OCCUPIED_SEAT && neighbourList.stream().filter(seat -> seat == OCCUPIED_SEAT).count() >= 5) {
                    seat2DArray[i][j] = EMPTY_SEAT;
                }
            }
        }
    }

    private List<Character> getNeighbourSeatPart2(final char[][] previousSeat2DArray, final int row, final int column) {
        final List<Character> neighbours = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                int nextRow = row;
                int nextColumn = column;
                boolean overflow = false;
                do {
                    nextRow = nextRow + i;
                    nextColumn = nextColumn + j;
                    if (nextRow < 0 || nextRow >= previousSeat2DArray.length || nextColumn < 0 || nextColumn >= previousSeat2DArray[0].length) {
                        overflow = true;
                        break;
                    }
                } while (previousSeat2DArray[nextRow][nextColumn] == FLOOR);
                if (overflow) {
                    continue;
                }
                if (previousSeat2DArray[nextRow][nextColumn] != FLOOR) {
                    neighbours.add(previousSeat2DArray[nextRow][nextColumn]);
                }
            }
		}
		return neighbours;
	}

}
