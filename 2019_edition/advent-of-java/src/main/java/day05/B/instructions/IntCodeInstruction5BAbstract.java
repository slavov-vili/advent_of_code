package day05.B.instructions;

import java.util.List;

import day05.A.IntCodeComputer5A;
import day05.A.instructions.IntCodeInstruction5AAbstract;
import day05.B.IntCodeComputer5B;

public abstract class IntCodeInstruction5BAbstract extends IntCodeInstruction5AAbstract {

	public IntCodeInstruction5BAbstract(int opCode, int paramCount) {
		super(opCode, paramCount);
	}

	@Override
	public void apply(IntCodeComputer5A computer, List<Integer> parameters) {
		
		if (computer instanceof IntCodeComputer5B)
			apply((IntCodeComputer5B)computer, parameters);
		else
			computer.requestHalt("Instruction " + this.getClass().getSimpleName()
					+ " requres an instance of IntCodeComputer5A");
	}

	public abstract void apply(IntCodeComputer5B computer, List<Integer> parameters);

}
