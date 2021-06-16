package day05.B;

import java.util.List;
import java.util.Optional;

import day02.IntCodeInstructionProvider;
import day02.instructions.IntCodeInstruction;
import day05.IntCodeInstructionParameterModeHandler;
import day05.A.IntCodeComputer5A;

//An IntCodeComputer5A with support for:
//- Jump Instructions
public class IntCodeComputer5B extends IntCodeComputer5A {

	private Optional<Integer> jumpIndex;

	public IntCodeComputer5B(List<? extends Number> initialMemory, IntCodeInstructionProvider instructionProvider,
			IntCodeInstructionParameterModeHandler modeHandler) {
		super(initialMemory, instructionProvider, modeHandler);
		this.jumpIndex = Optional.empty();
	}
	
	@Override
	public int calcNextInstructionIndex(IntCodeInstruction curInstruction) {
		int nextIndex = (this.jumpIndex.isPresent()) ?
				this.jumpIndex.get() : super.calcNextInstructionIndex(curInstruction);
		this.setJumpIndex(Optional.empty());
        return nextIndex;
    }
	
	public void setJumpIndex(Long newJumpIndex) {
		setJumpIndex(Optional.of(newJumpIndex.intValue()));
	}
	
	public void setJumpIndex(int newJumpIndex) {
		setJumpIndex(Optional.of(newJumpIndex));
	}
	
	public void setJumpIndex(Optional<Integer> newJumpIndex) {
		this.jumpIndex = newJumpIndex;
	}
}
