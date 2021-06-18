package day11;

import java.awt.Point;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import day05.A.IntCodeComputer5A;
import exceptions.InvalidIntCodeException;
import utils.PointUtils;
import utils.TwoDDirection;

public class EmergencyHullPaintingRobot {

	public static final Integer BLACK = 0;
	public static final Integer WHITE = 1;

	public static final Integer LEFT = 0;
	public static final Integer RIGHT = 1;
	
	public static final Character BLACK_CHAR = '.';
	public static final Character WHITE_CHAR = '#';
	
	private IntCodeComputer5A computer;
	private Map<Point, Integer> grid;
	private Point curPosition;
	private Point curDirection;
	private List<Point> paintPath;

	public EmergencyHullPaintingRobot(IntCodeComputer5A computer) {
		this.computer = computer;
		this.grid = new HashMap<>();
		this.curPosition = new Point();
		this.curDirection = TwoDDirection.NORTH;
		this.paintPath = new ArrayList<>();
	}
	
	public void run() throws InvalidIntCodeException {
		while (!this.computer.isHalted()) {
			Reader input = new StringReader(getCurPanel().toString());
	        StringWriter outputWriter = new StringWriter();
	        
	        computer.run(input, outputWriter);
	        
	        Point colorDirection = this.parseOutput(outputWriter.toString());
	        
	        this.paintCurPanel(colorDirection.x);
	        this.updateDirection(colorDirection.y);
	        this.move();
		}
	}
	
	public void paintCurPanel(int colorToPaint) {
		this.grid.put(this.getCurPosition(), colorToPaint);
		this.paintPath.add(this.getCurPosition());
	}
	
	public void updateDirection(int rotation) {
		Point oldDirection = this.getCurDirection();
		this.curDirection = (rotation == LEFT) ?
			TwoDDirection.getNextLeft(oldDirection) :
			TwoDDirection.getNextRight(oldDirection);
	}
	
	public void move() {
		this.curPosition = PointUtils.translate(this.getCurPosition(), this.getCurDirection());
	}
	
	// Point.x = color
	// Point.y = direction
	public Point parseOutput(String computerOutput) {
		String[] outputLines = computerOutput.split("\\n");
		return new Point(Integer.parseInt(outputLines[0]),
						 Integer.parseInt(outputLines[1]));
	}
	
	public String getPrintableGrid() {
		int maxX = this.getMax(Point::getX);
		int maxY = this.getMax(Point::getY);
		
		int minX = this.getMin(Point::getX);
		int minY = this.getMin(Point::getY);
		
		StringBuilder gridBuilder = new StringBuilder();
		for (int x=minX; x<=maxX; x++) {
			for (int y=minY; y<=maxY; y++) {
				gridBuilder.append(getCharAt(new Point(x, y)));
			}
			gridBuilder.append("\n");
		}
		return gridBuilder.toString();
	}
	
	// TODO: add a getter for position in grid
	private Character getCharAt(Point position) {
		return (this.getPanelAt(position) == BLACK) ?
				BLACK_CHAR : WHITE_CHAR; 
	}
	
	private int getMax(Function<Point, Double> coordGetter) {
		return this.grid.keySet().stream()
				.mapToInt(curPoint -> coordGetter.apply(curPoint).intValue())
				.max().getAsInt();
	}
	
	private int getMin(Function<Point, Double> coordGetter) {
		return this.grid.keySet().stream()
				.mapToInt(curPoint -> coordGetter.apply(curPoint).intValue())
				.min().getAsInt();
	}
	
	public Set<Point> getPaintedPanels() {
		return new HashSet<Point>(this.paintPath);
	}
	
	public Integer getCurPanel() {
		return getPanelAt(this.getCurPosition());
	}
	
	public Integer getPanelAt(Point position) {
		return this.grid.getOrDefault(position, BLACK);
	}
	
	public Point getCurPosition() {
		return this.curPosition;
	}
	
	public Point getCurDirection() {
		return this.curDirection;
	}
}
