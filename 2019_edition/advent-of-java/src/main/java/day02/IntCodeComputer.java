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

    // TODO: Make this a type class which takes a number type and uses it for the
    // computations (it must take instructions of the same type!)
    public IntCodeComputer(IntCodeComputerState initialState, IntCodeInstructionProvider instructionProvider) {
        this.memory = initialState.getMemory();
        this.curInstructionIdx = initialState.getCurInstructionIdx();
        this.instructionProvider = instructionProvider;
        this.haltMessage = Optional.empty();
    }

    public IntCodeComputerState run()
            throws InvalidIntCodeException {
    	while (this.shouldContinue()) {

            IntCodeInstruction curInstruction = getCurInstruction();

            List<Integer> curInstructionParameters = IntCodeComputerUtils
                    .findInstructionParameters(this.memory, this.curInstructionIdx, curInstruction);
            handleCurrentInstruction(curInstruction, curInstructionParameters);
            
            curInstructionIdx = calcNextInstructionIndex(curInstruction);
        }
    	
    	if (this.haltMessage.isPresent())
    		System.out.println(this.haltMessage.get());
    	
    	return this.getCurrentState();
    }

    protected boolean shouldContinue() {
        return this.haltMessage.isEmpty();
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

    public IntCodeComputerState resetState(IntCodeComputerState newState) {
    	List<Integer> oldMemory = this.resetMemory(newState.getMemory());
    	int oldCurInstructionIdx = this.resetCurInstructionIdx(newState.getCurInstructionIdx());
        return new IntCodeComputerState(oldMemory, oldCurInstructionIdx);
    }

    private List<Integer> resetMemory(List<Integer> newMemory) {
        List<Integer> oldMemory = this.getMemory();
        this.memory = new ArrayList<>(newMemory);
        return oldMemory;
    }

    private int resetCurInstructionIdx(int newCurInstructionIdx) {
        int oldCurInstructionIdx = this.getCurInstructionIdx();
        this.curInstructionIdx = newCurInstructionIdx;
        return oldCurInstructionIdx;
    }

    public IntCodeComputerState getCurrentState() {
        return new IntCodeComputerState(this.getMemory(), this.getCurInstructionIdx());
    }

    public List<Integer> getMemory() {
        return this.memory;
    }
    
    public IntCodeInstructionProvider getInstructionProvider() {
    	return this.instructionProvider;
    }
    
    protected IntCodeInstruction getCurInstruction() throws InvalidIntCodeException {
    	IntCodeInstruction curInstruction = this.instructionProvider
			.getInstructionByOpCode(this.getCurInstructionCode());
    	return curInstruction;
    }
    
    protected int getCurInstructionCode() {
    	return getInstructionCode(this.getCurInstructionIdx());
    }

    protected int getInstructionCode(int instructionIdx) {
        return this.memory.get(instructionIdx);
    }

    protected int getCurInstructionIdx() {
        return this.curInstructionIdx;
    }

}
