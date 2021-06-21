package day13;

import java.util.List;

import day02.Day02Main;
import day09.Day09Main;
import exceptions.InvalidArgumentException;
import exceptions.InvalidIntCodeException;

public class Day13Main {
	public static void main(String[] args) throws InvalidArgumentException, InvalidIntCodeException {
		int initialBlockCount = solveA();
		int score = solveB();
		
		System.out.printf("There were %d block tiles on the screen initially.\n", initialBlockCount);
		System.out.printf("The score at the end of the game was: %d.\n", score);
	}
	
	public static int solveA() throws InvalidArgumentException, InvalidIntCodeException {
		ArcadeCabinet cabinet = getCabinet();
		cabinet.run("");
		return cabinet.getBlockCount();
	}
	
	public static int solveB() throws InvalidArgumentException, InvalidIntCodeException {
		ArcadeCabinet cabinet = getCabinet();
		cabinet.putQuarters(2);
		String screen = "";
		String computerInput = "";
		
		while (!cabinet.computerIsHalted()) {
			System.out.println("Score: " + cabinet.getScore());
			System.out.println(screen);
			
			screen = cabinet.run(computerInput);
			computerInput = determineNextInput(cabinet).toString();
		}
		
		return cabinet.getScore();
	}
	
	public static String determineNextInput(ArcadeCabinet cabinet) {
		Integer ballX = (int) cabinet.getBallPosition().getX();
		Integer paddleX = (int) cabinet.getPaddlePosition().getX();
		String result = "0";
		if (ballX < paddleX)
			result = "-1";
		else if (ballX > paddleX)
			result = "1";
		return result;
	}
	
	public static ArcadeCabinet getCabinet() throws InvalidArgumentException {
		return new ArcadeCabinet(Day09Main.getComputer(getInput()));
	}

	protected static List<Long> getInput() {
		return Day02Main.getInput(Day13Main.class);
	}
}
