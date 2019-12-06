package day05;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import day02.IntCodeComputer;
import day02.IntCodeInstructionProvider;
import day02.instructions.IntCodeInstructionAddition;
import day02.instructions.IntCodeInstructionMultiplication;
import day05.instructions.part1.IntCodeInstructionOutputValue;
import day05.instructions.part1.IntCodeInstructionStoreUserInput;
import day05.instructions.part2.IntCodeInstructionEquals;
import day05.instructions.part2.IntCodeInstructionJumpIfFalse;
import day05.instructions.part2.IntCodeInstructionJumpIfTrue;
import day05.instructions.part2.IntCodeInstructionLessThan;
import exceptions.InvalidIntCodeException;
import utils.AdventOfCodeUtils;

public class Day05Main {
    public static void main(String[] args) {
        try {
            // TODO: figure out how to take input multiple times
            // solve(getComputerA());
            // System.out.println("Solved A!");
            solve(getComputerB());

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    protected static void solve(IntCodeComputer computer) throws InvalidIntCodeException {
        computer.processInput(getInput(), 0);
        return;
    }

    protected static IntCodeComputer getComputerA() {
        IntCodeInstructionProvider instructionProvider = new IntCodeInstructionProvider();
        try {
            instructionProvider.addNewInstruction(new IntCodeInstructionAddition(1, 3));
            instructionProvider.addNewInstruction(new IntCodeInstructionMultiplication(2, 3));
            instructionProvider.addNewInstruction(new IntCodeInstructionStoreUserInput(3, 1));
            instructionProvider.addNewInstruction(new IntCodeInstructionOutputValue(4, 1));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return new IntCodeComputer(99, instructionProvider);
    }

    protected static IntCodeComputer getComputerB() {
        IntCodeInstructionProvider instructionProvider = new IntCodeInstructionProvider();
        try {
            instructionProvider.addNewInstruction(new IntCodeInstructionAddition(1, 3));
            instructionProvider.addNewInstruction(new IntCodeInstructionMultiplication(2, 3));
            instructionProvider.addNewInstruction(new IntCodeInstructionStoreUserInput(3, 1));
            instructionProvider.addNewInstruction(new IntCodeInstructionOutputValue(4, 1));
            instructionProvider.addNewInstruction(new IntCodeInstructionJumpIfTrue(5, 2));
            instructionProvider.addNewInstruction(new IntCodeInstructionJumpIfFalse(6, 2));
            instructionProvider.addNewInstruction(new IntCodeInstructionLessThan(7, 3));
            instructionProvider.addNewInstruction(new IntCodeInstructionEquals(8, 3));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return new IntCodeComputer(99, instructionProvider);
    }

    protected static List<Integer> getInput() {
        String intCodesString = AdventOfCodeUtils.readClasspathFileLines(Day05Main.class, "input.txt").get(0);
        return Arrays.asList(intCodesString.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());
    }
}
