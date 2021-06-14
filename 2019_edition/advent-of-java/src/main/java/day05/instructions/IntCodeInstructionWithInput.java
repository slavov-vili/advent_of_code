package day05.instructions;

import java.util.List;
import day05.IntCodeComputer5A;

public abstract class IntCodeInstructionWithInput extends IntCodeInstruction5AAbstract {

    public IntCodeInstructionWithInput(int instructionCode, int paramCount) {
        super(instructionCode, paramCount);
    }

    @Override
    public void apply(IntCodeComputer5A computer, List<Integer> parameters) {
        String prompt = "Instruction " + this.getClass().getSimpleName() + " requires input: ";
        String input = computer.getInput(prompt);
        
        this.applyWithInput(computer, parameters, input);
    }

    protected abstract void applyWithInput(IntCodeComputer5A computer, List<Integer> parameters, String userInput);

}
