package day02;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import day02.instructions.IntCodeInstruction;
import utils.ListUtils;

public class IntCodeComputerUtils {

	public static List<Integer> findInstructionParameterValues(List<Integer> memory,
			List<Integer> instructionParameters) {
		List<Integer> parameterValues = new ArrayList<>(instructionParameters);
		for (int i=0; i<parameterValues.size()-1; i++)
			parameterValues.set(i, memory.get(parameterValues.get(i)));
		return parameterValues;
	}
	
	public static List<Integer> findInstructionParameters(List<Integer> memory,
			int curInstructionIdx, IntCodeInstruction curInstruction) {
		return findInstructionParamIndices(curInstructionIdx, curInstruction).stream()
				.map(memory::get)
				.collect(Collectors.toList());
	}
	
    public static List<Integer> findInstructionParamIndices(int curInstructionIdx,
            IntCodeInstruction curInstruction) {
    	int paramCount = curInstruction.getParamCount();
    	if (paramCount == 0)
    		return List.of();
    	else
    		return ListUtils.generateRange(curInstructionIdx + 1,
                calcEndIdxOfInstruction(curInstructionIdx, curInstruction));
    }
	
    public static int calcEndIdxOfInstruction(int instructionIdx, IntCodeInstruction curInstruction) {
        return instructionIdx + curInstruction.getParamCount();
    }

}
