package day05.instructions;

import java.util.Scanner;
import java.util.stream.IntStream;

import day02.instructions.IntCodeInstruction;
import day02.instructions.IntCodeInstructionAbstract;

public abstract class IntCodeInstructionWithOutput extends IntCodeInstructionAbstract {
	
	public IntCodeInstructionWithOutput(int instructionCode, int paramCount) {
		super(instructionCode, paramCount);
	}

	@Override
	public int apply(IntStream valuesIncludingOutput) {
		int output = this.applyBeforeOutput(valuesIncludingOutput);
		System.out.println("Instruction " + this.getClass().toString() + " output: " + output);
		return output;
	}

	protected abstract int applyBeforeOutput(IntStream valuesIncludingOutput);

}
