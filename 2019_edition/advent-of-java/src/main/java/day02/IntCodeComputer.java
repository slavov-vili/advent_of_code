package day02;

import java.util.ArrayList;
import java.util.List;
import day02.instructions.IntCodeInstruction;
import exceptions.InvalidIntCodeException;

public class IntCodeComputer {
    private IntCodeInstructionProvider instructionProvider;
    private List<Integer> memory;
    private int curInstructionIdx;

    // TODO: Make this a type class which takes a number type and uses it for the
    // computations (it must take instructions of the same type!)
    public IntCodeComputer(IntCodeComputerState initialState, IntCodeInstructionProvider instructionProvider) {
        this.memory = initialState.getMemory();
        this.curInstructionIdx = initialState.getCurInstructionIdx();
        this.instructionProvider = instructionProvider;
    }

    public IntCodeComputerState run()
            throws InvalidIntCodeException {
    	while (this.shouldContinue()) {

            IntCodeInstruction curInstruction = this.instructionProvider
            		.getInstructionByOpCode(this.getCurInstructionOpCode());
            
            List<Integer> curInstructionParameters = IntCodeComputerUtils
                    .findInstructionParameters(this.memory, curInstructionIdx, curInstruction);

            curInstruction.apply(this.memory, curInstructionParameters);

            curInstructionIdx = IntCodeComputerUtils.calcNextInstructionIndex(curInstructionIdx, curInstruction);
        };

        return this.getCurrentState();
    }

    private boolean shouldContinue() {
        return !instructionProvider.getHaltInstructionOpCode()
        		.equals(this.getCurInstructionOpCode());
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

    private List<Integer> getMemory() {
        return new ArrayList<>(this.memory);
    }

    private int getCurInstructionIdx() {
        return this.curInstructionIdx;
    }
    
    private int getCurInstructionOpCode() {
    	return getInstructionOpCode(this.getCurInstructionIdx());
    }

    private int getInstructionOpCode(int instructionIdx) {
        return this.memory.get(instructionIdx);
    }

}
