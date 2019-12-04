package day02;

import java.util.List;
import java.util.stream.IntStream;

import day02.instructions.IntCodeInstruction;
import exceptions.InvalidIntCodeException;
import utils.ListUtils;

public class IntCodeComputer {
    private int haltCode;
    private IntCodeInstructionProvider instructionProvider;

    public IntCodeComputer(int haltCode, IntCodeInstructionProvider instructionProvider) {
        this.haltCode = haltCode;
        this.instructionProvider = instructionProvider;
    }

    public List<Integer> processInput(List<Integer> inputCodes, int startIndex) throws InvalidIntCodeException {
        List<Integer> memory = ListUtils.cloneList(inputCodes);
        int curInstructionIdx = startIndex;
        int curInstructionCode = memory.get(curInstructionIdx);

        do {
            IntCodeInstruction curInstruction = this.instructionProvider.getInstructionByCode(curInstructionCode);
            IntStream instructionInput = IntCodeComputerUtils.extractInputForInstruction(memory, curInstructionIdx,
                    curInstruction);

            int outputValue = curInstruction.apply(instructionInput);
            int outputIndex = IntCodeComputerUtils.extractOutputIndexForInstruction(memory, curInstructionIdx,
                    curInstruction);

            memory.set(outputIndex, outputValue);

            curInstructionIdx = IntCodeComputerUtils.calcNextInstructionIndex(curInstructionIdx, curInstruction);
            curInstructionCode = memory.get(curInstructionIdx);
        } while (!this.codeIsHaltCode(curInstructionCode));

        return memory;
    }

    private boolean codeIsHaltCode(int code) {
        return code == this.haltCode;
    }

}
