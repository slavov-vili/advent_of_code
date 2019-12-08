package day05.instructions.part2;

import java.awt.Point;
import java.io.Writer;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import day02.IntCodeComputerState.ExecutionCode;
import day02.IntCodeComputerUtils;
import day02.instructions.IntCodeInstructionAbstract;
import day02.instructions.IntCodeInstructionResult;
import utils.ListUtils;

public class IntCodeInstructionLessThan extends IntCodeInstructionAbstract {
    public IntCodeInstructionLessThan(int instructionCode, int paramCount) {
        super(instructionCode, paramCount);
    }

    @Override
    public IntCodeInstructionResult apply(List<Integer> memory, List<Integer> parameterIndicesInMemory,
            List<ParamMode> parameterModes, Scanner inputScanner, Writer outputWriter) {
        List<Integer> parameters = ListUtils.getListElementsAt(memory, parameterIndicesInMemory);
        int param1Value = IntCodeComputerUtils.convertParameterValueToInputValue(memory, parameters.get(0),
                parameterModes.get(0));
        int param2Value = IntCodeComputerUtils.convertParameterValueToInputValue(memory, parameters.get(1),
                parameterModes.get(1));
        int outputValue = (param1Value < param2Value) ? 1 : 0;
        int writeParameterIndex = parameterIndicesInMemory.size() - 1;
        int writeIndex = IntCodeComputerUtils.convertParameterValueToWriteIndex(
                parameterIndicesInMemory.get(writeParameterIndex), parameters.get(writeParameterIndex),
                parameterModes.get(writeParameterIndex));
        return new IntCodeInstructionResult(Optional.of(new Point(writeIndex, outputValue)), Optional.empty(),
                ExecutionCode.READY_FOR_NEXT);
    }
}
