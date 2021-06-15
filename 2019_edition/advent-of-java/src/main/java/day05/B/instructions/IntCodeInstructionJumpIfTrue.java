package day05.B.instructions;

import java.util.List;
import java.util.Optional;

import day02.IntCodeComputer;
import day02.instructions.IntCodeInstructionAbstract;
import day05.B.IntCodeComputer5B;

public class IntCodeInstructionJumpIfTrue extends IntCodeInstructionAbstract {

    public IntCodeInstructionJumpIfTrue(int opCode, int paramCount) {
        super(opCode, paramCount);
    }

    @Override
    public void apply(IntCodeComputer computer, List<Integer> parameters) {
    	IntCodeComputer5B computer5B = (IntCodeComputer5B) computer;
        if (!parameters.get(0).equals(0))
        	computer5B.setJumpIndex(Optional.of(parameters.get(1)));
    }

}
