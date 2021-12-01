package day02;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import day02.instructions.IntCodeInstructionAddition;
import day02.instructions.IntCodeInstructionHalt;
import day02.instructions.IntCodeInstructionMultiplication;
import exceptions.InvalidArgumentException;
import exceptions.InvalidIntCodeException;
import utils.AdventOfCodeUtils;

public class Day02Main {

    public static void main(String[] args) {
        try {
            Long solutionA = solveA();
            Long solutionB = solveB(19_690_720L);
            
            System.out.println("Solution A: " + solutionA);
            System.out.println("Solution B: " + solutionB);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    protected static Long solveA() throws InvalidArgumentException, InvalidIntCodeException {
        IntCodeComputer computer = getDefaultComputer(getInputA());
        computer.run();
        return computer.readFromMemory(0);
    }
    
    protected static Long solveB(Long valueToFind) throws InvalidArgumentException, InvalidIntCodeException {
        int solutionNoun = 0;
        int solutionVerb = 0;

        for (int valueNoun = 0; valueNoun < 100; valueNoun++)
            for (int valueVerb = 0; valueVerb < 100; valueVerb++) {
                try {
                	IntCodeComputer computer = getDefaultComputer(getInputFor(
                			valueNoun, valueVerb));
                	computer.run();
                    Long curSolution = computer.readFromMemory(0);
                    if (curSolution.equals(valueToFind)) {
                        solutionNoun = valueNoun;
                        solutionVerb = valueVerb;
                        break;
                    }
                } catch (InvalidIntCodeException e) {
                    continue;
                }
            }

        return (100L * solutionNoun) + solutionVerb;
    }

    protected static IntCodeComputer getDefaultComputer(List<Long> initialMemory) throws InvalidArgumentException {
        return new IntCodeComputer(initialMemory, getDefaultInstructionProvider());
    }
    
    public static IntCodeInstructionProvider getDefaultInstructionProvider() throws InvalidArgumentException{
    	IntCodeInstructionProvider instructionProvider = new IntCodeInstructionProvider(new IntCodeInstructionHalt(99L));
        instructionProvider.addNewInstruction(new IntCodeInstructionAddition(1L));
        instructionProvider.addNewInstruction(new IntCodeInstructionMultiplication(2L));
        return instructionProvider;
    }

    public static List<Long> getInputA() {
        return getInputFor(12, 2);
    }
    
    protected static <T extends Number> List<Long> getInputFor(T valueAtPos1, T valueAtPos2) {
        List<Long> inputCodes = getInput();
        inputCodes.set(1, valueAtPos1.longValue());
        inputCodes.set(2, valueAtPos2.longValue());

        return inputCodes;
    }

    public static List<Long> getInput() {
        return getInput(Day02Main.class);
    }
    
    public static List<Long> getInput(Class<?> sourceClass) {
    	String intCodesString = AdventOfCodeUtils.readInputLines(sourceClass).get(0);
        return Arrays.asList(intCodesString.split(",")).stream()
        		.map(Long::parseLong)
        		.collect(Collectors.toList());
    }
}