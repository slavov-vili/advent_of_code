package day02;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import day02.instructions.IntCodeInstructionAddition;
import day02.instructions.IntCodeInstructionMultiplication;
import exceptions.InvalidIntCodeException;
import utils.AdventOfCodeUtils;

public class Day02Main {

    public static void main(String[] args) {
        IntCodeComputer computer = getComputerDay02();

        try {
            int solutionA = solveA(computer);
            System.out.println("Solution A: " + solutionA);

            int solutionB = solveB(computer, 19690720);
            System.out.println("Solution B: " + solutionB);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    protected static int solveA(IntCodeComputer computer) throws InvalidIntCodeException {
        return computer.processInput(getInputA(), 0).get(0);
    }

    protected static int solveB(IntCodeComputer computer, int valueToFind) {
        int solutionNoun = 0;
        int solutionVerb = 0;

        for (int valueNoun = 0; valueNoun < 100; valueNoun++)
            for (int valueVerb = 0; valueVerb < 100; valueVerb++) {
                try {
                    int curSolution = computer.processInput(getInputFor(valueNoun, valueVerb), 0).get(0);
                    if (curSolution == valueToFind) {
                        solutionNoun = valueNoun;
                        solutionVerb = valueVerb;
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }

        return (100 * solutionNoun) + solutionVerb;
    }

    protected static IntCodeComputer getComputerDay02() {
        IntCodeInstructionProvider instructionProvider = new IntCodeInstructionProvider();
        try {
            instructionProvider.addNewInstruction(new IntCodeInstructionAddition(1, 2));
            instructionProvider.addNewInstruction(new IntCodeInstructionMultiplication(2, 2));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return new IntCodeComputer(99, instructionProvider);
    }

    protected static List<Integer> getInputA() {
        return getInputFor(12, 2);
    }

    protected static List<Integer> getInputFor(int valueAtPos1, int valueAtPos2) {
        List<Integer> inputCodes = getInput();
        inputCodes.set(1, valueAtPos1);
        inputCodes.set(2, valueAtPos2);

        return inputCodes;
    }

    protected static List<Integer> getInput() {
        String intCodesString = AdventOfCodeUtils.readClasspathFileLines(Day02Main.class, "input.txt").get(0);
        return Arrays.asList(intCodesString.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());
    }
}
