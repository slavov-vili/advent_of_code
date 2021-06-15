package day07.instructions;

import java.util.List;
import java.util.Optional;

import day02.IntCodeComputer;
import day02.instructions.IntCodeInstructionAbstract;
import day07.IntCodeComputer7;

public abstract class IntCodeInstructionWithMissingInput extends IntCodeInstructionAbstract {

	public IntCodeInstructionWithMissingInput(int instructionCode, int paramCount) {
		super(instructionCode, paramCount);
	}

    @Override
    public void apply(IntCodeComputer computer, List<Integer> parameters) {
    	IntCodeComputer7 computer7 = (IntCodeComputer7) computer;
    	String instructionName = this.getClass().getSimpleName();
		System.out.printf("Instruction %s requires input: \n", instructionName);
		
    	if (!computer7.hasNextInput() ) {
    		computer7.setWaitingForInput(true);
    		computer7.setJumpIndex(Optional.of(computer7.getCurInstructionIdx()));
    	} else {
    		String input = computer7.getInput();
            System.out.printf("Instruction %s received input: %s\n", instructionName, input);
            
            this.applyWithInput(computer7, parameters, input);	
    	}
    }

    protected abstract void applyWithInput(IntCodeComputer7 computer, List<Integer> parameters, String userInput);


}
