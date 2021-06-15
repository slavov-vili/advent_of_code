package day09;

import java.util.List;

import day02.Day02Main;
import day02.IntCodeInstructionProvider;
import day05.Day05Main;
import day05.IntCodeInstructionParameterModeHandler;
import day07.Day07Main;
import day09.instructions.IntCodeInstructionSetRelativeBase;
import exceptions.InvalidArgumentException;

public class Day09Main {

	public static void main(String[] args) {

	}

	public static IntCodeComputer9 getComputer() throws InvalidArgumentException {
    	return new IntCodeComputer9(getInput(), getInstructionProvider(), getModeHandler());
    }
	
	public static IntCodeInstructionParameterModeHandler getModeHandler() {
		IntCodeInstructionParameterModeHandler modeHandler = Day05Main.getModeHandler();
		modeHandler.addModeHandler(2, (computer, parameter) ->
			computer.readFromMemory(parameter + ((IntCodeComputer9)computer).getRelativeBase()));
		return modeHandler;
	}
    
    public static IntCodeInstructionProvider getInstructionProvider() throws InvalidArgumentException {
    	IntCodeInstructionProvider instructionProvider = Day07Main.getInstructionProvider();
    	instructionProvider.addNewInstruction(new IntCodeInstructionSetRelativeBase(9));
    	return instructionProvider;
    }

    protected static List<Integer> getInput() {
        return Day02Main.getInput(Day09Main.class);
    }
}
