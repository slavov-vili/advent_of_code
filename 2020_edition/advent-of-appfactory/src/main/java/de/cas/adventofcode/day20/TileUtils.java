package de.cas.adventofcode.day20;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import de.cas.adventofcode.day20.Tile.Side;

public class TileUtils {

	public static Iterator<Tile> getStateIteratorFor(Tile tileToIterate) {
		return new TileStateIterator(tileToIterate);
	}

	public static Optional<Tile> findOrientationWhere(Tile tileToTransform, Map<Side, String> sideBorderMap) {
		Optional<Tile> orientation = Optional.empty();
		Iterator<Tile> iter = getStateIteratorFor(tileToTransform);
		while(iter.hasNext()) {
			Tile curState = iter.next();
			if (curState.bordersMatch(sideBorderMap)) {
				orientation = Optional.of(curState);
				break;
			}
		}
		return orientation;
	}

	public static Tile combineHorizontal(Tile tile1, Tile tile2, String separator) {
		List<String> newContent = IntStream.range(0, tile1.getContent().size())
				.mapToObj(i -> String.join(separator, tile1.getContent().get(i),
						tile2.getContent().get(i)))
				.collect(Collectors.toList());
		return new Tile(newContent);
	}
	
	public static Tile combineVertical(Tile tile1, Tile tile2, String separator) {
		List<String> newContent = new ArrayList<>(tile1.getContent());
		if (!separator.isEmpty())
			newContent.add(separator);
		newContent.addAll(tile2.getContent());
		return new Tile(newContent);
	}
	
	public static int findDragons(Tile tileToSearch, String head, String body, String tail) {
		int dragons = 0;
		Iterator<Tile> iter = getStateIteratorFor(tileToSearch);
		while(iter.hasNext()) {
			Tile curState = iter.next();
			dragons = countDragons(curState, head, body, tail);
			if (dragons != 0)
				break;
		}
		return dragons;
	}
	
	public static int countDragons(Tile tileToSearch, String head, String body, String tail) {
		int dragons = 0;
		Pattern bodyPattern = Pattern.compile(body);
		for (int i=0; i<tileToSearch.getRowCount()-3; i++) {
			List<String> nextRows = tileToSearch.getContent().subList(i, i+3);
			Matcher bodyMatcher = bodyPattern.matcher(nextRows.get(1));
			while (bodyMatcher.find()) {
				int startIndex = bodyMatcher.start();
				int endIndex = bodyMatcher.end();
				boolean headMatches = nextRows.get(0).substring(startIndex, endIndex).matches(head);
				boolean tailMatches = nextRows.get(2).substring(startIndex, endIndex).matches(tail);
				
				if (headMatches && tailMatches)
					dragons++;
			}
		}
		return dragons;
	}
	
	public static Tile trimBordersOf(Tile tileToTrim) {
		Tile trimmedTile = new Tile(tileToTrim);
		int lastRowIndex = trimmedTile.getRowCount()-1;
		int lastColIndex = trimmedTile.getColCount()-1;

		// Removing last first because else index is out of bounds
		trimmedTile.removeRow(lastRowIndex);
		trimmedTile.removeRow(0);
		trimmedTile.removeColumn(lastColIndex);
		trimmedTile.removeColumn(0);
		
		return trimmedTile;
	}
	
	public static boolean anyBorderEquals(Tile tileToCheck, String valueToCheck) {
		return Tile.Side.stream()
				.anyMatch(side -> tileToCheck.borderEquals(side, valueToCheck));
	}
	

	
	public static class TileStateIterator implements Iterator<Tile> {
		private final int maxState = 7;
		private Tile tile;
		private int state;

		public TileStateIterator(Tile toIterate) {
			this.tile = new Tile(toIterate);
			this.state = -1;
		}

		@Override
		public boolean hasNext() {
			return this.state <= maxState;
		}

		@Override
		public Tile next() {
			if (this.state != -1) {
				if (this.tile.isFlipped()) {
					this.tile.flip();
					this.tile.rotate();
				} else
					this.tile.flip();
			}
			this.state++;
			return this.tile;
		}
	}
}