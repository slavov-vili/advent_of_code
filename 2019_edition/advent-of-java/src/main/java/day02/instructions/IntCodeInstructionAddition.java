package day02.instructions;

import java.util.stream.IntStream;

public class IntCodeInstructionAddition implements IntCodeInstruction {

    private int inputSize;
    private int instructionCode;

    public IntCodeInstructionAddition(int instructionCode, int inputSize) {
        this.instructionCode = instructionCode;
        this.inputSize = inputSize;
    }

    public int apply(IntStream inputValues) {
        return inputValues.sum();
    }

    public int getCode() {
        return this.instructionCode;
    }

    public int getInputSize() {
        return this.inputSize;
    }

}
