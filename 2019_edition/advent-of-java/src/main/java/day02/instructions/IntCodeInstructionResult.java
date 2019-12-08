package day02.instructions;

import java.awt.Point;
import java.util.Optional;

import day02.IntCodeComputerState.ExecutionCode;

public class IntCodeInstructionResult {
    public Optional<Point> idxToNewValue;
    public Optional<Integer> nextInstructionIndex;
    public ExecutionCode executionCode;

    public IntCodeInstructionResult(Optional<Point> idxToNewValue, Optional<Integer> nextInstructionIndex,
            ExecutionCode executionCode) {
        this.idxToNewValue = idxToNewValue;
        this.nextInstructionIndex = nextInstructionIndex;
        this.executionCode = executionCode;
    }
}
