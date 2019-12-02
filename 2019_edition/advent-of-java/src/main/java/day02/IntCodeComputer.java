package day02;

import java.util.List;

public class IntCodeComputer {
    private static int INT_CODE_TERMINATE = 99;
    
    private List<Integer> memory;
    private int stepsToNextIntCode;
    private int curIntCodeIdx;

    public IntCodeComputer(List<Integer> inputIntCodes, int stepsToNextIntCode) {
	this.curIntCodeIdx = 0;
	this.memory = inputIntCodes;
	this.stepsToNextIntCode = stepsToNextIntCode;
    }
    
    public void processInput() throws InvalidIntCodeException {
	int curIntCode = this.getCurIntCode();
	while (curIntCode != INT_CODE_TERMINATE) {
	    handleIntCode(curIntCode);
	}
    }

    private void handleIntCode(int code) throws InvalidIntCodeException {
	int arg1 = this.getValueAt(curIntCodeIdx + 1);
	int arg2 = this.getValueAt(curIntCodeIdx + 2);
	int out = this.getValueAt(curIntCodeIdx + 3);
	
	switch (code) {
	case 1: {
	    int newValue = addValuesAt(arg1, arg2);
	    this.setValueAt(out, newValue);
	    break;
	}
	case 2: {
	    int newValue = multiplyValuesAt(arg1, arg2);
	    this.setValueAt(out, newValue);
	    break;
	}
	default:
	    throw new InvalidIntCodeException("IntCode " + code + " is not valid!");
	}
	
	updateNextInstructionIdx();
    }

    private int addValuesAt(int idxA, int idxB) {
	return this.getValueAt(idxA) + this.getValueAt(idxB);
    }

    private int multiplyValuesAt(int idxA, int idxB) {
	return this.getValueAt(idxA) * this.getValueAt(idxB);
    }

    private void updateNextInstructionIdx() {
	this.curIntCodeIdx += this.stepsToNextIntCode;
    }

    
    
    private int getCurIntCode() {
	return this.getValueAt(this.curIntCodeIdx);
    }
    
    public int getValueAt(int idx) {
	return memory.get(idx);
    }
    
    private void setValueAt(int idx, int newValue) {
	this.memory.set(idx, newValue);
    }

    public List<Integer> getEndSequence() {
	return this.memory;
    }
}
