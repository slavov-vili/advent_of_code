package day02.instructions;

import java.util.List;

public interface IntCodeInstruction {

    public enum ParamMode {
        POSITION, IMMEDIATE
    }

    public IntCodeInstructionResult apply(List<Integer> valuesIncludingOutput);

    public int getCode();

    public int getParamCount();
}
