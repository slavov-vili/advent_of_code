package day09;

import java.util.List;

import day02.IntCodeInstructionProvider;
import day05.IntCodeInstructionParameterModeHandler;
import day07.IntCodeComputer7;

//An IntCodeComputer7 with support for:
//- managing a relativeBase
public class IntCodeComputer9 extends IntCodeComputer7 {
	private Integer relativeBase;
	
	public IntCodeComputer9(List<Integer> initialMemory, IntCodeInstructionProvider instructionProvider,
			IntCodeInstructionParameterModeHandler modeHandler) {
		super(initialMemory, instructionProvider, modeHandler);
		this.relativeBase = 0;
	}

	public Integer getRelativeBase() {
		return this.relativeBase;
	}
	
	public void setRelativeBase(Integer newRelativeBase) {
		this.relativeBase = newRelativeBase;
	}
}
