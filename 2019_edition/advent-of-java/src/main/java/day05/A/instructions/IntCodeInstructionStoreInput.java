package day05.A.instructions;

import java.util.List;

import day05.A.IntCodeComputer5A;
import day05.instructions.IntCodeInstructionWithInput;
import day05.instructions.IntCodeInstructionWriting;

public class IntCodeInstructionStoreInput extends IntCodeInstructionWithInput
	implements IntCodeInstructionWriting {

    private int writeParamIndex;

	public IntCodeInstructionStoreInput(Long instructionCode) {
        super(instructionCode, 1);
        this.writeParamIndex = 0;
    }

    @Override
    protected void applyWithInput(IntCodeComputer5A computer, List<Long> parameters,
    		String userInput) {
    	computer.setMemoryAddress(parameters.get(this.writeParamIndex), computer.parseNumber(userInput));
    }

	@Override
	public int getWriteParamIndex() {
		return this.writeParamIndex;
	}

}
