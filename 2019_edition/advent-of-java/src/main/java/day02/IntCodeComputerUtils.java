package day02;

import java.util.List;
import java.util.stream.IntStream;

import day02.instructions.IntCodeInstruction;
import utils.ListUtils;

public class IntCodeComputerUtils {

    protected static int calcIndexOfOutputIndexForInstruction(int curInstructionIdx,
            IntCodeInstruction curInstruction) {
        return curInstructionIdx + curInstruction.getInputSize() + 1;
    }

    protected static int calcNextInstructionIndex(int curInstructionIdx, IntCodeInstruction curInstruction) {
        return calcIndexOfOutputIndexForInstruction(curInstructionIdx, curInstruction) + 1;
    }

    protected static IntStream extractInputForInstruction(List<Integer> memory, int curInstructionIdx,
            IntCodeInstruction curInstruction) {
        List<Integer> inputIndices = memory.subList(curInstructionIdx + 1,
                calcIndexOfOutputIndexForInstruction(curInstructionIdx, curInstruction));
        return ListUtils.getListElementsAt(memory, inputIndices).mapToInt(val -> val);
    }

    protected static int extractOutputIndexForInstruction(List<Integer> memory, int curInstructionIdx,
            IntCodeInstruction curInstruction) {
        int indexOfOutputIndex = calcIndexOfOutputIndexForInstruction(curInstructionIdx, curInstruction);
        return memory.get(indexOfOutputIndex);
    }
}
