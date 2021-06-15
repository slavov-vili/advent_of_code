package day07.instructions;

import java.util.List;
import java.util.Optional;

import day07.IntCodeComputer7;

public abstract class IntCodeInstructionWithMissingInput extends IntCodeInstruction7Abstract {

	public IntCodeInstructionWithMissingInput(int instructionCode, int paramCount) {
		super(instructionCode, paramCount);
	}

    @Override
    public void apply(IntCodeComputer7 computer, List<Integer> parameters) {
    	String instructionName = this.getClass().getSimpleName();
		System.out.printf("Instruction %s requires input: \n", instructionName);
		
    	if (!computer.hasNextInput() ) {
    		computer.setWaitingForInput(true);
    		computer.setJumpIndex(Optional.of(computer.getCurInstructionIdx()));
    	} else {
    		String input = computer.getInput();
            System.out.printf("Instruction %s received input: %s\n", instructionName, input);
            
            this.applyWithInput(computer, parameters, input);	
    	}
    }

    protected abstract void applyWithInput(IntCodeComputer7 computer, List<Integer> parameters, String userInput);


}
