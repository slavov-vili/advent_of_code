package day07;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import day02.IntCodeComputerState;
import day02.IntCodeComputerState.ExecutionCode;
import day05.Day05Main;
import utils.AdventOfCodeUtils;
import utils.ListUtils;

public class Day07Main {

    public static void main(String[] args) {
        int solutionA = solveA(0, 4);
        int solutionB = solveB(5, 9);

        System.out.println("Solution A: " + solutionA);
        System.out.println("Solution B: " + solutionB);

    }

    protected static int solveA(int minSettingValue, int maxSettingValue) {
        return getLargestAmplifierChainOutput(prepareAmplifierPermutations(minSettingValue, maxSettingValue)
                .map(amplifierChain -> runAmplifierChain(0, amplifierChain)));
    }

    protected static int solveB(int minSettingValue, int maxSettingValue) {
        return getLargestAmplifierChainOutput(prepareAmplifierPermutations(minSettingValue, maxSettingValue)
                .map(amplifierChain -> runAmplifierLoop(0, amplifierChain)));
    }

    protected static Stream<List<IntCodeAmplifier>> prepareAmplifierPermutations(int minSettingValue,
            int maxSettingValue) {
        List<Integer> amplifierSettings = ListUtils.generateRange(minSettingValue, maxSettingValue);
        List<List<Integer>> permutations = ListUtils.findPermutations(amplifierSettings.size(), amplifierSettings,
                new ArrayList<>());
        return permutations.stream().map(permutation -> mapToAmplifier(generateAmplifiers(permutation), permutation));
    }

    protected static List<IntCodeAmplifier> runAmplifierChain(int firstAmplifierInput,
            List<IntCodeAmplifier> initialAmplifierChain) {
        List<IntCodeAmplifier> endAmplifierChain = new ArrayList<>(initialAmplifierChain);
        int curAmplifierInput = firstAmplifierInput;
        for (IntCodeAmplifier amplifier : endAmplifierChain)
            curAmplifierInput = amplifier.amplifySignal(curAmplifierInput).get();

        return endAmplifierChain;
    }

    protected static List<IntCodeAmplifier> runAmplifierLoop(int initialLoopInput,
            List<IntCodeAmplifier> initialAmplifierChain) {
        List<IntCodeAmplifier> curAmplifierChain = initialAmplifierChain;
        int curChainInput = initialLoopInput;
        while (!ListUtils.getLastElement(curAmplifierChain).getComputerState().getExecutionCode()
                .equals(ExecutionCode.HALT)) {
            curAmplifierChain = runAmplifierChain(curChainInput, curAmplifierChain);
            curChainInput = ListUtils.getLastElement(curAmplifierChain).getOutput().get();
        }
        return curAmplifierChain;
    }

    protected static int getLargestAmplifierChainOutput(Stream<List<IntCodeAmplifier>> amplifierChains) {
        return amplifierChains.mapToInt(amplifierChain -> ListUtils.getLastElement(amplifierChain).getOutput().get())
                .max().getAsInt();
    }

    protected static List<IntCodeAmplifier> mapToAmplifier(List<IntCodeAmplifier> initialAmplifierChain,
            List<Integer> amplifierInputs) {
        List<IntCodeAmplifier> endAmplifierChain = new ArrayList<>(initialAmplifierChain);
        for (int i = 0; i < endAmplifierChain.size(); i++) {
            endAmplifierChain.get(i).amplifySignal(amplifierInputs.get(i));
        }
        return endAmplifierChain;
    }

    protected static List<IntCodeAmplifier> generateAmplifiers(List<Integer> amplifierSettings) {
        List<IntCodeAmplifier> amplifiers = new ArrayList<>();
        for (Integer setting : amplifierSettings)
            amplifiers.add(new IntCodeAmplifier(setting, Day05Main.getComputerB(getInitialComputerState())));
        return amplifiers;
    }

    protected static IntCodeComputerState getInitialComputerState() {
        return new IntCodeComputerState(getInput(), 0, ExecutionCode.READY_FOR_NEXT);
    }

    protected static List<Integer> getInput() {
        String intCodesString = AdventOfCodeUtils.readInputLines(Day07Main.class).get(0);
        return Arrays.asList(intCodesString.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());
    }
}
