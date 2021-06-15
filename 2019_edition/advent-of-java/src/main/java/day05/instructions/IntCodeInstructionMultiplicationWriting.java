package day05.instructions;

import java.util.List;

import day02.IntCodeComputer;
import day02.instructions.IntCodeInstructionAbstract;

public class IntCodeInstructionMultiplicationWriting extends IntCodeInstructionAbstract 
	implements IntCodeInstructionWriting {

    private int writeParamIndex;

	public IntCodeInstructionMultiplicationWriting(int instructionCode) {
        super(instructionCode, 3);
        this.writeParamIndex = 2;
    }

    @Override
    public void apply(IntCodeComputer computer, List<Integer> parameters) {
        Integer writeIndex = parameters.get(this.writeParamIndex);
        Integer result = parameters.get(0) * parameters.get(1);
        computer.setMemoryAddress(writeIndex, result);
    }

	@Override
	public int getWriteParamIndex() {
		return this.writeParamIndex;
	}

}

