package day02.instructions;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import day02.IntCodeComputerState.ExecutionCode;

public class IntCodeInstructionHalt extends IntCodeInstructionAbstract {

    public IntCodeInstructionHalt(int opCode) {
        super(opCode, 0);
    }

    @Override
    public IntCodeInstructionResult apply(List<Integer> memory, List<Integer> parameterIndices,
            List<ParamMode> parameterModes, Scanner inputScanner, Writer outputWriter) throws IOException {
        return new IntCodeInstructionResult(Optional.empty(), Optional.empty(), ExecutionCode.HALT);
    }

}
