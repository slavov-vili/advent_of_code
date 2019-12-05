package day05.instructions;

import java.util.Scanner;
import java.util.stream.IntStream;

import day02.instructions.IntCodeInstruction;
import day02.instructions.IntCodeInstructionAbstract;

public abstract class IntCodeInstructionWithInput extends IntCodeInstructionAbstract {
	
	public IntCodeInstructionWithInput(int instructionCode, int paramCount) {
		super(instructionCode, paramCount);
	}

	@Override
	public int apply(IntStream valuesIncludingOutput) {
		return this.applyWithUserInput(valuesIncludingOutput, this.getUserInput());
	}
	
	protected String getUserInput() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Instruction " + this.getClass().toString() + " requires input: ");
		String userInput = scanner.next();
		scanner.close();
		return userInput;
	}

	protected abstract int applyWithUserInput(IntStream valuesIncludingOutput, String userInput);

}
