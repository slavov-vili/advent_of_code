package de.cas.adventofcode.day20;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import de.cas.adventofcode.day20.Tile.Side;
import de.cas.adventofcode.shared.Day;

public class Day20Velislav extends Day<Long> {
	private static final String TILE_LINE = "Tile #?(\\d+):?";
	private static final String DRAGON_HEAD = "..................#.";
	private static final String DRAGON_BODY = "#....##....##....###";
	private static final String DRAGON_TAIL = ".#..#..#..#..#..#...";
	private static final int HASHTAGS_PER_DRAGON = 15;
	
	protected Day20Velislav() {
		super(20);
	}

	public static void main(final String[] args) {
		new Day20Velislav().run();
	}

	@Override
	public Long solvePart1(List<String> input) {
		if (input.isEmpty())
			return null;
		input.add("");
		Map<String, Tile> idTileMap = parseTiles(input);
		List<Tile> cornerTiles = findTilesWithNFittingBorders(idTileMap, 2);
		
		return cornerTiles.stream()
				.mapToLong(tile -> (long)extractTileId(tile.getId()))
				.reduce(1L, (result, id) -> result * id);
	}

	private List<Tile> findTilesWithNFittingBorders(Map<String, Tile> idTileMap, int fittingBordersCount) {
		List<Tile> validTiles = new ArrayList<>();
		for (String id : idTileMap.keySet()) {
			Tile tile = idTileMap.get(id);
			Collection<Tile> otherTiles = getOtherTiles(idTileMap, tile);

			if (findFittingSides(tile, otherTiles).size() == fittingBordersCount)
				validTiles.add(tile);
		}
		return validTiles;
	}

	@Override
	public Long solvePart2(List<String> input) {
		if (input.isEmpty())
			return null;
		input.add("");
		Map<String, Tile> idTileMap = parseTiles(input);
		
		Tile aCornerTile = findTilesWithNFittingBorders(idTileMap, 2).get(0);
		Collection<Tile> otherTiles = excludeEntries(idTileMap, Set.of(aCornerTile.getId())).values();
		List<Side> fittingSides = findFittingSides(aCornerTile, otherTiles);
		
		int gridSize = (int)Math.sqrt(idTileMap.size());
		List<List<Tile>> tileGrid = reassembleTileGrid(idTileMap, aCornerTile, fittingSides, gridSize);
		tileGrid = tileGrid.stream()
			.map(row -> row.stream()
					.map(TileUtils::trimBordersOf)
					.collect(Collectors.toList()))
			.collect(Collectors.toList());
		
		Tile gridAsTile = Tile.fromGrid(tileGrid, "");
		gridAsTile.setId("Grid #0");
		gridAsTile.prettyPrint();
		
		int totalHashtags = gridAsTile.countChar('#');
		int dragonCount = TileUtils.findDragons(gridAsTile, DRAGON_HEAD, DRAGON_BODY, DRAGON_TAIL);
		
		return (long)(totalHashtags - (HASHTAGS_PER_DRAGON * dragonCount));
	}

	private List<List<Tile>> reassembleTileGrid(Map<String, Tile> idTileMap,
			Tile cornerTile, List<Side> fittingSidesOfCorner, int gridSize) {
		List<List<Tile>> grid = new ArrayList<>();
		Map<String, Tile> possibleTileMap = new HashMap<>(idTileMap);
		Side mainSide = fittingSidesOfCorner.get(0);
		Side secondarySide = fittingSidesOfCorner.get(1);
		Side nextFittingSide = mainSide.getOpposite();
		String nextFittingValue = cornerTile.getBorder(nextFittingSide);
		
		for (int i=0; i<gridSize; i++) {
			Map<Side, String> nextTileRequirements = createTileRequirements(
					List.of(nextFittingSide), List.of(nextFittingValue));
			List<Tile> curLine = findNextLine(possibleTileMap, nextTileRequirements,
					secondarySide.getOpposite(), gridSize);	
			grid.add(curLine);
			
			Set<String> curLineIds = curLine.stream()
					.map(Tile::getId)
					.collect(Collectors.toSet());
			curLineIds.forEach(possibleTileMap::remove);
			
			nextFittingValue = curLine.get(0).getBorder(mainSide);
		}

		if (Tile.Side.TOP.equals(secondarySide) || Tile.Side.LEFT.equals(secondarySide))
			grid.forEach(Collections::reverse);
		if (Tile.Side.TOP.equals(mainSide) || Tile.Side.LEFT.equals(mainSide))
			Collections.reverse(grid);
		
		return grid;
	}
	
