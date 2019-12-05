package day05.instructions;

import java.util.stream.IntStream;

public class IntCodeInstructionOutputValue extends IntCodeInstructionWithOutput {
	
	public IntCodeInstructionOutputValue(int instructionCode, int paramCount) {
		super(instructionCode, paramCount);
	}
	
	@Override
	protected int applyBeforeOutput(IntStream valuesIncludingOutput) {
		return valuesIncludingOutput.findFirst().getAsInt();
	}

}
