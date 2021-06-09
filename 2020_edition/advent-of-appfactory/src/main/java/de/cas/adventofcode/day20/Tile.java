package de.cas.adventofcode.day20;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tile {
	public String id;
	private List<String> content;
	private boolean flipped;
	
	public static enum Side {
		TOP, RIGHT, BOT, LEFT;
		
		public Side getOpposite() {
			Side opposite;
			switch(this) {
			case TOP:
				opposite = BOT;
				break;
			case BOT:
				opposite = TOP;
				break;
			case RIGHT:
				opposite = LEFT;
				break;
			case LEFT:
				opposite = RIGHT;
				break;
			default:
				opposite = null;
			}
			return opposite;
		}
		
		public static Stream<Side> stream() {
			return Arrays.stream(Side.values());
		}
	}

	public Tile(Tile otherTile) {
		this(otherTile.getId(), otherTile.getContent());
	}
	
	public Tile(List<String> content) {
		this("Tile #0", content);
	}
	
	public Tile(String id, List<String> content) {
		this.id = id;
		this.content = new ArrayList<>(content);
		this.flipped = false;
	}
	
	public static Tile fromGrid(List<List<Tile>> tileGrid, String separator) {
		return tileGrid.stream()
				.map(rowOfTiles -> rowOfTiles.stream()
						.reduce((tile1, tile2) -> TileUtils.combineHorizontal(tile1, tile2, separator)).get())
				.reduce((tile1, tile2) -> TileUtils.combineVertical(tile1, tile2, separator)).get();
	}
	
	public void rotate() {
		List<String> newContent = new ArrayList<>();
		for (int i=0; i<content.get(0).length(); i++) {
			String lineFromColumn = "";
			
			for (int j=content.size()-1; j>=0; j--)
				lineFromColumn += content.get(j).charAt(i);

			newContent.add(lineFromColumn);
		}
		this.content = newContent;
	}
	
	public void flip() {
		List<String> newContent = new ArrayList<>();
		for (int i=this.content.size()-1; i>=0; i--)
			newContent.add(content.get(i));
		
		this.flipped = !this.isFlipped();
		this.content = newContent;
	}
	
	public int countChar(char charToFind) {
		int count = 0;
		for (int i=0; i<this.getRowCount(); i++)
			for (int j=0; j<this.getColCount(); j++)
				if (this.content.get(i).charAt(j) == charToFind)
					count++;
		return count;
	}
	
	public boolean bordersMatch(Map<Side, String> sideBorderMap) {
		return sideBorderMap.keySet().stream()
				.allMatch(side -> this.borderEquals(side, sideBorderMap.get(side)));
	}
	
	public boolean borderEquals(Side side, String toCheck) {
		return this.getBorder(side).equals(toCheck);
	}
	
	public List<String> getBorders() {
		return Side.stream()
				.map(this::getBorder)
				.collect(Collectors.toList());
	}
	
	public String getBorder(Side side) {
		String sideString = "";
		switch(side) {
		case TOP:
			sideString = this.getRow(0);
			break;
		case BOT:
			sideString = this.getRow(this.getRowCount()-1);
			break;
		case RIGHT:
			sideString = this.getCol(this.getColCount()-1);
			break;
		case LEFT:
			sideString = this.getCol(0);
			break;
		}
		return sideString;
	}
	
	public String removeRow(int indexToRemove) {
		return this.content.remove(indexToRemove);
	}
	
	public String removeColumn(int indexToRemove) {
		String removedColumn = "";
		for (int i=0; i<this.getRowCount(); i++) {
			String row = this.content.get(i);
			char removedChar = row.charAt(indexToRemove);
			removedColumn += removedChar;
			String prefix = row.substring(0, indexToRemove);
			String suffix = row.substring(indexToRemove+1);
			this.content.set(i, prefix+suffix);
		}
		return removedColumn;
	}
	
	public boolean isFlipped() {
		return this.flipped;
	}
	
	public String getRow(int rowIdx) {
		return this.content.get(rowIdx);
	}
	
	public String getCol(int colIdx) {
		return this.content.stream().reduce("",
				(col, curRow) -> col + curRow.charAt(colIdx),String::concat);
	}
	
	public int getRowCount() {
		return this.content.size();
	}
	
	public int getColCount() {
		return this.content.get(0).length();
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String newId) {
		this.id = newId;
	}
	
	public List<String> getContent() {
		return this.content;
	}
	
	public boolean equals(Object other) {
		boolean result = false;
		
		if (other instanceof Tile) {
			Tile otherTile = (Tile) other;
			result = this.id == otherTile.getId();
			result &= this.flipped == otherTile.isFlipped();
			result &= this.content.equals(otherTile.getContent());
		}
		return result;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Tile(");
		
		sb.append("id=");
		sb.append(this.id);
		sb.append(", ");
		
		sb.append("isFlipped=");
		sb.append(this.isFlipped());
		sb.append(", ");
		
		sb.append("content = ");
		sb.append(this.content.toString());
		
		sb.append(")");
		return sb.toString();
	}
	
	public void prettyPrint() {
		System.out.println(this.id + ":");
		System.out.println("isFlipped: " + this.isFlipped());
		this.content.forEach(System.out::println);
	}
}
