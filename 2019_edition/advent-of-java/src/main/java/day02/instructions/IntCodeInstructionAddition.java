package day02.instructions;

import java.util.List;
import java.util.Optional;

public class IntCodeInstructionAddition extends IntCodeInstructionAbstract {

    public IntCodeInstructionAddition(int instructionCode, int paramCount) {
        super(instructionCode, paramCount);
    }

    @Override
    public Optional<Integer> apply(List<Integer> memory, List<Integer> parameters) {
        Integer writeIndex = parameters.get(2);
        Integer result = memory.get(parameters.get(0)) + memory.get(parameters.get(1));
        memory.set(writeIndex, result);
        return Optional.of(writeIndex);
    }

}
