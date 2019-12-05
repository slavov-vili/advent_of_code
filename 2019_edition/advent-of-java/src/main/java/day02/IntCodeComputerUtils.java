package day02;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import day02.instructions.IntCodeInstruction;
import day02.instructions.IntCodeInstruction.ParamMode;
import utils.ListUtils;

public class IntCodeComputerUtils {

    protected static int calcOutputParamIndexForInstruction(int curInstructionIdx, IntCodeInstruction curInstruction) {
        return curInstructionIdx + curInstruction.getParamCount();
    }

    protected static int calcNextInstructionIndex(int curInstructionIdx, IntCodeInstruction curInstruction) {
        return calcOutputParamIndexForInstruction(curInstructionIdx, curInstruction) + 1;
    }

    protected static IntStream extractInputForInstruction(List<Integer> memory, int curInstructionIdx,
            IntCodeInstruction curInstruction) {
        List<Integer> valuesInMemory = memory.subList(curInstructionIdx + 1,
                calcOutputParamIndexForInstruction(curInstructionIdx, curInstruction)+1);
        List<Integer> instructionInputs = new ArrayList<>();
        
        for (int i = 0; i < valuesInMemory.size(); i++)
        	instructionInputs.add(convertMemoryValueToInstructionValue(memory, valuesInMemory.get(i), i, curInstruction));
        
        return instructionInputs.stream()
        		.mapToInt(val -> val);
    }

    protected static Integer convertMemoryValueToInstructionValue(List<Integer> memory, Integer memoryValue, int memoryValueIndexForInstruction,
            IntCodeInstruction curInstruction) {
        if (curInstruction.paramModeIs(memoryValueIndexForInstruction, ParamMode.POSITION))
            return memory.get(memoryValue);
        
        else
            return memoryValue;
    }

    protected static int extractOutputIndexForInstruction(List<Integer> memory, int curInstructionIdx,
            IntCodeInstruction curInstruction) {
        int indexOfOutputParam = calcOutputParamIndexForInstruction(curInstructionIdx, curInstruction);
        return convertMemoryValueToInstructionValue(memory, memory.get(indexOfOutputParam), curInstruction.getParamCount()-1, curInstruction);
    }
}
