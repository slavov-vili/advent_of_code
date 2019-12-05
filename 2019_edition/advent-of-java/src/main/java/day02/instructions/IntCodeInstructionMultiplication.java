package day02.instructions;

import java.util.stream.IntStream;

public class IntCodeInstructionMultiplication extends IntCodeInstructionAbstract {

    public IntCodeInstructionMultiplication(int instructionCode, int paramCount) {
        super(instructionCode, paramCount);
    }
    
    @Override
    public int apply(IntStream inputValues) {
        return inputValues.reduce(1, (a, b) -> a * b);
    }

}
