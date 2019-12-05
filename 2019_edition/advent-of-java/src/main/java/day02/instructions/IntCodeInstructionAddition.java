package day02.instructions;

import java.util.stream.IntStream;

public class IntCodeInstructionAddition extends IntCodeInstructionAbstract {

    public IntCodeInstructionAddition(int instructionCode, int paramCount) {
        super(instructionCode, paramCount);
    }
    
    @Override
    public int apply(IntStream inputValues) {
        return inputValues.sum();
    }

}
