package day07.instructions;

import java.util.List;

import day05.instructions.IntCodeInstructionWriting;
import day07.IntCodeComputer7;

public class IntCodeInstructionStoreInput7 extends IntCodeInstructionWithMissingInput
	implements IntCodeInstructionWriting {

	public IntCodeInstructionStoreInput7(int opCode, int paramCount) {
		super(opCode, paramCount);
	}

	@Override
	protected void applyWithInput(IntCodeComputer7 computer, List<Integer> parameters, String userInput) {
		computer.setMemoryAddress(parameters.get(0), Integer.parseInt(userInput));
	}

	@Override
	public int getWriteParamIndex() {
		return 0;
	}

}
