package day11;

import java.util.List;

import day02.Day02Main;
import day02.IntCodeInstructionProvider;
import day05.IntCodeInstructionParameterEvaluator;
import day09.Day09Main;
import day09.IntCodeComputer9;
import day09.IntCodeInstructionParameterEvaluator9;
import exceptions.InvalidArgumentException;
import exceptions.InvalidIntCodeException;

public class Day11Main {

	public static void main(String[] args) throws InvalidArgumentException, InvalidIntCodeException {
		EmergencyHullPaintingRobot robot = new EmergencyHullPaintingRobot(getComputer());
		robot.run();
		int paintedPanelCount = robot.getPaintedPanels().size();
		System.out.printf("The robot painted %d panels\n", paintedPanelCount);
		
		robot = new EmergencyHullPaintingRobot(getComputer());
		robot.paintCurPanel(EmergencyHullPaintingRobot.WHITE);
		robot.run();
		String endGrid = robot.getPrintableGrid();
		System.out.println(endGrid);
	}

	public static IntCodeComputer9 getComputer() throws InvalidArgumentException {
		return new IntCodeComputer9(getInput(), getInstructionProvider(), getModeHandler());
	}

	public static IntCodeInstructionParameterEvaluator getModeHandler() {
		return new IntCodeInstructionParameterEvaluator9();
	}

	public static IntCodeInstructionProvider getInstructionProvider() throws InvalidArgumentException {
		return Day09Main.getInstructionProvider();
	}

	protected static List<Long> getInput() {
		return Day02Main.getInput(Day11Main.class);
	}
}
