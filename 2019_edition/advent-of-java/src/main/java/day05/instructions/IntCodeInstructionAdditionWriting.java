package day05.instructions;

import java.util.List;

import day02.IntCodeComputer;
import day02.instructions.IntCodeInstructionAbstract;

public class IntCodeInstructionAdditionWriting extends IntCodeInstructionAbstract
	implements IntCodeInstructionWriting {

    public IntCodeInstructionAdditionWriting(int instructionCode, int paramCount) {
        super(instructionCode, paramCount);
    }

    @Override
    public void apply(IntCodeComputer computer, List<Integer> parameters) {
        Integer writeIndex = parameters.get(2);
        Integer result = parameters.get(0) + parameters.get(1);
        computer.setMemoryAddress(writeIndex, result);
    }

	@Override
	public int getWriteParamIndex() {
		return 2;
	}

}
