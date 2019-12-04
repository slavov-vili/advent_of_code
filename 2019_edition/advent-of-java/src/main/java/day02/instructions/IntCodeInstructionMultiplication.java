package day02.instructions;

import java.util.stream.IntStream;

public class IntCodeInstructionMultiplication implements IntCodeInstruction {

    private int inputSize;
    private int instructionCode;

    public IntCodeInstructionMultiplication(int instructionCode, int inputSize) {
        this.instructionCode = instructionCode;
        this.inputSize = inputSize;
    }

    public int apply(IntStream inputValues) {
        return inputValues.reduce(1, (a, b) -> a * b);
    }

    public int getCode() {
        return this.instructionCode;
    }

    public int getInputSize() {
        return this.inputSize;
    }

}
