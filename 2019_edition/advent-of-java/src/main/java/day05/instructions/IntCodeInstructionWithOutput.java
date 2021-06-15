package day05.instructions;

import java.io.IOException;
import java.util.List;

import day05.A.IntCodeComputer5A;
import day05.A.instructions.IntCodeInstruction5AAbstract;

public abstract class IntCodeInstructionWithOutput extends IntCodeInstruction5AAbstract {

    public IntCodeInstructionWithOutput(int instructionCode, int paramCount) {
        super(instructionCode, paramCount);
    }

    @Override
    public void apply(IntCodeComputer5A computer, List<Integer> parameters) {
        
        String outputValue = this.applyBeforeOutput(computer, parameters);
        try {
        	computer.writeOutput(outputValue);
		} catch (IOException e) {
			e.printStackTrace();
			computer.requestHalt(e.getMessage());
		}
    }

    protected abstract String applyBeforeOutput(IntCodeComputer5A computer, List<Integer> parameters);

}
