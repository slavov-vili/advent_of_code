package day05.instructions;

import java.io.IOException;
import java.util.List;

import day02.IntCodeComputer;
import day02.instructions.IntCodeInstructionAbstract;
import day05.A.IntCodeComputer5A;

public abstract class IntCodeInstructionWithOutput extends IntCodeInstructionAbstract {

    public IntCodeInstructionWithOutput(int instructionCode, int paramCount) {
        super(instructionCode, paramCount);
    }

    @Override
    public void apply(IntCodeComputer computer, List<Integer> parameters) {
    	IntCodeComputer5A computer5A = (IntCodeComputer5A) computer;
        String outputValue = this.applyBeforeOutput(computer5A, parameters);
        try {
        	computer5A.writeOutput(outputValue);
		} catch (IOException e) {
			e.printStackTrace();
			computer5A.requestHalt(e.getMessage());
		}
    }

    protected abstract String applyBeforeOutput(IntCodeComputer5A computer, List<Integer> parameters);

}
