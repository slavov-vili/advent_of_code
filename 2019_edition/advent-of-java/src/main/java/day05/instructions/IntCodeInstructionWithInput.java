package day05.instructions;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

import day02.instructions.IntCodeInstruction;
import day02.instructions.IntCodeInstructionAbstract;
import day02.instructions.IntCodeInstructionResult;

public abstract class IntCodeInstructionWithInput extends IntCodeInstructionAbstract {
	
	public IntCodeInstructionWithInput(int instructionCode, int paramCount) {
		super(instructionCode, paramCount);
	}

	@Override
	public IntCodeInstructionResult apply(List<Integer> valuesIncludingOutput) {
	    int outputValue = this.applyWithUserInput(valuesIncludingOutput, this.getUserInput());
        int instructionJumpSize = valuesIncludingOutput.size();
        return new IntCodeInstructionResult(outputValue, instructionJumpSize);
	}
	
	protected String getUserInput() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Instruction " + this.getClass().toString() + " requires input: ");
		String userInput = scanner.next();
		scanner.close();
		return userInput;
	}

	protected abstract int applyWithUserInput(List<Integer> valuesIncludingOutput, String userInput);

}
