package day02.instructions;

import java.util.stream.IntStream;

public interface IntCodeInstruction {

    public int apply(IntStream inputValues);

    public int getCode();

    public int getInputSize();
}
