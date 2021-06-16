package day09;

import java.util.List;

import day02.IntCodeInstructionProvider;
import day05.IntCodeInstructionParameterEvaluator;
import day07.IntCodeComputer7;

// An IntCodeComputer7 with support for:
//	 - managing a relativeBase
//	 - Accessing memory beyond the initial input
public class IntCodeComputer9 extends IntCodeComputer7 {
	private Long relativeBase;
	
	public IntCodeComputer9(List<? extends Number> initialMemory, IntCodeInstructionProvider instructionProvider,
			IntCodeInstructionParameterEvaluator modeHandler) {
		super(initialMemory, instructionProvider, modeHandler);
		this.relativeBase = 0L;
	}

	public Long getRelativeBase() {
		return this.relativeBase;
	}
	
	public void setRelativeBase(Long offset) {
		this.relativeBase += offset;
	}
	
	public <T extends Number> Long readFromMemory(T addressToRead) {
    	checkIndex(addressToRead);
        return this.memory.getOrDefault(addressToRead.longValue(), 0L);
    }
	
	@Override
	protected <T extends Number> void checkIndex(T addressToCheck) {
		try {
    		super.checkIndex(addressToCheck);
    	} catch (IndexOutOfBoundsException e) {
    		if ((addressToCheck.longValue() < 0) || (addressToCheck.longValue() > Integer.MAX_VALUE))
    			throw new IndexOutOfBoundsException(String.valueOf(addressToCheck));
    	}
    }
}
