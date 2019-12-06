package day02;

import java.util.List;

import day02.instructions.IntCodeInstruction.ParamMode;
import day02.instructions.IntCodeInstructionResult;
import utils.ListUtils;

public class IntCodeComputerUtils {

    protected static int calcLastParamIndexForInstruction(int curInstructionIdx, int curInstructionParamCount) {
        return curInstructionIdx + curInstructionParamCount;
    }

    protected static int calcNextInstructionIndex(int curInstructionIdx, IntCodeInstructionResult curInstructionResult,
            int curInstructionParamCount) {
        if (curInstructionResult.nextInstructionIndex.isPresent())
            return curInstructionResult.nextInstructionIndex.get();

        return calcLastParamIndexForInstruction(curInstructionIdx, curInstructionParamCount) + 1;
    }

    protected static List<Integer> findInstructionParamIndices(int curInstructionIdx, int curInstructionParamCount) {
        List<Integer> paramIndices = ListUtils.generateRange(curInstructionIdx + 1,
                calcLastParamIndexForInstruction(curInstructionIdx, curInstructionParamCount));

        return paramIndices;
    }

    public static Integer convertParameterValueToInputValue(List<Integer> memory, Integer paramValue,
            ParamMode paramMode) {
        if (paramMode == ParamMode.POSITION)
            return memory.get(paramValue);
        else
            return paramValue;
    }

    public static Integer convertParameterValueToWriteIndex(int writeParameterIndexInMemory, int writeParameterValue,
            ParamMode writeParameterMode) {
        if (writeParameterMode == ParamMode.POSITION)
            return writeParameterValue;
        else
            return writeParameterIndexInMemory;
    }

    protected static int calcOutputIndexForInstruction(int curInstructionIdx, int indexOfOutputParameter) {
        return curInstructionIdx + indexOfOutputParameter + 1;
    }
}
