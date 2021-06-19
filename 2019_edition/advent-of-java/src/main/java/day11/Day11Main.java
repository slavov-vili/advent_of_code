package day11;

import java.util.List;

import datastructures.Grid2DInfinite;
import datastructures.IGrid2D;
import day02.Day02Main;
import day09.Day09Main;
import exceptions.InvalidArgumentException;
import exceptions.InvalidIntCodeException;

public class Day11Main {

	public static void main(String[] args) throws InvalidArgumentException, InvalidIntCodeException {
		EmergencyHullPaintingRobot robot = getRobot();
		robot.run();
		int paintedPanelCount = robot.getPaintedPanels().size();
		
		robot = getRobot();
		robot.paintCurPanel(EmergencyHullPaintingRobot.WHITE_DIGIT);
		String endGrid = robot.run();
		
		System.out.printf("The robot painted %d panels\n", paintedPanelCount);
		System.out.println(endGrid);
	}
	
	public static EmergencyHullPaintingRobot getRobot() throws InvalidArgumentException {
		return new EmergencyHullPaintingRobot(Day09Main.getComputer(getInput()), getInfiniteGrid());
	}
	
	public static IGrid2D<Integer> getInfiniteGrid() {
		return new Grid2DInfinite<>(EmergencyHullPaintingRobot.BLACK_DIGIT);
	}

	protected static List<Long> getInput() {
		return Day02Main.getInput(Day11Main.class);
	}
}