	private List<Tile> findNextLine(Map<String, Tile> tilesToSearch,
			Map<Side, String> initialTileRequirements, Side consecutiveSide, int lineSize) {
		List<Tile> nextLine = new ArrayList<>();
		Map<Side, String> nextTileRequirements = initialTileRequirements;
		Map<String, Tile> possibleTileMap = new HashMap<>(tilesToSearch);
		
		for (int i=0; i<lineSize; i++) {
			Map<Side, String> requirementsCopy = new HashMap<>(nextTileRequirements);
			List<Tile> nextTiles = possibleTileMap.values().stream()
					.map(tile -> TileUtils.findOrientationWhere(tile, requirementsCopy))
					.filter(Optional::isPresent)
					.map(Optional::get)
					.collect(Collectors.toList());
			
			if (nextTiles.size() != 1)
				throw new RuntimeException("Invalid number of next tiles: " + nextTiles.size());
			
			Tile curTile = nextTiles.get(0);
			nextLine.add(curTile);
			possibleTileMap.remove(curTile.getId());
			
			String nextExpectedBorder = curTile.getBorder(consecutiveSide.getOpposite()); 
			nextTileRequirements = createTileRequirements(List.of(consecutiveSide),
					List.of(nextExpectedBorder));
		}
		
		return nextLine;
	}
	
	private List<Tile.Side> findFittingSides(Tile tile, Collection<Tile> tilesToFit) {
		return Tile.Side.stream()
				.filter(side -> !findFittingTiles(tile.getBorder(side), tilesToFit).isEmpty())
				.collect(Collectors.toList());
	}
	
	private Set<Tile> findFittingTiles(String border, Collection<Tile> tilesToFit) {
		Set<Tile> fittingTiles = new HashSet<>();
		// TODO: Pass a map where a list of all states is the value instead
		Map<Tile, Iterator<Tile>> tileIteratorMap = new HashMap<>();
		
		for (Tile tile : tileIteratorMap.keySet()) {
			Iterator<Tile> iter = tileIteratorMap.get(tile);
			while (iter.hasNext())
				if (TileUtils.anyBorderEquals(iter.next(), border)) {
					fittingTiles.add(tile);
					break;
				}
		}
		
		return fittingTiles;
	}
	
	private static Map<String, Tile> parseTiles(List<String> lines) {
		Map<String, Tile> tiles = new HashMap<>();
		String curId = "Tile #0";
		List<String> curContent = new ArrayList<>();
		for (String line : lines) {
			if (line.matches(TILE_LINE))
				curId = String.format("Tile #%d", extractTileId(line));
			else if (line.isEmpty()) {
				tiles.put(curId, new Tile(curId, curContent));
				curContent = new ArrayList<>();
			} else {
				curContent.add(line);
			}
		}
		return tiles;
	}
	
	private static Integer extractTileId(String line) {
		return Integer.parseInt(line.replaceAll(TILE_LINE, "$1"));
	}
	
	private Map<Tile.Side, String> createTileRequirements(List<Tile.Side> sides, List<String> values) {
		return IntStream.range(0, sides.size())
				.boxed()
				.collect(Collectors.toMap(sides::get, values::get));
	}

	private Collection<Tile> getOtherTiles(Map<String, Tile> idTileMap, Tile tile) {
		Set<String> otherTileIDs = getSetWithout(idTileMap.keySet(), Set.of(tile.getId()));
		Collection<Tile> otherTiles = extractEntries(idTileMap, otherTileIDs).values();
		return otherTiles;
	}
	
	private <K,V> Map<K, V> excludeEntries(Map<K, V> originalMap, Set<K> keysToExclude) {
		return extractEntries(originalMap, getSetWithout(originalMap.keySet(), keysToExclude));
	}
	
	private <K,V> Map<K, V> extractEntries(Map<K, V> originalMap, Set<K> keysToExtract) {
		return keysToExtract.stream()
				.collect(Collectors.toMap(Function.identity(), originalMap::get));
	}

	private <T> Set<T> getSetWithout(Set<T> originalSet, Set<T> elementsToRemove) {
		Set<T> setWithoutElements = new HashSet<>(originalSet);
		setWithoutElements.removeAll(elementsToRemove);
		return setWithoutElements;
	}

}
