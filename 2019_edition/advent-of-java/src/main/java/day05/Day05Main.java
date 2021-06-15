package day05;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

import day02.Day02Main;
import day02.IntCodeComputerState;
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
            
            IntCodeComputer5A computerA = getComputerA(getInitialComputerState());
            computerA.run(userInputReader, stdOutputWriter);

            IntCodeComputer5B computerB = getComputerB(getInitialComputerState());
            computerB.run(userInputReader, stdOutputWriter);
            
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static IntCodeComputer5A getComputerA(IntCodeComputerState initialState) throws InvalidArgumentException {
        return new IntCodeComputer5A(initialState, getInstructionProviderA(), getModeHandlerA());
    }
    
    public static IntCodeInstructionProvider getInstructionProviderA() throws InvalidArgumentException {
    	IntCodeInstructionProvider instructionProvider = Day02Main.getDefaultInstructionProvider();
    	instructionProvider.replaceInstruction(new IntCodeInstructionAdditionWriting(1, 3));
    	instructionProvider.replaceInstruction(new IntCodeInstructionMultiplicationWriting(2, 3));
    	instructionProvider.addNewInstruction(new IntCodeInstructionStoreInput(3, 1));
        instructionProvider.addNewInstruction(new IntCodeInstructionOutputValue(4, 1));
        return instructionProvider;
    }
    
    public static IntCodeComputer5B getComputerB(IntCodeComputerState initialState) throws InvalidArgumentException {
        return new IntCodeComputer5B(initialState, getInstructionProviderB(), getModeHandlerA());
    }
    
    public static IntCodeInstructionProvider getInstructionProviderB() throws InvalidArgumentException {
    	IntCodeInstructionProvider instructionProvider = getInstructionProviderA();
        instructionProvider.addNewInstruction(new IntCodeInstructionJumpIfTrue(5, 2));
        instructionProvider.addNewInstruction(new IntCodeInstructionJumpIfFalse(6, 2));
        instructionProvider.addNewInstruction(new IntCodeInstructionLessThan(7, 3));
        instructionProvider.addNewInstruction(new IntCodeInstructionEquals(8, 3));
        return instructionProvider;
    }
    
    public static IntCodeInstructionParameterModeHandler<IntCodeComputer5A> getModeHandlerA() {
    	IntCodeInstructionParameterModeHandler<IntCodeComputer5A> modeHandler = new IntCodeInstructionParameterModeHandler<>();
    	modeHandler.addModeHandler(0, (computer, parameter) -> computer.getMemory().get(parameter));
    	modeHandler.addModeHandler(1, (computer, parameter) -> parameter);
    	return modeHandler;
    }

    public static IntCodeComputerState getInitialComputerState() {
        return Day02Main.createInitialComputerState(getInput());
    }

    protected static List<Integer> getInput() {
        return Day02Main.getInput(Day05Main.class);
    }
}
