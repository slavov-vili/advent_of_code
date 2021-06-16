package day05.instructions;

import java.util.List;

import day02.IntCodeComputer;
import day02.instructions.IntCodeInstructionAbstract;
import day05.A.IntCodeComputer5A;

public abstract class IntCodeInstructionWithInput extends IntCodeInstructionAbstract {

    public IntCodeInstructionWithInput(Long instructionCode, int paramCount) {
        super(instructionCode, paramCount);
    }

    @Override
    public void apply(IntCodeComputer computer, List<Long> parameters) {
    	IntCodeComputer5A computer5A = (IntCodeComputer5A) computer;
    	String instructionName = this.getClass().getSimpleName();
		System.out.printf("Instruction %s requires input: \n", instructionName);
		
		String input = computer5A.getInput();
        System.out.printf("Instruction %s received input: %s\n", instructionName, input);
        
        this.applyWithInput(computer5A, parameters, input);	
    }

    protected abstract void applyWithInput(IntCodeComputer5A computer, List<Long> parameters, String userInput);

}
