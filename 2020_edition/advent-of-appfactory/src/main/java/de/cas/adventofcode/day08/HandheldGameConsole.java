package de.cas.adventofcode.day08;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HandheldGameConsole {
    public static final String NOP = "nop";
    public static final String JMP = "jmp";
    public static final String ACC = "acc";
    public static final String INSTRUCTION_SEPARATOR = " ";
    private int acc;

    public void run(final List<String> input) throws InfiniteLoopException {
        int pc = 0;
        this.acc = 0;
        final Set<Integer> pcSet = new HashSet<>();
        while (pc < input.size()) {
            if (pcSet.contains(pc)) {
                throw new InfiniteLoopException();
            } else {
                pcSet.add(pc);
            }
            final String line = input.get(pc);
            final String instruction = line.split(INSTRUCTION_SEPARATOR)[0];
            final int value = Integer.parseInt(line.split(INSTRUCTION_SEPARATOR)[1]);
            switch (instruction) {
                case ACC:
                    this.acc = this.acc + value;
                    pc++;
                    break;
                case JMP:
                    pc = pc + value;
                    break;
                case NOP:
                    pc++;
                    break;
                default:
                    break;
            }
        }
    }

    public int getAcc() {
        return this.acc;
    }

}
