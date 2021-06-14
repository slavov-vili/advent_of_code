package day05.instructions;

import java.util.List;

import day05.IntCodeComputer5A;

public class IntCodeInstructionOutputValue extends IntCodeInstructionWithOutput {

    public IntCodeInstructionOutputValue(int instructionCode, int paramCount) {
        super(instructionCode, paramCount);
    }

    @Override
    protected String applyBeforeOutput(IntCodeComputer5A computer, List<Integer> parameters) {
        return Integer.toString(parameters.get(0));
    }

}
