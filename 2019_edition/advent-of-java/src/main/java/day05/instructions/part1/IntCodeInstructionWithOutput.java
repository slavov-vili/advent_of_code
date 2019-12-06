package day05.instructions.part1;

import java.util.List;
import java.util.Optional;

import day02.IntCodeComputerUtils;
import day02.instructions.IntCodeInstructionAbstract;
import day02.instructions.IntCodeInstructionResult;
import utils.ListUtils;

public abstract class IntCodeInstructionWithOutput extends IntCodeInstructionAbstract {

    public IntCodeInstructionWithOutput(int instructionCode, int paramCount) {
        super(instructionCode, paramCount);
    }

    @Override
    public IntCodeInstructionResult apply(List<Integer> memory, List<Integer> parameterIndicesInMemory,
            List<ParamMode> parameterModes) {
        List<Integer> parameters = ListUtils.getListElementsAt(memory, parameterIndicesInMemory);
        int outputValue = this.applyBeforeOutput(memory, parameters, parameterModes);
        System.out.println("Instruction " + this.getClass().toString() + " output: " + outputValue);
        int writeParameterIndex = 0;
        int writeIndex = IntCodeComputerUtils.convertParameterValueToWriteIndex(
                parameterIndicesInMemory.get(writeParameterIndex), parameters.get(writeParameterIndex),
                parameterModes.get(writeParameterIndex));
        return new IntCodeInstructionResult(outputValue, writeIndex, Optional.empty());
    }

    protected abstract int applyBeforeOutput(List<Integer> memory, List<Integer> parameters,
            List<ParamMode> parameterModes);

}
