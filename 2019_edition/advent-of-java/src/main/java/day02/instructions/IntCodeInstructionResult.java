package day02.instructions;

public class IntCodeInstructionResult {
    public int outputValue;
    public int instructionJumpSize;

    public IntCodeInstructionResult(int outputValue, int instructionJumpSize) {
        this.outputValue = outputValue;
        this.instructionJumpSize = instructionJumpSize;
    }
}
