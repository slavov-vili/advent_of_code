package day05.A.instructions;

import java.util.List;

import day02.IntCodeComputer;
import day02.instructions.IntCodeInstructionAbstract;
import day05.A.IntCodeComputer5A;

public abstract class IntCodeInstruction5AAbstract extends IntCodeInstructionAbstract {

	public IntCodeInstruction5AAbstract(int opCode, int paramCount) {
		super(opCode, paramCount);
	}

	@Override
	public void apply(IntCodeComputer computer, List<Integer> parameters) {
		
		if (computer instanceof IntCodeComputer5A)
			apply((IntCodeComputer5A)computer, parameters);
		else
			computer.requestHalt("Instruction " + this.getClass().getSimpleName()
					+ " requres an instance of IntCodeComputer5A");
	}

	public abstract void apply(IntCodeComputer5A computer, List<Integer> parameters);
}
