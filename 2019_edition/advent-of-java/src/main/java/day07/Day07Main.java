package day07;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import day02.Day02Main;
import day02.IntCodeComputerState;
import day02.IntCodeInstructionProvider;
import day05.Day05Main;
import day07.instructions.IntCodeInstructionStoreInput7;
import exceptions.InvalidArgumentException;
import utils.ListUtils;

public class Day07Main {

    public static void main(String[] args) {
        int solutionA = solveA(0, 4);
        int solutionB = solveB(5, 9);
        
        System.out.println("Solution A: " + solutionA);
        System.out.println("Solution B: " + solutionB);

    }

    protected static int solveA(int minSettingValue, int maxSettingValue) {
        return getLargestAmplifierChainOutput(prepareAmplifierPermutations(minSettingValue, maxSettingValue).stream()
                .map(amplifierChain -> runAmplifierChain(0, amplifierChain, true))
                .collect(Collectors.toList()));
    }
    
    protected static int solveB(int minSettingValue, int maxSettingValue) {
        return getLargestAmplifierChainOutput(prepareAmplifierPermutations(minSettingValue, maxSettingValue).stream()
                .map(amplifierChain -> runAmplifierLoop(0, amplifierChain))
                .collect(Collectors.toList()));
    }

    protected static List<List<IntCodeAmplifier>> prepareAmplifierPermutations(int minSettingValue,
            int maxSettingValue) {
        List<Integer> amplifierSettings = ListUtils.generateRange(minSettingValue, maxSettingValue);
        List<List<Integer>> settingPermutations = ListUtils.findPermutations(amplifierSettings.size(),
        		amplifierSettings, new ArrayList<>());
        List<List<IntCodeAmplifier>> amplifierPermutations = settingPermutations.stream()
        		.map(permutation -> generateAmplifiers(permutation))
        		.collect(Collectors.toList());
        return amplifierPermutations;
    }

    protected static List<IntCodeAmplifier> runAmplifierChain(int firstAmplifierInput,
            List<IntCodeAmplifier> initialAmplifierChain, boolean isInitialAmplify) {
        List<IntCodeAmplifier> endAmplifierChain = new ArrayList<>(initialAmplifierChain);
        String curAmplifierInput = String.valueOf(firstAmplifierInput);
        for (IntCodeAmplifier amplifier : endAmplifierChain)
        	try {
        		if (isInitialAmplify)
        			curAmplifierInput = String.valueOf(amplifier.getPhaseSetting())
        				+ " " + curAmplifierInput;
        		curAmplifierInput = String.valueOf(amplifier.amplify(curAmplifierInput).get());
        	} catch (Exception e) {
        		throw new RuntimeException(e);
        	}

        return endAmplifierChain;
    }

    protected static List<IntCodeAmplifier> runAmplifierLoop(int initialLoopInput,
            List<IntCodeAmplifier> initialAmplifierChain) {
        List<IntCodeAmplifier> curAmplifierChain = initialAmplifierChain;
        int curChainInput = initialLoopInput;
        boolean isInitialAmplify = true;
        while (!ListUtils.getLastElement(curAmplifierChain).computerIsHalted()) {
            curAmplifierChain = runAmplifierChain(curChainInput, curAmplifierChain, isInitialAmplify);
            curChainInput = ListUtils.getLastElement(curAmplifierChain).getOutput().get();
            isInitialAmplify = false;
        }
        return curAmplifierChain;
    }

    protected static int getLargestAmplifierChainOutput(List<List<IntCodeAmplifier>> amplifierChains) {
        return amplifierChains.stream()
        		.mapToInt(amplifierChain -> ListUtils.getLastElement(amplifierChain).getOutput().get())
                .max().getAsInt();
    }

    protected static List<IntCodeAmplifier> generateAmplifiers(List<Integer> amplifierSettings) {
        List<IntCodeAmplifier> amplifiers = new ArrayList<>();
        for (Integer setting : amplifierSettings)
        	try {
        		amplifiers.add(new IntCodeAmplifier(setting, getComputer()));
        	} catch (Exception e) {
        		throw new RuntimeException(e);
        	}
        return amplifiers;
    }
    
    public static IntCodeComputer7 getComputer() throws InvalidArgumentException {
    	return new IntCodeComputer7(getInitialComputerState(),
    			getInstructionProvider(), Day05Main.getModeHandlerA());
    }
    
    public static IntCodeInstructionProvider getInstructionProvider() throws InvalidArgumentException {
    	IntCodeInstructionProvider instructionProvider = Day05Main.getInstructionProviderB();
    	instructionProvider.replaceInstruction(new IntCodeInstructionStoreInput7(3, 1));
    	return instructionProvider;
    }

    public static IntCodeComputerState getInitialComputerState() {
        return Day02Main.createInitialComputerState(getInput());
    }

    protected static List<Integer> getInput() {
        return Day02Main.getInput(Day07Main.class);
    }
}
