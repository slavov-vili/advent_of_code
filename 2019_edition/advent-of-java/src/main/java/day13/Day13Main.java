package day13;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
		String screen = cabinet.run("");
		return cabinet.getBlockCount();
	}
	
	public static int solveB() throws InvalidArgumentException, InvalidIntCodeException {
		ArcadeCabinet cabinet = getCabinet();
		cabinet.putQuarters(2);
		String computerInput = getSolutionB();
		String screen = "";
		
		List<String> curPath = new ArrayList<>();
		
		while (!cabinet.computerIsHalted()) {
			System.out.println("Score: " + cabinet.getScore());
			System.out.println(screen);
			
			if (cabinet.computerIsWaitingForInput()) {
				String userInput = readUserInput();
				curPath.add(String.format("\"%s\"", userInput));
				computerInput = translateUserInput(userInput);
			}
			
			screen = cabinet.run(computerInput);
		}
		System.out.println(curPath);
		return cabinet.getScore();
	}
	
	private static String getSolutionB() {
		List<String> translatedSolution = List.of("s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "a", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "a", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "d", "d", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "d", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "a", "s", "s", "a", "s", "s", "s", "s"
				, "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d"
				, "s", "s", "s", "s", "a", "a", "s", "s", "d", "d", "d", "d", "d", "a", "a", "a", "a"
				, "a", "a", "a", "a", "a", "d", "d", "d", "d", "s", "s", "s", "a", "a", "s", "s", "s"
				, "s", "s", "s", "s", "s", "d", "d", "d", "d", "d", "d", "s", "s", "s", "s", "s", "s"
				, "s", "s", "a", "a", "d", "d", "d", "s", "a", "a", "a", "a", "a", "a", "a", "a", "s"
				, "s", "s", "s", "s", "s", "s", "a", "a", "a", "a", "s", "s", "s", "s", "s", "s", "s"
				, "d", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "a", "a"
				, "a", "d", "d", "d", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "a"
				, "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "s"
				, "a", "s", "s", "s", "s", "s", "s", "a", "d", "s", "s", "d", "d", "d", "d", "a", "a"
				, "a", "a", "d", "d", "d", "d", "a", "a", "a", "a", "a", "s", "s", "s", "d", "d", "d"
				, "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "s", "s", "s", "d", "a", "a", "a"
				, "d", "d", "s", "a", "a", "a", "a", "a", "a", "a", "a", "a", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "d", "d", "d", "a", "s", "s", "s", "s", "a", "s", "a", "a", "a"
				, "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d"
				, "d", "d", "d", "d", "d", "d", "d", "d", "d", "a", "a", "a", "a", "a", "a", "a", "a"
				, "a", "a", "a", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "a", "d", "d", "a"
				, "a", "a", "a", "a", "a", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "d"
				, "d", "d", "d", "s", "d", "d", "s", "s", "a", "a", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "a", "a", "a", "a", "a", "a", "a", "a", "a"
				, "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a"
				, "a", "a", "a", "a", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "a", "a"
				, "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "d", "d", "d"
				, "d", "d", "s", "a", "a", "a", "d", "d", "d", "a", "s", "s", "s", "s", "s", "s", "a"
				, "a", "a", "d", "d", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "a", "s", "a", "a", "s", "s", "d", "d", "d"
				, "d", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "d", "d", "s", "s", "s"
				, "s", "s", "s", "a", "s", "a", "s", "a", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "d", "d", "d", "d", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "a", "a", "a", "a", "a", "a", "a"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "d", "d", "d", "s", "s"
				, "a", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "d", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "d", "d", "d", "d", "s", "s"
				, "a", "a", "a", "a", "a", "s", "s", "s", "s", "s", "a", "s", "s", "a", "s", "s", "s"
				, "s", "d", "d", "d", "d", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "a", "a", "a", "a", "s", "s", "s", "s", "d", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "d", "d", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "d", "d", "d", "d", "d", "d", "d", "d", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "d", "d", "d", "d", "d"
				, "d", "d", "d", "d", "d", "d", "d", "d", "s", "s", "s", "s", "a", "a", "a", "a", "a"
				, "a", "a", "a", "a", "a", "a", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "d", "d", "d", "d", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "a", "a"
				, "a", "a", "s", "s", "s", "s", "s", "d", "d", "d", "d", "d", "d", "d", "s", "a", "a"
				, "a", "s", "s", "s", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "a", "a", "a", "a", "a", "a"
				, "a", "a", "a", "a", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "d", "d", "d", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "a", "a", "a", "a", "d", "d", "d", "d", "d", "d", "d", "d", "a", "a"
				, "a", "a", "a", "a", "a", "a", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "d", "d", "s", "s", "s"
				, "d", "d", "s", "s", "s", "s", "s", "s", "s", "a", "a", "a", "d", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "d", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "a", "s", "s", "s", "s", "d", "d", "d", "d", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "a", "a", "s", "s", "s", "a", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "a", "s", "s", "s", "s", "d", "d", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "a", "a", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "d", "s", "s", "a", "a", "a", "a", "a", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "d", "d", "d", "d", "d"
				, "d", "d", "d", "d", "d", "d", "d", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "d", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "a", "a", "s", "s", "s", "s", "s", "s", "s", "s", "d", "a"
				, "a", "a", "a", "a", "a", "a", "a", "s", "s", "s", "s", "s", "d", "d", "d", "d", "d"
				, "d", "d", "d", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "d", "d", "s", "s", "s", "s", "s", "s", "s", "s", "a", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "d", "d", "s", "s", "s", "s", "s", "s", "a", "a", "a", "a"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "d", "d", "d", "d", "d"
				, "s", "s", "s", "s", "s", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a"
				, "a", "a", "a", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "a", "a", "a", "a", "a", "a", "s"
				, "s", "s", "s", "s", "s", "d", "d", "d", "d", "d", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "d", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "a", "a", "a", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "d", "d", "d", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "d", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "d"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "s"
				, "s", "s", "s", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a"
				, "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a"
				, "a", "a", "s", "s", "s", "s", "a", "s", "s", "d", "d", "d", "d", "d", "d", "d", "d"
				, "d", "d", "d", "d", "d", "d", "d", "d", "s", "s", "a", "a", "a", "a", "a", "a", "a"
				, "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "d", "d", "d", "d", "d", "d", "d"
				, "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d"
				, "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "s", "s", "s", "a", "a"
				, "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a"
				, "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a"
				, "a", "s", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "d", "d", "d", "d", "d"
				, "d", "d", "d", "d", "d", "d", "d", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "a", "d", "d", "d", "d", "s", "a", "a", "a", "a", "d", "d", "d", "d"
				, "d", "d", "d", "d", "d", "s", "s", "s", "s", "s", "s", "s", "s", "a", "a", "a", "a"
				, "a", "a", "a", "a", "s", "s", "s", "s", "s", "s", "s", "d", "d", "d", "d", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "a", "s", "a"
				, "a", "a", "a", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "a", "a", "a", "a", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "d", "d", "d", "d", "s", "s", "s", "s", "a", "a", "a", "s", "s", "s", "s", "s", "s"
				, "s", "s", "a", "a", "a", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "d", "s", "s", "a", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "d", "d", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "a", "a", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "d", "d", "d", "d", "d", "d", "d"
				, "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d"
				, "d", "d", "d", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "a", "a", "a", "a", "a"
				, "a", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "s", "s", "s", "s", "a", "a"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "d", "d", "d", "d", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "d", "d", "d", "d", "s", "s", "s", "s", "s"
				, "s", "a", "d", "s", "s", "s", "s", "s", "d", "d", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "a", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "a", "a", "a", "a", "a", "a", "a", "a"
				, "a", "a", "a", "a", "d", "d", "d", "d", "s", "s", "s", "s", "s", "s", "s", "s", "d"
				, "d", "d", "d", "d", "d", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "d", "d", "s", "s", "a", "a", "a", "a", "a", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "a", "a", "a", "a", "a", "a", "s", "s", "d", "d", "d", "s", "s"
				, "s", "s", "s", "s", "s", "s", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d"
				, "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "s", "s", "a", "a", "a", "a", "a"
				, "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "s", "s", "s", "a", "a"
				, "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "s"
				, "s", "s", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d"
				, "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d"
				, "d", "d", "d", "s", "s", "s", "s", "s", "s", "a", "a", "a", "a", "a", "a", "a", "a"
				, "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a"
				, "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "s", "d", "d", "d", "d", "d", "d"
				, "d", "d", "d", "d", "s", "s", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "s"
				, "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a"
				, "a", "a", "a", "a", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d"
				, "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d"
				, "d", "d", "d", "d", "d", "d", "s", "s", "s", "s", "a", "a", "a", "a", "a", "a", "a"
				, "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a"
				, "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "d", "d", "d", "s"
				, "s", "s", "s", "s", "s", "s", "s", "d", "d", "d", "d", "d", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "a", "s", "d", "d", "d"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "a"
				, "a", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "d", "s", "s", "s", "s", "s"
				, "d", "d", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d"
				, "d", "d", "d", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a"
				, "a", "a", "a", "a", "a", "a", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d"
				, "s", "s", "s", "s", "s", "s", "d", "d", "d", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "d", "d"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "a", "a", "a", "a", "a", "a", "a", "a", "s", "s", "s", "s"
				, "s", "s", "d", "d", "d", "d", "d", "d", "s", "d", "d", "d", "d", "a", "a", "a", "a"
				, "a", "a", "a", "a", "a", "a", "s", "s", "s", "s", "s", "s", "s", "s", "s", "a", "a"
				, "a", "a", "a", "a", "a", "a", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d"
				, "d", "d", "d", "d", "d", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a"
				, "a", "a", "a", "a", "a", "a", "a", "a", "a", "d", "d", "d", "d", "d", "d", "d", "d"
				, "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d"
				, "d", "s", "s", "s", "s", "s", "s", "s", "d", "d", "s", "s", "s", "s", "s", "s", "s"
				, "s", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a"
				, "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "d", "d", "d", "d"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "d", "s"
				, "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d"
				, "d", "d", "d", "d", "d", "d", "d", "d", "a", "a", "a", "a", "a", "a", "a", "a", "a"
				, "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "a", "a", "a", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d"
				, "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "a"
				, "a", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "a", "a", "a", "a", "a"
				, "s", "s", "s", "s", "s", "s", "s", "a", "a", "a", "a", "s", "a", "a", "a", "a", "a"
				, "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d"
				, "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "s", "s", "s", "s"
				, "a", "a", "s", "s", "s", "s", "s", "s", "a", "a", "a", "a", "a", "a", "a", "a", "a"
				, "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "s", "s"
				, "a", "a", "a", "s", "s", "s", "d", "d", "d", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "a", "a"
				, "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "s", "d", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "d", "d", "d", "d", "s"
				, "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d"
				, "d", "d", "d", "d", "d", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a"
				, "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "s", "s", "s"
				, "s", "s", "s", "s", "d", "d", "d", "d", "d", "d", "s", "s", "s", "s", "s", "d", "d"
				, "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a"
				, "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "s", "d", "d", "d", "d"
				, "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d"
				, "d", "d", "d", "d", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "a", "s", "a", "a", "a", "a", "a"
				, "a", "a", "a", "a", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "a", "a", "a"
				, "a", "a", "a", "a", "a", "a", "s", "s", "s", "s", "s", "s", "s", "s", "a", "a", "a"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "d"
				, "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d"
				, "d", "d", "d", "d", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a"
				, "a", "a", "a", "a", "a", "a", "a", "a", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d"
				, "d", "d", "d", "d", "d", "s", "s", "s", "s", "s", "s", "d", "d", "d", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "a", "a", "a", "a", "a"
				, "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "s", "s", "s"
				, "s", "s", "s", "s", "a", "s", "s", "s", "s", "s", "d", "d", "d", "d", "d", "d", "d"
				, "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "d", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "a", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "a", "a", "a", "a", "a", "a", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "d", "d", "d", "d", "d", "s", "s", "s", "s", "s", "s", "s"
				, "s", "d", "d", "d", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a"
				, "a", "a", "a", "a", "a", "a", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "d", "d", "d", "d", "d", "d", "d"
				, "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "s", "s", "s", "s", "s"
				, "d", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a"
				, "a", "a", "a", "a", "a", "a", "a", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s"
				, "s", "s", "s", "d", "d", "d", "s", "s", "s", "s", "s", "s", "s", "d", "d", "d", "d"
				, "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "s", "s", "s"
				, "s", "s", "s", "s", "s"
				).stream()
				.map(Day13Main::translateUserInput)
				.collect(Collectors.toList());
		return String.join(" ", translatedSolution);
	}
	
	public static String translateUserInput(String userInput) {
		String result;
		if (userInput.equals("a"))
			result = "-1";
		else if (userInput.equals("d"))
			result = "1";
		else
			result = "0";
		return result;	
	}
	
	public static String readUserInput() {
		System.out.println("Move joystick (a=left, s=neutral, d=right):");
		Scanner inputScanner = new Scanner(System.in);
		return inputScanner.next();
	}
	
	public static ArcadeCabinet getCabinet() throws InvalidArgumentException {
		return new ArcadeCabinet(Day09Main.getComputer(getInput()));
	}

	protected static List<Long> getInput() {
		return Day02Main.getInput(Day13Main.class);
	}
}
