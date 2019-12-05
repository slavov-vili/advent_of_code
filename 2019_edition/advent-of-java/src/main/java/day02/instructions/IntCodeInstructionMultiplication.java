package day02.instructions;

import java.util.stream.IntStream;

public class IntCodeInstructionMultiplication extends IntCodeInstructionAbstract {

    public IntCodeInstructionMultiplication(int instructionCode, int paramCount) {
        super(instructionCode, paramCount);
    }
    
    @Override
    public int apply(IntStream valuesIncludingOutput) {
        return valuesIncludingOutput.limit(this.getParamCount()-1).reduce(1, (a, b) -> a * b);
    }

}
