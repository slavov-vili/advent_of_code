package day05;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import day02.IntCodeComputer;
import day02.IntCodeInstructionProvider;
import day02.instructions.IntCodeInstructionAddition;
import day02.instructions.IntCodeInstructionMultiplication;
import day05.instructions.IntCodeInstructionOutputValue;
import day05.instructions.IntCodeInstructionStoreUserInput;
import exceptions.InvalidIntCodeException;
import utils.AdventOfCodeUtils;

public class Day05Main {
    public static void main(String[] args) {
        try {
            solveA(getComputerA());
            System.out.println("Solution to A is below HALT!");

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    protected static void solveA(IntCodeComputer computer) throws InvalidIntCodeException {
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

    protected static List<Integer> getInput() {
        String intCodesString = AdventOfCodeUtils.readClasspathFileLines(Day05Main.class, "input.txt").get(0);
        return Arrays.asList(intCodesString.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());
    }
}
