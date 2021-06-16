package day09.instructions;

import java.util.List;

import day02.IntCodeComputer;
import day02.instructions.IntCodeInstructionAbstract;
import day09.IntCodeComputer9;

public class IntCodeInstructionRelativeBaseOffset extends IntCodeInstructionAbstract {

	public IntCodeInstructionRelativeBaseOffset(Long opCode) {
		super(opCode, 1);
	}

	@Override
    public void apply(IntCodeComputer computer, List<Long> parameters) {
        ((IntCodeComputer9) computer).setRelativeBase(parameters.get(0));
    }
}
