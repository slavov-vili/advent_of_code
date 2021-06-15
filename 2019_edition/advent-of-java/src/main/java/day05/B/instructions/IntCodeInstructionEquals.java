package day05.B.instructions;

import java.util.List;

import day02.IntCodeComputer;
import day02.instructions.IntCodeInstructionAbstract;
import day05.instructions.IntCodeInstructionWriting;

public class IntCodeInstructionEquals extends IntCodeInstructionAbstract
	implements IntCodeInstructionWriting {
    public IntCodeInstructionEquals(int instructionCode, int paramCount) {
        super(instructionCode, paramCount);
    }

    @Override
    public void apply(IntCodeComputer computer, List<Integer> parameters) {
        int result = (parameters.get(0).equals(parameters.get(1))) ? 1 : 0;
        computer.getMemory().set(parameters.get(2), result);
    }

	@Override
	public int getWriteParamIndex() {
		return 2;
	}
}
