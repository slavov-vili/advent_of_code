package day05.B.instructions;

import java.util.List;
import java.util.Optional;
import day05.B.IntCodeComputer5B;

public class IntCodeInstructionJumpIfTrue extends IntCodeInstruction5BAbstract {

    public IntCodeInstructionJumpIfTrue(int opCode, int paramCount) {
        super(opCode, paramCount);
    }

    @Override
    public void apply(IntCodeComputer5B computer, List<Integer> parameters) {
        if (!parameters.get(0).equals(0))
        	computer.setJumpIndex(Optional.of(parameters.get(1)));
    }

}
