package day02.instructions;

import java.util.List;
import java.util.Optional;

public class IntCodeInstructionHalt extends IntCodeInstructionAbstract {

    public IntCodeInstructionHalt(int opCode) {
        super(opCode, 0);
    }

    @Override
    public Optional<Integer> apply(List<Integer> memory, List<Integer> parameters) {
        return Optional.empty();
    }

}
