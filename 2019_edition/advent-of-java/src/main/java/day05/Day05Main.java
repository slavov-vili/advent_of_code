package day05;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

import day02.Day02Main;
import day02.IntCodeInstructionProvider;
import day05.A.IntCodeComputer5A;
import day05.A.instructions.IntCodeInstructionOutputValue;
import day05.A.instructions.IntCodeInstructionStoreInput;
import day05.B.IntCodeComputer5B;
import day05.B.instructions.IntCodeInstructionEquals;
import day05.B.instructions.IntCodeInstructionJumpIfFalse;
import day05.B.instructions.IntCodeInstructionJumpIfTrue;
import day05.B.instructions.IntCodeInstructionLessThan;
import day05.instructions.IntCodeInstructionAdditionWriting;
import day05.instructions.IntCodeInstructionMultiplicationWriting;
import exceptions.InvalidArgumentException;

public class Day05Main {
    public static void main(String[] args) {
        try {
            Reader userInputReader = new InputStreamReader(System.in);
            Writer stdOutputWriter = new OutputStreamWriter(System.out);
            
            IntCodeComputer5A computerA = getComputerA(getInput());
            computerA.run(userInputReader, stdOutputWriter);

            IntCodeComputer5B computerB = getComputerB(getInput());
            computerB.run(userInputReader, stdOutputWriter);
            
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

	public static IntCodeComputer5A getComputerA(List<Long> initialMemory) throws InvalidArgumentException {
        return new IntCodeComputer5A(initialMemory, getInstructionProviderA(), getModeHandler());
    }
    
    public static IntCodeInstructionProvider getInstructionProviderA() throws InvalidArgumentException {
    	IntCodeInstructionProvider instructionProvider = Day02Main.getDefaultInstructionProvider();
    	instructionProvider.replaceInstruction(new IntCodeInstructionAdditionWriting(1L));
    	instructionProvider.replaceInstruction(new IntCodeInstructionMultiplicationWriting(2L));
    	instructionProvider.addNewInstruction(new IntCodeInstructionStoreInput(3L));
        instructionProvider.addNewInstruction(new IntCodeInstructionOutputValue(4L));
        return instructionProvider;
    }
    
    public static IntCodeComputer5B getComputerB(List<Long> initialMemory) throws InvalidArgumentException {
        return new IntCodeComputer5B(initialMemory, getInstructionProviderB(), getModeHandler());
    }
    
    public static IntCodeInstructionProvider getInstructionProviderB() throws InvalidArgumentException {
    	IntCodeInstructionProvider instructionProvider = getInstructionProviderA();
        instructionProvider.addNewInstruction(new IntCodeInstructionJumpIfTrue(5L));
        instructionProvider.addNewInstruction(new IntCodeInstructionJumpIfFalse(6L));
        instructionProvider.addNewInstruction(new IntCodeInstructionLessThan(7L));
        instructionProvider.addNewInstruction(new IntCodeInstructionEquals(8L));
        return instructionProvider;
    }
    
    public static IntCodeInstructionParameterModeHandler getModeHandler() {
    	IntCodeInstructionParameterModeHandler modeHandler = new IntCodeInstructionParameterModeHandler();
    	modeHandler.addModeHandler(0L, (computer, parameter) -> computer.readFromMemory(parameter));
    	modeHandler.addModeHandler(1L, (computer, parameter) -> parameter);
    	return modeHandler;
    }

    protected static List<Long> getInput() {
        return Day02Main.getInput(Day05Main.class);
    }
}
