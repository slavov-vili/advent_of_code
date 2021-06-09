package de.cas.adventofcode.day17;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.cas.adventofcode.shared.Day;

public class Day17 extends Day<Integer> {
	protected Day17() {
		super(17);
	}

	public static void main(final String[] args) {
		new Day17().run();
	}

	@Override
	public Integer solvePart1(List<String> input) {
		Set<IntTriple> activeCoordinates = new HashSet<>();
		for (int y = 0; y < input.size(); y++) {
			for (int x = 0; x < input.get(y).length(); x++) {
				if (input.get(y).charAt(x) == '#') {
					activeCoordinates.add(new IntTriple(x, y, 0));
				}
			}
		}
		for (int cycle = 0; cycle < 6; cycle++) {
			activeCoordinates = executeCycle(activeCoordinates);
		}
		return activeCoordinates.size();
	}

	private Set<IntTriple> executeCycle(Set<IntTriple> activeCoordinates) {
		Set<IntTriple> newActiveCoordinates = new HashSet<>(activeCoordinates);
		for (int x = -15; x <= 15; x++) {
			for (int y = -15; y <= 15; y++) {
				for (int z = -15; z<=15; z++) {
					IntTriple currentCoordinate = new IntTriple(x, y, z);
					List<IntTriple> activeNeighbours = getActiveNeighbours(activeCoordinates, currentCoordinate);
					if (activeCoordinates.contains(currentCoordinate)) {
						if (activeNeighbours.size() != 2 && activeNeighbours.size() != 3) {
							newActiveCoordinates.remove(currentCoordinate);
						}
					} else {
						if (activeNeighbours.size() == 3) {
							newActiveCoordinates.add(currentCoordinate);
						}
					}
				}
			}
		}
		return newActiveCoordinates;
	}

	@Override
	public Integer solvePart2(List<String> input) {
		Set<IntQuadruple> activeCoordinates = new HashSet<>();
		for (int y = 0; y < input.size(); y++) {
			for (int x = 0; x < input.get(y).length(); x++) {
				if (input.get(y).charAt(x) == '#') {
					activeCoordinates.add(new IntQuadruple(
							x, y, 0, 0));
				}
			}
		}

		for (int cycle = 0; cycle < 6; cycle++) {
			activeCoordinates = executeCycle4D(activeCoordinates);
		}
		return activeCoordinates.size();
	}

	private List<IntTriple> getActiveNeighbours(Set<IntTriple> activeCoordinates, IntTriple coordinates) {
		final List<IntTriple> activeNeighbours = new ArrayList<>();
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				for (int z = -1; z<=1; z++) {
					if (x == 0 && y == 0 && z == 0) {
						continue;
					}
					final int nextX = coordinates.left + x;
					final int nextY = coordinates.middle + y;
					final int nextZ = coordinates.right + z;
					IntTriple neighborCoordinate = new IntTriple(nextX, nextY, nextZ);
					if (activeCoordinates.contains(neighborCoordinate)) {
						activeNeighbours.add(neighborCoordinate);
					}
				}
			}
		}
		return activeNeighbours;
	}

	private List<IntQuadruple> getActiveNeighbours4D(Set<IntQuadruple> activeCoordinates, IntQuadruple coordinates) {
		final List<IntQuadruple> activeNeighbours = new ArrayList<>();
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				for (int z = -1; z<=1; z++) {
					for (int w = -1; w<=1; w++) {
						if (x == 0 && y == 0 && z == 0 && w == 0) {
							continue;
						}
						final int nextX = coordinates.getX() + x;
						final int nextY = coordinates.getY() + y;
						final int nextZ = coordinates.getZ() + z;
						final int nextW = coordinates.getW() + w;
						IntQuadruple neighborCoordinate = new IntQuadruple(nextX, nextY, nextZ, nextW);
						if (activeCoordinates.contains(neighborCoordinate)) {
							activeNeighbours.add(neighborCoordinate);
						}
					}
				}
			}
		}
		return activeNeighbours;
	}

	private Set<IntQuadruple> executeCycle4D(Set<IntQuadruple> activeCoordinates) {
		Set<IntQuadruple> newActiveCoordinates = new HashSet<>(activeCoordinates);
		for (int x = -15; x <= 15; x++) {
			for (int y = -15; y<=15; y++) {
				for (int z = -15; z<=15; z++) {
					for (int w = -15; w<=15; w++) {
						IntQuadruple currentCoordinate = new IntQuadruple(x, y, z, w);
						List<IntQuadruple> activeNeighbours = getActiveNeighbours4D(
							activeCoordinates, currentCoordinate);
						if (activeCoordinates.contains(currentCoordinate)) {
							if (activeNeighbours.size() != 2 && activeNeighbours.size() != 3) {
								newActiveCoordinates.remove(currentCoordinate);
							}
						} else {
							if (activeNeighbours.size() == 3) {
								newActiveCoordinates.add(currentCoordinate);
							}
						}
					}
				}
			}
		}
		return newActiveCoordinates;
	}

}
