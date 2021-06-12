package day02;

import java.util.List;
import java.util.stream.Collectors;

import day02.instructions.IntCodeInstruction;
import utils.ListUtils;

public class IntCodeComputerUtils {

	protected static List<Integer> findInstructionParameters(List<Integer> memory,
			int curInstructionIdx, IntCodeInstruction curInstruction) {
		return findInstructionParamIndices(curInstructionIdx, curInstruction).stream()
				.map(memory::get)
				.collect(Collectors.toList());
	}
	
    protected static List<Integer> findInstructionParamIndices(int curInstructionIdx,
            IntCodeInstruction curInstruction) {
    	int paramCount = curInstruction.getParamCount();
    	if (paramCount == 0)
    		return List.of();
    	else
    		return ListUtils.generateRange(curInstructionIdx + 1,
                calcEndIdxOfInstruction(curInstructionIdx, curInstruction.getParamCount()));
    }
	
    protected static int calcNextInstructionIndex(int curInstructionIdx, IntCodeInstruction curInstruction) {
        return calcEndIdxOfInstruction(curInstructionIdx, curInstruction.getParamCount()) + 1;
    }
	
    protected static int calcEndIdxOfInstruction(int instructionIdx, int instructionParamCount) {
        return instructionIdx + instructionParamCount;
    }

}
