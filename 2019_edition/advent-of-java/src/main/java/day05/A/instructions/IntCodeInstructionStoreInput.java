package day05.A.instructions;

import java.util.List;

import day05.A.IntCodeComputer5A;
import day05.instructions.IntCodeInstructionWithInput;
import day05.instructions.IntCodeInstructionWriting;

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
