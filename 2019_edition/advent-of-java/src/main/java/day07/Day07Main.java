package day07;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import day02.IntCodeComputerState;
import day02.IntCodeComputerState.ExecutionCode;
import day05.Day05Main;
import utils.AdventOfCodeUtils;
import utils.ListUtils;

public class Day07Main {

    public static void main(String[] args) {
        int solutionA = solveA(0, 4);
        System.out.println("Solution A: " + solutionA);

        // TODO: PART 2 (int computer: make curInstructionIdx a member variable)
        // TODO: processInput command returns the end state => pass it back in the next
        // call
    }

    protected static int solveA(int minSettingValue, int maxSettingValue) {
        List<Integer> amplifierSettings = ListUtils.generateRange(0, 4);
        return ListUtils.findPermutations(amplifierSettings.size(), amplifierSettings, new ArrayList<>()).stream()
                .mapToInt(arrangement -> runAmplifierChain(0, arrangement)).max().getAsInt();
    }

    protected static int runAmplifierChain(int initialInput, List<Integer> amplifierSettings) {
        List<IntCodeAmplifier> amplifiers = new ArrayList<>();
        for (Integer setting : amplifierSettings)
            amplifiers
                    .add(new IntCodeAmplifier(setting, Day05Main.getComputerB(getInitialComputerState()), getInput()));

        int result = initialInput;
        for (IntCodeAmplifier amplifier : amplifiers)
            result = amplifier.amplifySignal(result);

        return result;
    }

    public static IntCodeComputerState getInitialComputerState() {
        return new IntCodeComputerState(getInput(), 0, ExecutionCode.READY_FOR_NEXT);
    }

    protected static List<Integer> getInput() {
        String intCodesString = AdventOfCodeUtils.readClasspathFileLines(Day07Main.class, "input.txt").get(0);
        return Arrays.asList(intCodesString.split(",")).stream().map(Integer::parseInt).collect(Collectors.toList());
    }
}
