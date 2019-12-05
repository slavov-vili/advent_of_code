package day02.instructions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.IntegerUtils;
import utils.ListUtils;

public abstract class IntCodeInstructionAbstract implements IntCodeInstruction {

    private int opCode;
    private int paramCount;

    public IntCodeInstructionAbstract(int opCode, int paramCount) {
        this.opCode = opCode;
        this.paramCount = paramCount;
    }

    @Override
    public int getCode() {
        return this.opCode;
    }

    @Override
    public int getParamCount() {
        return this.paramCount;
    }

}
