package day02.instructions;

import java.util.List;

public interface IntCodeInstruction {

    public int apply(List<Integer> inputValues);

    public int getCode();

    public int getInputSize();
}
