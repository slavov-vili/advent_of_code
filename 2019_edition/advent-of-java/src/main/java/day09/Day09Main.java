package day09;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

import day02.Day02Main;
import day02.IntCodeInstructionProvider;
import day05.IntCodeInstructionParameterEvaluator;
import day07.Day07Main;
import day09.instructions.IntCodeInstructionRelativeBaseOffset;
import exceptions.InvalidArgumentException;

public class Day09Main {

	public static void main(String[] args) {
		try {
			Reader userInputReader = new InputStreamReader(System.in);
			Writer stdOutputWriter = new OutputStreamWriter(System.out);

			// Input = 1
			IntCodeComputer9 computer = getComputer();
			computer.run(userInputReader, stdOutputWriter);
			
			// Input = 2
			computer = getComputer();
			computer.run(userInputReader, stdOutputWriter);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static IntCodeComputer9 getComputer() throws InvalidArgumentException {
		return getComputer(getInput());
	}
	
	public static IntCodeComputer9 getComputer(List<Long> initialMemory) throws InvalidArgumentException {
		return new IntCodeComputer9(initialMemory, getInstructionProvider(), getModeHandler());
	}

	public static IntCodeInstructionParameterEvaluator getModeHandler() {
		return new IntCodeInstructionParameterEvaluator9();
	}

	public static IntCodeInstructionProvider getInstructionProvider() throws InvalidArgumentException {
		IntCodeInstructionProvider instructionProvider = Day07Main.getInstructionProvider();
		instructionProvider.addNewInstruction(new IntCodeInstructionRelativeBaseOffset(9L));
		return instructionProvider;
	}

	protected static List<Long> getInput() {
		return Day02Main.getInput(Day09Main.class);
	}
}
