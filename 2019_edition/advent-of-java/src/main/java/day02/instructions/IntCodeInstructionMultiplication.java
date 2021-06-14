package day02.instructions;

import java.util.List;
import java.util.Optional;

import day02.IntCodeComputer;

public class IntCodeInstructionMultiplication extends IntCodeInstructionAbstract 
	implements IntCodeInstructionWriting {

    public IntCodeInstructionMultiplication(int instructionCode, int paramCount) {
        super(instructionCode, paramCount);
    }

    @Override
    public void apply(IntCodeComputer computer, List<Integer> parameters) {
        Integer writeIndex = parameters.get(2);
        Integer result = parameters.get(0) * parameters.get(1);
        computer.getMemory().set(writeIndex, result);
    }

	@Override
	public int getWriteParamIndex() {
		return 2;
	}

}

