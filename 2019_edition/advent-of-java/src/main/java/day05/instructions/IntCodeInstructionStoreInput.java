package day05.instructions;

import java.util.List;

import day02.instructions.IntCodeInstructionWriting;
import day05.IntCodeComputer5A;

public class IntCodeInstructionStoreInput extends IntCodeInstructionWithInput
	implements IntCodeInstructionWriting {

    public IntCodeInstructionStoreInput(int instructionCode, int paramCount) {
        super(instructionCode, paramCount);
    }

    @Override
    protected void applyWithInput(IntCodeComputer5A computer, List<Integer> parameters,
    		String userInput) {
    	computer.getMemory().set(parameters.get(0), Integer.parseInt(userInput));
    }

	@Override
	public int getWriteParamIndex() {
		return 0;
	}

}
