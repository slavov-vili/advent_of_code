package day02;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import day02.instructions.IntCodeInstruction;
import day02.instructions.IntCodeInstruction.ParamMode;
import utils.ListUtils;

public class IntCodeComputerUtils {

    protected static int calcParamsEndIndexForInstruction(int curInstructionIdx, IntCodeInstruction curInstruction) {
        return curInstructionIdx + curInstruction.getParamCount();
    }

    protected static int calcNextInstructionIndex(int curInstructionIdx, IntCodeInstruction curInstruction) {
        return calcParamsEndIndexForInstruction(curInstructionIdx, curInstruction) + 1;
    }

    protected static IntStream extractInputForInstruction(List<Integer> memory, int curInstructionIdx,
            IntCodeInstruction curInstruction) {
        List<Integer> valuesInMemory = memory.subList(curInstructionIdx + 1,
                calcParamsEndIndexForInstruction(curInstructionIdx, curInstruction));
        List<Integer> instructionInputs = new ArrayList<>();
        for (int i = 0; i < valuesInMemory.size(); i++)
        	instructionInputs.add(convertMemoryValueToInstructionInput(memory, valuesInMemory.get(i), i, curInstruction));
        return instructionInputs.stream()
        		.mapToInt(val -> val);
    }

    protected static Integer convertMemoryValueToInstructionInput(List<Integer> memory, Integer memoryValue, int memoryValueIndexForInstruction,
            IntCodeInstruction curInstruction) {
        if (curInstruction.paramModeIs(memoryValueIndexForInstruction, ParamMode.POSITION))
            return memory.get(memoryValue);
        
        else
            return memoryValue;
    }

    protected static int extractOutputIndexForInstruction(List<Integer> memory, int curInstructionIdx,
            IntCodeInstruction curInstruction) {
        int indexOfOutputParam = calcParamsEndIndexForInstruction(curInstructionIdx, curInstruction);
        return memory.get(indexOfOutputParam);
    }
}
