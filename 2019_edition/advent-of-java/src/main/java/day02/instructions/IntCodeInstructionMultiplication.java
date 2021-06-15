package day02.instructions;

import java.util.List;

import day02.IntCodeComputer;

public class IntCodeInstructionMultiplication extends IntCodeInstructionAbstract {

	public IntCodeInstructionMultiplication(int opCode) {
		super(opCode, 3);
	}

	@Override
	public void apply(IntCodeComputer computer, List<Integer> parameters) {
		Integer writeIndex = parameters.get(2);
        Integer result = parameters.get(0) * parameters.get(1);
        computer.setMemoryAddress(writeIndex, result);
	}

}
