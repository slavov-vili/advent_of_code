package day02;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.util.Scanner;

import day02.instructions.IntCodeInstruction;
import day02.instructions.IntCodeInstruction.ParamMode;
import day02.instructions.IntCodeInstructionResult;
import day02.instructions.IntCodeInstructionUtils;
import exceptions.InvalidIntCodeException;
import utils.ListUtils;

public class IntCodeComputer {
    private int haltCode;
    private IntCodeInstructionProvider instructionProvider;

    public IntCodeComputer(int haltCode, IntCodeInstructionProvider instructionProvider) {
        this.haltCode = haltCode;
        this.instructionProvider = instructionProvider;
    }

    public List<Integer> processCodes(List<Integer> inputCodes, int startIndex, Reader inputReader, Writer outputWriter)
            throws InvalidIntCodeException, IOException {
        Scanner inputScanner = new Scanner(inputReader);
        List<Integer> memory = ListUtils.cloneList(inputCodes);
        int curInstructionIdx = startIndex;
        int curInstructionCode = memory.get(curInstructionIdx);
        // TODO: move this to utils
        int curInstructionOpCode = curInstructionCode % 100;

        do {
            IntCodeInstruction curInstruction = this.instructionProvider.getInstructionByOpCode(curInstructionOpCode);
            int curInstructionParamCount = curInstruction.getParamCount();
            List<Integer> curInstructionInputIndices = IntCodeComputerUtils
                    .findInstructionParamIndices(curInstructionIdx, curInstructionParamCount);
            List<ParamMode> curInstructionParamModesInOrder = IntCodeInstructionUtils
                    .generateParameterModesInOrder(curInstructionCode, curInstructionParamCount);

            IntCodeInstructionResult instructionResult = curInstruction.apply(memory, curInstructionInputIndices,
                    curInstructionParamModesInOrder, inputScanner, outputWriter);
            int outputIndex = instructionResult.outputIndex;

            memory.set(outputIndex, instructionResult.outputValue);

            curInstructionIdx = IntCodeComputerUtils.calcNextInstructionIndex(curInstructionIdx, instructionResult,
                    curInstructionParamCount);
            curInstructionCode = memory.get(curInstructionIdx);
            curInstructionOpCode = curInstructionCode % 100;
        } while (!this.codeIsHaltCode(curInstructionOpCode));

        // System.out.println("HALT!");
        return memory;
    }

    private boolean codeIsHaltCode(int code) {
        return code == this.haltCode;
    }

}
