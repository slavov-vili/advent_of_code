package day05.instructions;

import java.util.List;
import java.util.stream.IntStream;

public class IntCodeInstructionOutputValue extends IntCodeInstructionWithOutput {
	
	public IntCodeInstructionOutputValue(int instructionCode, int paramCount) {
		super(instructionCode, paramCount);
	}
	
	@Override
	protected int applyBeforeOutput(List<Integer> valuesIncludingOutput) {
		return valuesIncludingOutput.get(0);
	}

}
