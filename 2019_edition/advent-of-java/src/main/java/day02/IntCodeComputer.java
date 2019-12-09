package day02;

import java.awt.Point;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import day02.IntCodeComputerState.ExecutionCode;
import day02.instructions.IntCodeInstruction;
import day02.instructions.IntCodeInstruction.ParamMode;
import day02.instructions.IntCodeInstructionResult;
import day02.instructions.IntCodeInstructionUtils;
import exceptions.InvalidIntCodeException;

public class IntCodeComputer {
    private IntCodeInstructionProvider instructionProvider;
    private List<Integer> memory;
    private int curInstructionIdx;
    private ExecutionCode executionCode;

    // TODO: Make this a type class which takes a number type and uses it for the computations (it must take instructions of the same type!)
    public IntCodeComputer(IntCodeComputerState initialState, IntCodeInstructionProvider instructionProvider) {
        this.memory = initialState.getMemory();
        this.curInstructionIdx = initialState.getCurInstructionIdx();
        this.executionCode = initialState.getExecutionCode();
        this.instructionProvider = instructionProvider;
    }

    public IntCodeComputerState run() throws InvalidIntCodeException, IOException {
        return this.run(new InputStreamReader(System.in), new OutputStreamWriter(System.out));
    }

    public IntCodeComputerState run(Reader inputReader, Writer outputWriter)
            throws InvalidIntCodeException, IOException {
        Scanner inputScanner = new Scanner(inputReader);
        int curInstructionCode;
        int curInstructionOpCode;
        IntCodeInstructionResult curInstructionResult;

        do {
            curInstructionCode = getInstructionCode(curInstructionIdx);
            curInstructionOpCode = getInstructionOpCode(curInstructionIdx);
            IntCodeInstruction curInstruction = this.instructionProvider.getInstructionByOpCode(curInstructionOpCode);
            List<Integer> curInstructionInputIndices = IntCodeComputerUtils
                    .findInstructionParamIndices(curInstructionIdx, curInstruction);
            // TODO: switch this to map to access modes more easily
            List<ParamMode> curInstructionParamModesInOrder = IntCodeInstructionUtils
                    .generateParameterModesInOrder(curInstructionCode, curInstruction);

            curInstructionResult = curInstruction.apply(this.memory, curInstructionInputIndices,
                    curInstructionParamModesInOrder, inputScanner, outputWriter);

            this.resetExecutionCode(curInstructionResult.executionCode);
            if (curInstructionResult.idxToNewValue.isPresent())
                this.updateMemory(curInstructionResult.idxToNewValue.get());

            curInstructionIdx = IntCodeComputerUtils.calcNextInstructionIndex(curInstructionIdx, curInstructionResult,
                    curInstruction);
        } while (this.shouldContinue(curInstructionResult));

        return this.getCurrentState();
    }

    private Integer updateMemory(Point idxToNewValue) {
        return this.memory.set(idxToNewValue.x, idxToNewValue.y);

    }

    private boolean shouldContinue(IntCodeInstructionResult instructionResult) {
        return instructionResult.executionCode.equals(ExecutionCode.READY_FOR_NEXT);
    }

    public IntCodeComputerState resetState(IntCodeComputerState newState) {
        return new IntCodeComputerState(this.resetMemory(newState.getMemory()),
                this.resetCurInstructionIdx(newState.getCurInstructionIdx()),
                this.resetExecutionCode(newState.getExecutionCode()));
    }

    private List<Integer> resetMemory(List<Integer> newMemory) {
        List<Integer> oldMemory = this.getMemory();
        this.memory = new ArrayList<>(newMemory);
        return oldMemory;
    }

    private int resetCurInstructionIdx(int newCurInstructionIdx) {
        int oldCurInstructionIdx = this.getCurInstructionIdx();
        this.curInstructionIdx = newCurInstructionIdx;
        return oldCurInstructionIdx;
    }

    private ExecutionCode resetExecutionCode(ExecutionCode newExecutionCode) {
        ExecutionCode oldExecutionCode = this.getExecutionCode();
        this.executionCode = newExecutionCode;
        return oldExecutionCode;
    }

    public IntCodeComputerState getCurrentState() {
        return new IntCodeComputerState(this.getMemory(), this.getCurInstructionIdx(), this.getExecutionCode());
    }

    private List<Integer> getMemory() {
        return new ArrayList<>(this.memory);
    }

    private int getCurInstructionIdx() {
        return this.curInstructionIdx;
    }

    private ExecutionCode getExecutionCode() {
        return this.executionCode;
    }

    private int getInstructionCode(int instructionIdx) {
        return this.memory.get(instructionIdx);
    }

    private int getInstructionOpCode(int instructionIdx) {
        return this.getInstructionCode(instructionIdx) % 100;
    }

}
