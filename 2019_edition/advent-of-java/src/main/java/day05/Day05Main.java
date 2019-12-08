package day05;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import day02.IntCodeComputer;
import day02.IntCodeComputerState;
import day02.IntCodeComputerState.ExecutionCode;
import day02.IntCodeInstructionProvider;
import day02.instructions.IntCodeInstructionAddition;
import day02.instructions.IntCodeInstructionHalt;
import day02.instructions.IntCodeInstructionMultiplication;
import day05.instructions.part1.IntCodeInstructionOutputValue;
import day05.instructions.part1.IntCodeInstructionStoreInput;
import day05.instructions.part2.IntCodeInstructionEquals;
import day05.instructions.part2.IntCodeInstructionJumpIfFalse;
import day05.instructions.part2.IntCodeInstructionJumpIfTrue;
import day05.instructions.part2.IntCodeInstructionLessThan;
import exceptions.InvalidIntCodeException;
import utils.AdventOfCodeUtils;

public class Day05Main {
    public static void main(String[] args) {
        try {
            Reader userInputReader = new InputStreamReader(System.in);
            Writer stdOutputWriter = new OutputStreamWriter(System.out);
            solve(getComputerA(getInitialComputerState()), userInputReader, stdOutputWriter);
            // System.out.println("Solved A!");
            solve(getComputerB(getInitialComputerState()), userInputReader, stdOutputWriter);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    protected static void solve(IntCodeComputer computer, Reader userInputReader, Writer stdOutputWriter)
            throws InvalidIntCodeException, IOException {
        computer.run(userInputReader, stdOutputWriter);
        return;
    }

    public static IntCodeComputer getComputerA(IntCodeComputerState initialState) {
        IntCodeInstructionProvider instructionProvider = new IntCodeInstructionProvider(new IntCodeInstructionHalt(99));
        try {
            instructionProvider.addNewInstruction(new IntCodeInstructionAddition(1, 3));
            instructionProvider.addNewInstruction(new IntCodeInstructionMultiplication(2, 3));
            instructionProvider.addNewInstruction(new IntCodeInstructionStoreInput(3, 1));
            instructionProvider.addNewInstruction(new IntCodeInstructionOutputValue(4, 1));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return new IntCodeComputer(initialState, instructionProvider);
    }

    public static IntCodeComputer getComputerB(IntCodeComputerState initialState) {
        IntCodeInstructionProvider instructionProvider = new IntCodeInstructionProvider(new IntCodeInstructionHalt(99));
        try {
            instructionProvider.addNewInstruction(new IntCodeInstructionAddition(1, 3));
            instructionProvider.addNewInstruction(new IntCodeInstructionMultiplication(2, 3));
            instructionProvider.addNewInstruction(new IntCodeInstructionStoreInput(3, 1));
            instructionProvider.addNewInstruction(new IntCodeInstructionOutputValue(4, 1));
            instructionProvider.addNewInstruction(new IntCodeInstructionJumpIfTrue(5, 2));
            instructionProvider.addNewInstruction(new IntCodeInstructionJumpIfFalse(6, 2));
            instructionProvider.addNewInstruction(new IntCodeInstructionLessThan(7, 3));
            instructionProvider.addNewInstruction(new IntCodeInstructionEquals(8, 3));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return new IntCodeComputer(initialState, instructionProvider);
    }

    public static IntCodeComputerState getInitialComputerState() {
        return new IntCodeComputerState(getInput(), 0, ExecutionCode.READY_FOR_NEXT);
    }

    protected static List<Integer> getInput() {
        String intCodesString = AdventOfCodeUtils.readClasspathFileLines(Day05Main.class, "input.txt").get(0);
        return Arrays.asList(intCodesString.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());
    }
}
