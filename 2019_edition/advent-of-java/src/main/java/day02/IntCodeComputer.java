package day02;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import day02.instructions.IntCodeInstruction;
import exceptions.InvalidIntCodeException;

public class IntCodeComputer {
    private IntCodeInstructionProvider instructionProvider;
    private List<Integer> memory;
    private int curInstructionIdx;
	private Optional<String> haltMessage;

    // TODO: Make this a type class which takes a number type and uses it for the computations
	// TODO: Abstract reading and writing to memory/
    public IntCodeComputer(List<Integer> initialMemory, IntCodeInstructionProvider instructionProvider) {
        this.memory = new ArrayList<>(initialMemory);
        this.curInstructionIdx = 0;
        this.instructionProvider = instructionProvider;
        this.haltMessage = Optional.empty();
    }

    public void run() throws InvalidIntCodeException {
    	while (!this.shouldStop()) {
            IntCodeInstruction curInstruction = getCurInstruction();

            List<Integer> curInstructionParameters = IntCodeComputerUtils
                    .findInstructionParameters(this.memory, this.curInstructionIdx, curInstruction);
            handleCurrentInstruction(curInstruction, curInstructionParameters);
            
            curInstructionIdx = calcNextInstructionIndex(curInstruction);
        }
    	
    	if (this.haltMessage.isPresent())
    		System.out.println("Halt Message: " + this.haltMessage.get());
    }

    protected boolean shouldStop() {
        return this.haltMessage.isPresent();
    }
    
	public void requestHalt(String message) {
		this.haltMessage = Optional.of(message);
	}
	
	public boolean isHalted() {
		return this.haltMessage.isPresent();
	}
    
    public void handleCurrentInstruction(IntCodeInstruction curInstruction,
    		List<Integer> curInstructionParameters) {
    	List<Integer> parameterValues = IntCodeComputerUtils
    			.findInstructionParameterValues(this.memory, curInstructionParameters);
        curInstruction.apply(this, parameterValues);
    }
    
    public int calcNextInstructionIndex(IntCodeInstruction curInstruction) {
        return IntCodeComputerUtils
        		.calcEndIdxOfInstruction(this.curInstructionIdx, curInstruction) + 1;
    }

    public List<Integer> getMemory() {
        return this.memory;
    }
    
    public IntCodeInstructionProvider getInstructionProvider() {
    	return this.instructionProvider;
    }
    
    public IntCodeInstruction getCurInstruction() throws InvalidIntCodeException {
    	IntCodeInstruction curInstruction = this.instructionProvider
			.getInstructionByOpCode(this.getCurInstructionCode());
    	return curInstruction;
    }
    
    public int getCurInstructionCode() {
    	return getInstructionCode(this.getCurInstructionIdx());
    }

    public int getInstructionCode(int instructionIdx) {
        return this.memory.get(instructionIdx);
    }

    public int getCurInstructionIdx() {
        return this.curInstructionIdx;
    }

}
