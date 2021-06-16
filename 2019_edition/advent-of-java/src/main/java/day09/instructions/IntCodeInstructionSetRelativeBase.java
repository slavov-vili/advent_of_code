package day09.instructions;

import java.util.List;

import day02.IntCodeComputer;
import day02.instructions.IntCodeInstructionAbstract;
import day09.IntCodeComputer9;

public class IntCodeInstructionSetRelativeBase extends IntCodeInstructionAbstract {

	public IntCodeInstructionSetRelativeBase(int opCode) {
		super(opCode, 1);
	}

	@Override
    public void apply(IntCodeComputer computer, List<Long> parameters) {
        ((IntCodeComputer9) computer).setRelativeBase(parameters.get(0));
    }
}
