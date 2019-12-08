package day02;

import java.util.ArrayList;
import java.util.List;

public class IntCodeComputerState {

    public enum ExecutionCode {
        READY_FOR_NEXT, REQUIRE_INPUT, HALT
    }

    private List<Integer> memory;
    private int curInstructionIdx;
    private ExecutionCode executionCode;

    public IntCodeComputerState(List<Integer> memory, int curInstructionIdx, ExecutionCode executionCode) {
        this.memory = memory;
        this.curInstructionIdx = curInstructionIdx;
        this.executionCode = executionCode;
    }

    public List<Integer> getMemory() {
        return new ArrayList<>(this.memory);
    }

    public int getCurInstructionIdx() {
        return this.curInstructionIdx;
    }

    public ExecutionCode getExecutionCode() {
        return this.executionCode;
    }

}
