package day02.instructions;

import java.util.Optional;

public class IntCodeInstructionResult {
    public int outputValue;
    public int outputIndex;
    public Optional<Integer> nextInstructionIndex;

    public IntCodeInstructionResult(int outputValue, int outputIndex, Optional<Integer> nextInstructionIndex) {
        this.outputValue = outputValue;
        this.outputIndex = outputIndex;
        this.nextInstructionIndex = nextInstructionIndex;
    }
}
