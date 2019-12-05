package day02.instructions;

import java.util.stream.IntStream;

public interface IntCodeInstruction {

    public enum ParamMode {
        POSITION, IMMEDIATE
    }

    public int apply(IntStream valuesIncludingOutput);

    public int getCode();

    public int getParamCount();
}
