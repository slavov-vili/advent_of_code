package day05.instructions.part1;

import java.awt.Point;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import day02.IntCodeComputerState.ExecutionCode;
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
            List<ParamMode> parameterModes, Scanner inputScanner, Writer outputWriter) throws IOException {
        List<Integer> parameters = ListUtils.getListElementsAt(memory, parameterIndicesInMemory);
        int outputValue = this.applyBeforeOutput(memory, parameters, parameterModes);
        outputWriter.append(outputValue + "\n");
        outputWriter.flush();
        int writeParameterIndex = 0;
        int writeIndex = IntCodeComputerUtils.convertParameterValueToWriteIndex(
                parameterIndicesInMemory.get(writeParameterIndex), parameters.get(writeParameterIndex),
                parameterModes.get(writeParameterIndex));
        return new IntCodeInstructionResult(Optional.of(new Point(writeIndex, outputValue)), Optional.empty(),
                ExecutionCode.READY_FOR_NEXT);
    }

    protected abstract int applyBeforeOutput(List<Integer> memory, List<Integer> parameters,
            List<ParamMode> parameterModes);

}
