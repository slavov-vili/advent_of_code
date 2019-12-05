package day02;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import day02.instructions.IntCodeInstruction;
import day02.instructions.IntCodeInstruction.ParamMode;
import day02.instructions.IntCodeInstructionUtils;
import utils.ListUtils;

public class IntCodeComputerUtils {

    protected static int calcLastParamIndexForInstruction(int curInstructionIdx, int curInstructionParamCount) {
        return curInstructionIdx + curInstructionParamCount;
    }

    protected static int calcNextInstructionIndex(int curInstructionIdx, int curInstructionParamCount) {
        return calcLastParamIndexForInstruction(curInstructionIdx, curInstructionParamCount) + 1;
    }

    protected static IntStream extractInputForInstruction(List<Integer> memory, int curInstructionIdx,
            int curInstructionCode, int curInstructionParamCount) {
        List<Integer> valuesInMemory = memory.subList(curInstructionIdx + 1,
                calcLastParamIndexForInstruction(curInstructionIdx, curInstructionParamCount) + 1);
        Map<Integer, ParamMode> paramIndexToMode = IntCodeInstructionUtils.mapParamIndexToMode(curInstructionCode,
                curInstructionParamCount);

        return IntStream.range(0, valuesInMemory.size())
                .map(valueIdx -> convertMemoryValueToInstructionValue(memory, valuesInMemory.get(valueIdx),
                        paramIndexToMode.get(valueIdx)));
    }

    protected static Integer convertMemoryValueToInstructionValue(List<Integer> memory, Integer paramValue,
            ParamMode paramMode) {
        if (paramMode == ParamMode.POSITION)
            return memory.get(paramValue);
        else
            return paramValue;
    }
}
