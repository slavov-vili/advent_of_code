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
            int curInstructionOpCode = curInstructionCode % 100;
            IntCodeInstruction curInstruction = this.instructionProvider.getInstructionByOpCode(curInstructionOpCode);
            int curInstructionParamCount = curInstruction.getParamCount();
            IntStream instructionInput = IntCodeComputerUtils.extractInputForInstruction(memory, curInstructionIdx,
                    curInstructionCode, curInstructionParamCount);

            int outputValue = curInstruction.apply(instructionInput);
            int outputIndex = memory.get(IntCodeComputerUtils.calcLastParamIndexForInstruction(curInstructionIdx,
                    curInstructionParamCount));

            memory.set(outputIndex, outputValue);

            curInstructionIdx = IntCodeComputerUtils.calcNextInstructionIndex(curInstructionIdx, curInstructionParamCount);
            curInstructionCode = memory.get(curInstructionIdx);
        } while (!this.codeIsHaltCode(curInstructionCode));

        System.out.println("HALT!");
        return memory;
    }

    private boolean codeIsHaltCode(int code) {
        return code == this.haltCode;
    }

}
