package day02.instructions;

import java.util.List;

import day02.IntCodeComputer;

public class IntCodeInstructionMultiplication extends IntCodeInstructionAbstract {

	public IntCodeInstructionMultiplication(Long opCode) {
		super(opCode, 3);
	}

	@Override
	public void apply(IntCodeComputer computer, List<Long> parameters) {
		Long writeIndex = parameters.get(2);
        Long result = parameters.get(0) * parameters.get(1);
        computer.setMemoryAddress(writeIndex, result);
	}

}
