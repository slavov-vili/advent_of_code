package day02_old.instructions;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Scanner;

public interface IntCodeInstruction {

    public enum ParamMode {
        POSITION, IMMEDIATE
    }

    public IntCodeInstructionResult apply(List<Integer> memory, List<Integer> parameterIndices,
            List<ParamMode> parameterModes, Scanner inputScanner, Writer outputWriter) throws IOException;

    public int getCode();

    public int getParamCount();
}
