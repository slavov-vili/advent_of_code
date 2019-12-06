package day05.instructions.part1;

import java.util.List;

import day02.IntCodeComputerUtils;

public class IntCodeInstructionOutputValue extends IntCodeInstructionWithOutput {

    public IntCodeInstructionOutputValue(int instructionCode, int paramCount) {
        super(instructionCode, paramCount);
    }

    @Override
    protected int applyBeforeOutput(List<Integer> memory, List<Integer> parameters, List<ParamMode> parameterModes) {
        return IntCodeComputerUtils.convertParameterValueToInputValue(memory, parameters.get(0), parameterModes.get(0));
    }

}
