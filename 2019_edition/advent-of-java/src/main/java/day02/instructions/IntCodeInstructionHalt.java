package day02.instructions;

import java.util.List;

import day02.IntCodeComputer;

public class IntCodeInstructionHalt extends IntCodeInstructionAbstract {

    public IntCodeInstructionHalt(int opCode) {
        super(opCode, 0);
    }

    @Override
    public void apply(IntCodeComputer computer, List<Integer> parameters) {
        computer.requestHalt("Program execution complete");
    }

}
