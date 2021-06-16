package day09;

import java.util.List;

import day02.IntCodeInstructionProvider;
import day05.IntCodeInstructionParameterModeHandler;
import day07.IntCodeComputer7;

//An IntCodeComputer7 with support for:
//- managing a relativeBase
public class IntCodeComputer9 extends IntCodeComputer7 {
	private Long relativeBase;
	
	public IntCodeComputer9(List<? extends Number> initialMemory, IntCodeInstructionProvider instructionProvider,
			IntCodeInstructionParameterModeHandler modeHandler) {
		super(initialMemory, instructionProvider, modeHandler);
		this.relativeBase = 0L;
	}

	public Long getRelativeBase() {
		return this.relativeBase;
	}
	
	public void setRelativeBase(Long newRelativeBase) {
		this.relativeBase = newRelativeBase;
	}
}
