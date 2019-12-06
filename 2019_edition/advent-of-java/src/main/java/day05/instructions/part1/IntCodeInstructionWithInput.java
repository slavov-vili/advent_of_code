package day05.instructions.part1;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

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
            List<ParamMode> parameterModes) {
        List<Integer> parameters = ListUtils.getListElementsAt(memory, parameterIndicesInMemory);
        int outputValue = this.applyWithUserInput(memory, parameters, parameterModes, this.getUserInput());
        int writeParameterIndex = 0;
        int writeIndex = IntCodeComputerUtils.convertParameterValueToWriteIndex(
                parameterIndicesInMemory.get(writeParameterIndex), parameters.get(writeParameterIndex),
                parameterModes.get(writeParameterIndex));
        return new IntCodeInstructionResult(outputValue, writeIndex, Optional.empty());
    }

    protected String getUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Instruction " + this.getClass().toString() + " requires input: ");
        String userInput = scanner.next();
        scanner.close();
        return userInput;
    }

    protected abstract int applyWithUserInput(List<Integer> memory, List<Integer> parameters,
            List<ParamMode> parameterModes, String userInput);

}
