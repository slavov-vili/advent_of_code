package day02_old;

import java.util.List;

import day02.instructions.IntCodeInstruction;
import day02.instructions.IntCodeInstruction.ParamMode;
import day02.instructions.IntCodeInstructionResult;
import utils.ListUtils;

public class IntCodeComputerUtils {

    protected static int calcLastParamIndexForInstruction(int curInstructionIdx, int curInstructionParamCount) {
        return curInstructionIdx + curInstructionParamCount;
    }

    protected static int calcNextInstructionIndex(int curInstructionIdx, IntCodeInstructionResult curInstructionResult,
            IntCodeInstruction curInstruction) {
        if (curInstructionResult.nextInstructionIndex.isPresent())
            return curInstructionResult.nextInstructionIndex.get();

        return calcLastParamIndexForInstruction(curInstructionIdx, curInstruction.getParamCount()) + 1;
    }

    protected static List<Integer> findInstructionParamIndices(int curInstructionIdx,
            IntCodeInstruction curInstruction) {
        List<Integer> paramIndices = ListUtils.generateRange(curInstructionIdx + 1,
                calcLastParamIndexForInstruction(curInstructionIdx, curInstruction.getParamCount()));

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
