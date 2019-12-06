package day02.instructions;

import java.util.List;

public interface IntCodeInstruction {

    public enum ParamMode {
        POSITION, IMMEDIATE
    }

    public IntCodeInstructionResult apply(List<Integer> memory, List<Integer> parameterIndices,
            List<ParamMode> parameterModes);

    public int getCode();

    public int getParamCount();
}
