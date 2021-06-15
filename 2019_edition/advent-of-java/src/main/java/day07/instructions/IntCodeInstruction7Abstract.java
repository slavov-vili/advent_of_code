package day07.instructions;

import java.util.List;

import day05.B.IntCodeComputer5B;
import day05.B.instructions.IntCodeInstruction5BAbstract;
import day07.IntCodeComputer7;

public abstract class IntCodeInstruction7Abstract extends IntCodeInstruction5BAbstract {

	public IntCodeInstruction7Abstract(int opCode, int paramCount) {
		super(opCode, paramCount);
	}

	@Override
	public void apply(IntCodeComputer5B computer, List<Integer> parameters) {
		
		if (computer instanceof IntCodeComputer7)
			apply((IntCodeComputer7)computer, parameters);
		else
			computer.requestHalt("Instruction " + this.getClass().getSimpleName()
					+ " requres an instance of IntCodeComputer7");
	}

	public abstract void apply(IntCodeComputer7 computer, List<Integer> parameters);
}
