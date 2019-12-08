package day07;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import day02.IntCodeComputerState;
import day02.IntCodeComputerState.ExecutionCode;
import day05.Day05Main;
import exceptions.InvalidArgumentException;
import utils.AdventOfCodeUtils;
import utils.ListUtils;

public class Day07Main {

    public static void main(String[] args) {
        int solutionA = solveA(0, 4);
        System.out.println("Solution A: " + solutionA);

    }

    protected static int solveA(int minSettingValue, int maxSettingValue) {
        List<Integer> amplifierSettings = ListUtils.generateRange(0, 4);
        List<List<Integer>> permutations = ListUtils.findPermutations(amplifierSettings.size(), amplifierSettings, new ArrayList<>());
        return permutations.stream()
                .map(permutation -> mapToAmplifier(generateAmplifiers(permutation), permutation))
                .map(amplifierChain -> runAmplifierChain(0, amplifierChain))
                .mapToInt(amplifierChain -> amplifierChain.get(amplifierChain.size()-1).getOutput().get())
                .max().getAsInt();
    }

    protected static List<IntCodeAmplifier> runAmplifierChain(int firstAmplifierInput,
            List<IntCodeAmplifier> initialAmplifierChain) {
        List<IntCodeAmplifier> endAmplifierChain = new ArrayList<>(initialAmplifierChain);
        int curAmplifierInput = firstAmplifierInput;
        for (IntCodeAmplifier amplifier : endAmplifierChain)
            curAmplifierInput = amplifier.amplifySignal(curAmplifierInput).get();

        return endAmplifierChain;
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
        String intCodesString = AdventOfCodeUtils.readClasspathFileLines(Day07Main.class, "input.txt").get(0);
        return Arrays.asList(intCodesString.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());
    }
}
