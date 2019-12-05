package day05.instructions;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

import day02.instructions.IntCodeInstruction;
import day02.instructions.IntCodeInstructionAbstract;
import day02.instructions.IntCodeInstructionResult;

public abstract class IntCodeInstructionWithOutput extends IntCodeInstructionAbstract {
	
	public IntCodeInstructionWithOutput(int instructionCode, int paramCount) {
		super(instructionCode, paramCount);
	}

	@Override
	public IntCodeInstructionResult apply(List<Integer> valuesIncludingOutput) {
	    int outputValue = this.applyBeforeOutput(valuesIncludingOutput);
        int instructionJumpSize = valuesIncludingOutput.size();
        System.out.println("Instruction " + this.getClass().toString() + " output: " + outputValue);
        return new IntCodeInstructionResult(outputValue, instructionJumpSize);
	}

	protected abstract int applyBeforeOutput(List<Integer> valuesIncludingOutput);

}
