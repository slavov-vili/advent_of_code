package day11;

import java.awt.Point;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import datastructures.Direction2D;
import datastructures.Grid2DInfinite;
import datastructures.IGrid2D;
import day05.A.IntCodeComputer5A;
import exceptions.InvalidIntCodeException;
import utils.PointUtils;

public class EmergencyHullPaintingRobot {

	public static final Integer BLACK_DIGIT = 0;
	public static final Integer WHITE_DIGIT = 1;

	public static final Integer LEFT = 0;
	public static final Integer RIGHT = 1;
	
	public static final Character BLACK_CHAR = '.';
	public static final Character WHITE_CHAR = '#';
	
	private IntCodeComputer5A computer;
	private IGrid2D<Integer> grid;
	private Point curPosition;
	private Point curDirection;
	private List<Point> paintPath;

	public EmergencyHullPaintingRobot(IntCodeComputer5A computer, IGrid2D<Integer> grid) {
		this.computer = computer;
		this.grid = grid;
		this.curPosition = new Point();
		this.curDirection = Direction2D.NORTH;
		this.paintPath = new ArrayList<>();
	}
	
	public String run() throws InvalidIntCodeException {
		while (!this.computer.isHalted()) {
			Reader input = new StringReader(getCurPanel().toString());
	        StringWriter outputWriter = new StringWriter();
	        
	        computer.run(input, outputWriter);
	        
	        Point colorDirection = this.parseOutput(outputWriter.toString());
	        
	        this.paintCurPanel(colorDirection.x);
	        this.updateDirection(colorDirection.y);
	        this.move();
		}
		return this.getPrintableGrid();
	}
	
	public void paintCurPanel(int colorToPaint) {
		this.grid.set(this.getCurPosition(), colorToPaint);
		this.paintPath.add(this.getCurPosition());
	}
	
	public void updateDirection(int rotation) {
		Point oldDirection = this.getCurDirection();
		this.curDirection = (rotation == LEFT) ?
			Direction2D.getNextLeft(oldDirection) :
			Direction2D.getNextRight(oldDirection);
	}
	
	public void move() {
		this.curPosition = PointUtils.translate(this.getCurPosition(), this.getCurDirection());
	}
	
	// Point.x = color
	// Point.y = direction
	public Point parseOutput(String computerOutput) {
		String[] outputLines = computerOutput.split(IntCodeComputer5A.OUTPUT_SEPARATOR);
		return new Point(Integer.parseInt(outputLines[0]),
						 Integer.parseInt(outputLines[1]));
	}
	
	public String getPrintableGrid() {
		String flippedGrid = this.grid.toString()
				.replace(Character.forDigit(BLACK_DIGIT, 10), BLACK_CHAR)
				.replace(Character.forDigit(WHITE_DIGIT, 10), WHITE_CHAR);
		String[] flippedLines = flippedGrid.split(Grid2DInfinite.LINE_SEPARATOR);
		
		StringBuilder gridBuilder = new StringBuilder();
		for (String line : flippedLines)
			gridBuilder.insert(0, line + Grid2DInfinite.LINE_SEPARATOR);
		return gridBuilder.toString();
	}
	
	public Set<Point> getPaintedPanels() {
		return new HashSet<Point>(this.paintPath);
	}
	
	public Integer getCurPanel() {
		return getPanelAt(this.getCurPosition());
	}
	
	public Integer getPanelAt(Point position) {
		return this.grid.get(position);
	}
	
	public Point getCurPosition() {
		return this.curPosition;
	}
	
	public Point getCurDirection() {
		return this.curDirection;
	}
}
