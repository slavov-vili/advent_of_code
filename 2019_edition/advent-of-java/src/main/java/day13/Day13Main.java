package day13;

import java.util.List;

import day02.Day02Main;
import day09.Day09Main;
import exceptions.InvalidArgumentException;
import exceptions.InvalidIntCodeException;

public class Day13Main {
	public static void main(String[] args) throws InvalidArgumentException, InvalidIntCodeException {
		ArcadeCabinet cabinet = getCabinet();
		String screen = cabinet.run("");
		int blockCount = cabinet.getBlockCount();
		
		while (!cabinet.computerIsHalted()) {
			System.out.println("Score: " + cabinet.getScore());
			System.out.println(screen);
			
			
		}
		
		System.out.printf("There were %d block tiles on the screen initially.\n", blockCount);
	}
	
	public static ArcadeCabinet getCabinet() throws InvalidArgumentException {
		return new ArcadeCabinet(Day09Main.getComputer(getInput()));
	}

	protected static List<Long> getInput() {
		return Day02Main.getInput(Day13Main.class);
	}
}
