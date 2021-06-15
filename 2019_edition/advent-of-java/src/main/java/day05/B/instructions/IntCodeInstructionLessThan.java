package day05.B.instructions;

import java.util.List;

import day02.instructions.IntCodeInstructionWriting;
import day05.A.IntCodeComputer5A;
import day05.A.instructions.IntCodeInstruction5AAbstract;

public class IntCodeInstructionLessThan extends IntCodeInstruction5AAbstract
	implements IntCodeInstructionWriting {
    public IntCodeInstructionLessThan(int instructionCode, int paramCount) {
        super(instructionCode, paramCount);
    }

    @Override
    public void apply(IntCodeComputer5A computer, List<Integer> parameters) {
        int result = (parameters.get(0).compareTo(parameters.get(1)) < 0) ? 1 : 0;
        computer.getMemory().set(parameters.get(2), result);
    }

	@Override
	public int getWriteParamIndex() {
		return 2;
	}
}
