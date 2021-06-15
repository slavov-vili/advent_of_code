package day05.instructions;

import java.util.List;

import day05.A.IntCodeComputer5A;
import day05.A.instructions.IntCodeInstruction5AAbstract;

public abstract class IntCodeInstructionWithInput extends IntCodeInstruction5AAbstract {

    public IntCodeInstructionWithInput(int instructionCode, int paramCount) {
        super(instructionCode, paramCount);
    }

    @Override
    public void apply(IntCodeComputer5A computer, List<Integer> parameters) {
    	String instructionName = this.getClass().getSimpleName();
		System.out.printf("Instruction %s requires input: \n", instructionName);
        String input = computer.getInput();
        System.out.printf("Instruction %s received input: %s\n", instructionName, input);
        
        this.applyWithInput(computer, parameters, input);
    }

    protected abstract void applyWithInput(IntCodeComputer5A computer, List<Integer> parameters, String userInput);

}
