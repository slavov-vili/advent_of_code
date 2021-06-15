package day05.A.instructions;

import java.util.List;

import day05.A.IntCodeComputer5A;
import day05.instructions.IntCodeInstructionWithOutput;

public class IntCodeInstructionOutputValue extends IntCodeInstructionWithOutput {

    public IntCodeInstructionOutputValue(int instructionCode) {
        super(instructionCode, 1);
    }

    @Override
    protected String applyBeforeOutput(IntCodeComputer5A computer, List<Integer> parameters) {
        return Integer.toString(parameters.get(0));
    }

}
