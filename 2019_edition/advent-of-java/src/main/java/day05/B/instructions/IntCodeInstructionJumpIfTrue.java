package day05.B.instructions;

import java.util.List;

import day02.IntCodeComputer;
import day02.instructions.IntCodeInstructionAbstract;
import day05.B.IntCodeComputer5B;

public class IntCodeInstructionJumpIfTrue extends IntCodeInstructionAbstract {

    public IntCodeInstructionJumpIfTrue(Long opCode) {
        super(opCode, 2);
    }

    @Override
    public void apply(IntCodeComputer computer, List<Long> parameters) {
    	IntCodeComputer5B computer5B = (IntCodeComputer5B) computer;
        if (!parameters.get(0).equals(0L))
        	computer5B.setJumpIndex(parameters.get(1));
    }

}
