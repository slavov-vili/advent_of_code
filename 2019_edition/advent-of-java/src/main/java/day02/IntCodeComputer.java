package day02;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import day02.instructions.IntCodeInstruction;
import exceptions.InvalidIntCodeException;

public class IntCodeComputer {
    private IntCodeInstructionProvider instructionProvider;
    private Map<Long, Long> memory;
    private int curInstructionIdx;
	private Optional<String> haltMessage;

    public IntCodeComputer(List<? extends Number> initialMemory, IntCodeInstructionProvider instructionProvider) {
        setMemory(initialMemory);
        this.curInstructionIdx = 0;
        this.instructionProvider = instructionProvider;
        this.haltMessage = Optional.empty();
    }

    public void run() throws InvalidIntCodeException {
    	while (!this.shouldStop()) {
            IntCodeInstruction curInstruction = getCurInstruction();

            List<Long> curInstructionParameters = IntCodeComputerUtils
                    .findInstructionParameters(this, curInstruction);
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
    		List<Long> curInstructionParameters) {
    	List<Long> parameterValues = IntCodeComputerUtils
    			.findInstructionParameterValues(this, curInstructionParameters);
        curInstruction.apply(this, parameterValues);
    }
    
    public int calcNextInstructionIndex(IntCodeInstruction curInstruction) {
        return IntCodeComputerUtils
        		.calcEndIdxOfInstruction(this.curInstructionIdx, curInstruction) + 1;
    }
    
    public void setMemory(List<? extends Number> initialMemory) {
    	Map<Long, Long> newMemory = new HashMap<>();
    	for (Integer i=0; i<initialMemory.size(); i++)
    		newMemory.put(i.longValue(), initialMemory.get(i).longValue());
    	this.memory = newMemory;
    }

    public <T extends Number> Long readFromMemory(T addressToRead) {
    	checkIndex(addressToRead);
        return this.memory.get(addressToRead.longValue());
    }
    
    public <T extends Number> Long setMemoryAddress(T address, T value) {
    	checkIndex(address);
        return this.memory.put(address.longValue(), value.longValue());
    }
    
    private <T extends Number> void checkIndex(T addressToCheck) {
    	if ((addressToCheck.longValue() < 0) || (addressToCheck.longValue() > Integer.MAX_VALUE))
    		throw new IndexOutOfBoundsException(String.valueOf(addressToCheck));
    }
    
    public IntCodeInstructionProvider getInstructionProvider() {
    	return this.instructionProvider;
    }
    
    public IntCodeInstruction getCurInstruction() throws InvalidIntCodeException {
    	IntCodeInstruction curInstruction = this.instructionProvider
			.getInstructionByOpCode(this.getCurInstructionCode());
    	return curInstruction;
    }
    
    public Long getCurInstructionCode() {
    	return getInstructionCode(this.getCurInstructionIdx());
    }

    public Long getInstructionCode(int instructionIdx) {
        return this.readFromMemory(instructionIdx);
    }

    public int getCurInstructionIdx() {
        return this.curInstructionIdx;
    }

}
