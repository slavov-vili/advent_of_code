package de.cas.adventofcode.day08;

import java.util.ArrayList;
import java.util.List;

import de.cas.adventofcode.shared.Day;

public class Day08 extends Day<Integer> {
    protected Day08() {
        super(8);
    }

    public static void main(final String[] args) {
        new Day08().run();
    }

    @Override
    public Integer solvePart1(final List<String> input) {
        final HandheldGameConsole handheldGameConsole = new HandheldGameConsole();
        try {
            handheldGameConsole.run(input);
        } catch (final InfiniteLoopException e) {
            return handheldGameConsole.getAcc();
        }
        return 0;
    }

    @Override
    public Integer solvePart2(final List<String> input) {
        for (int i = 0; i < input.size(); i++) {
            final List<String> modifiedInput = this.replaceNopAndJmp(input, i);
            final HandheldGameConsole handheldGameConsole = new HandheldGameConsole();
            try {
                handheldGameConsole.run(modifiedInput);
                return handheldGameConsole.getAcc();
            } catch (final InfiniteLoopException e) {
                // Try next line
            }
        }
        throw new RuntimeException("No solution found!");
    }

    private List<String> replaceNopAndJmp(final List<String> input, final int i) {
        final List<String> modifiedInput = new ArrayList<>(List.copyOf(input));
        final String[] line = input.get(i).split(HandheldGameConsole.INSTRUCTION_SEPARATOR);
        if (line[0].equalsIgnoreCase(HandheldGameConsole.JMP)) {
            line[0] = HandheldGameConsole.NOP;
        } else if (line[0].equalsIgnoreCase(HandheldGameConsole.NOP)) {
            line[0] = HandheldGameConsole.JMP;
        }
        modifiedInput.remove(i);
        modifiedInput.add(i, line[0] + HandheldGameConsole.INSTRUCTION_SEPARATOR + line[1]);
        return modifiedInput;
    }
}
