package day02.instructions;

import java.io.Writer;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.IntStream;

import day02.IntCodeComputerUtils;
import utils.ListUtils;

public class IntCodeInstructionAddition extends IntCodeInstructionAbstract {

    public IntCodeInstructionAddition(int instructionCode, int paramCount) {
        super(instructionCode, paramCount);
    }

    @Override
    public IntCodeInstructionResult apply(List<Integer> memory, List<Integer> parameterIndicesInMemory,
            List<ParamMode> parameterModes, Scanner inputScanner, Writer outputWriter) {
        List<Integer> parameters = ListUtils.getListElementsAt(memory, parameterIndicesInMemory);
        int outputValue = IntStream.range(0, parameterIndicesInMemory.size()).limit(parameterIndicesInMemory.size() - 1)
                .map(paramIdx -> IntCodeComputerUtils.convertParameterValueToInputValue(memory,
                        parameters.get(paramIdx), parameterModes.get(paramIdx)))
                .reduce(0, (a, b) -> a + b);
        int writeParameterIndex = parameterIndicesInMemory.size() - 1;
        int writeIndex = IntCodeComputerUtils.convertParameterValueToWriteIndex(
                parameterIndicesInMemory.get(writeParameterIndex), parameters.get(writeParameterIndex),
                parameterModes.get(writeParameterIndex));
        return new IntCodeInstructionResult(outputValue, writeIndex, Optional.empty());
    }

}
