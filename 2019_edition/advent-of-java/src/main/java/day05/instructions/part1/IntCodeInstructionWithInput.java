package day05.instructions.part1;

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

public abstract class IntCodeInstructionWithInput extends IntCodeInstructionAbstract {

    public IntCodeInstructionWithInput(int instructionCode, int paramCount) {
        super(instructionCode, paramCount);
    }

    @Override
    public IntCodeInstructionResult apply(List<Integer> memory, List<Integer> parameterIndicesInMemory,
            List<ParamMode> parameterModes, Scanner inputScanner, Writer outputWriter) {
        System.out.println("Instruction " + this.getClass().toString() + " requires input: ");
        if (!inputScanner.hasNext())
            return new IntCodeInstructionResult(Optional.empty(), Optional.empty(), ExecutionCode.REQUIRE_INPUT);

        String input = this.getNextInput(inputScanner);
        List<Integer> parameters = ListUtils.getListElementsAt(memory, parameterIndicesInMemory);
        int outputValue = this.applyWithInput(memory, parameters, parameterModes, input);
        int writeParameterIndex = 0;
        int writeIndex = IntCodeComputerUtils.convertParameterValueToWriteIndex(
                parameterIndicesInMemory.get(writeParameterIndex), parameters.get(writeParameterIndex),
                parameterModes.get(writeParameterIndex));
        return new IntCodeInstructionResult(Optional.of(new Point(writeIndex, outputValue)), Optional.empty(),
                ExecutionCode.READY_FOR_NEXT);
    }

    protected String getNextInput(Scanner inputScanner) {
        String nextInput = inputScanner.next();
        System.out.println("Instruction " + this.getClass().toString() + " received input: " + nextInput);
        return nextInput;
    }

    protected abstract int applyWithInput(List<Integer> memory, List<Integer> parameters,
            List<ParamMode> parameterModes, String userInput);

}
