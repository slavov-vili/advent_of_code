package day05.instructions.part1;

import java.util.List;

public class IntCodeInstructionStoreInput extends IntCodeInstructionWithInput {

    public IntCodeInstructionStoreInput(int instructionCode, int paramCount) {
        super(instructionCode, paramCount);
    }

    @Override
    protected int applyWithUserInput(List<Integer> memory, List<Integer> parameters, List<ParamMode> parameterModes,
            String userInput) {
        return Integer.parseInt(userInput);
    }

}
