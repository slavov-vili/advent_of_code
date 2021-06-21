package day13;

import java.awt.Point;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import datastructures.Grid2DInfinite;
import datastructures.IGrid2D;
import day05.A.IntCodeComputer5A;
import day09.IntCodeComputer9;
import exceptions.InvalidIntCodeException;

public class ArcadeCabinet {
	public static final String TILE_OUTPUT_PATTERN_STRING = String.format("(-?\\d+)%s(-?\\d+)%s(-?\\d+)%s",
			IntCodeComputer5A.OUTPUT_SEPARATOR, IntCodeComputer5A.OUTPUT_SEPARATOR, IntCodeComputer5A.OUTPUT_SEPARATOR);
	public static final Pattern TILE_OUTPUT_PATTERN = Pattern.compile(TILE_OUTPUT_PATTERN_STRING);
	
	public static final Integer EMPTY_DIGIT = 0;
	public static final Integer WALL_DIGIT = 1;
	public static final Integer BLOCK_DIGIT = 2;
	public static final Integer PADDLE_DIGIT = 3;
	public static final Integer BALL_DIGIT = 4;
	
	public static final Character EMPTY_CHAR = ' ';
	public static final Character WALL_CHAR = '#';
	public static final Character BLOCK_CHAR = '+';
	public static final Character PADDLE_CHAR = '=';
	public static final Character BALL_CHAR = 'o';
	
	public static final Point SEGMENT_DISPLAY = new Point(-1, 0);
	
	private IntCodeComputer9 computer;
	private IGrid2D<Integer> grid;
	private int score;

	public ArcadeCabinet(IntCodeComputer9 computer) {
		this.computer = computer;
		this.grid = new Grid2DInfinite<>(0);
		this.score = 0;
	}
	
	public String run(String input) throws InvalidIntCodeException {
        Writer outputWriter = new StringWriter();
        Reader inputReader = new StringReader(input);
        
        computer.run(inputReader, outputWriter);
        
        Map<Point, Integer> parsedOutput = this.parseOutput(outputWriter.toString());
        
        if (parsedOutput.containsKey(SEGMENT_DISPLAY)) {
        	this.setScore(parsedOutput.get(SEGMENT_DISPLAY));
        	parsedOutput.remove(SEGMENT_DISPLAY);
        }
        
        this.updateGrid(parsedOutput);
        return this.getPrintableGrid();
	}
	
	public int getBlockCount() {
		return (int) this.grid.count(tile -> tile.equals(BLOCK_DIGIT));
	}
	
	public String getPrintableGrid() {
		return this.grid.toString()
				.replace(Character.forDigit(EMPTY_DIGIT, 10), EMPTY_CHAR)
				.replace(Character.forDigit(WALL_DIGIT, 10), WALL_CHAR)
				.replace(Character.forDigit(BLOCK_DIGIT, 10), BLOCK_CHAR)
				.replace(Character.forDigit(PADDLE_DIGIT, 10), PADDLE_CHAR)
				.replace(Character.forDigit(BALL_DIGIT, 10), BALL_CHAR);
	}
	
	public Map<Point, Integer> parseOutput(String computerOutput) {
		Map<Point, Integer> updatedTiles = new HashMap<>();
		Matcher matcher = TILE_OUTPUT_PATTERN.matcher(computerOutput);
		while (matcher.find()) {
			int x = Integer.parseInt(matcher.group(1));
			int y = Integer.parseInt(matcher.group(2));
			int id = Integer.parseInt(matcher.group(3));
			updatedTiles.put(new Point(x, y), id);
		}
		return updatedTiles;
	}
	
	public void updateGrid(Map<Point, Integer> updatedTiles) {
		updatedTiles.forEach((position, id) -> this.grid.set(position, id));
	}
	
	public void putQuarters(int quarterCount) {
		this.computer.setMemoryAddress(0, quarterCount);
	}
	
	public boolean computerIsHalted() {
		return this.computer.isHalted();
	}
	
	public boolean computerIsWaitingForInput() {
		return this.computer.isWaitingForInput();
	}
	
	public int getScore() {
		return this.score;
	}
	
	public void setScore(int newScore) {
		this.score = newScore;
	}
}
