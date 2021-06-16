package day05.instructions;

import java.util.List;

import day02.IntCodeComputer;
import day02.instructions.IntCodeInstructionAbstract;

public class IntCodeInstructionAdditionWriting extends IntCodeInstructionAbstract
	implements IntCodeInstructionWriting {

    private int writeParamIndex;

	public IntCodeInstructionAdditionWriting(Long instructionCode) {
        super(instructionCode, 3);
        this.writeParamIndex = 2;
    }

    @Override
    public void apply(IntCodeComputer computer, List<Long> parameters) {
        int writeIndex = parameters.get(this.writeParamIndex).intValue();
        Long result = parameters.get(0) + parameters.get(1);
        computer.setMemoryAddress(writeIndex, result);
    }

	@Override
	public int getWriteParamIndex() {
		return this.writeParamIndex;
	}

}
