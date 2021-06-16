package day02.instructions;

import java.util.List;

import day02.IntCodeComputer;

public class IntCodeInstructionHalt extends IntCodeInstructionAbstract {

    public IntCodeInstructionHalt(Long opCode) {
        super(opCode, 0);
    }

    @Override
    public void apply(IntCodeComputer computer, List<Long> parameters) {
        computer.requestHalt("Program execution complete");
    }

}
