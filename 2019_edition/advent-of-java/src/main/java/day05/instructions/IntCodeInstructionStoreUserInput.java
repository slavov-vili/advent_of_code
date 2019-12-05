package day05.instructions;

import java.util.stream.IntStream;

import day02.instructions.IntCodeInstructionAbstract;

public class IntCodeInstructionStoreUserInput extends IntCodeInstructionWithInput {

	public IntCodeInstructionStoreUserInput(int instructionCode, int paramCount) {
		super(instructionCode, paramCount);
	}
	
	@Override
	protected int applyWithUserInput(IntStream valuesIncludingOutput, String userInput) {
		return Integer.parseInt(userInput);
	}

}
