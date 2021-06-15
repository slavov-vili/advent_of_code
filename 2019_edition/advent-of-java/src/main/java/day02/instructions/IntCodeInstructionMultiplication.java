package day02.instructions;

import java.util.List;

import day02.IntCodeComputer;

public class IntCodeInstructionMultiplication extends IntCodeInstructionAbstract {

	public IntCodeInstructionMultiplication(int opCode, int paramCount) {
		super(opCode, paramCount);
	}

	@Override
	public void apply(IntCodeComputer computer, List<Integer> parameters) {
		Integer writeIndex = parameters.get(2);
        Integer result = parameters.get(0) * parameters.get(1);
        computer.getMemory().set(writeIndex, result);
	}

}
