package day02.instructions;

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

    @Override
    public int setCode(int newCode) {
    	int oldCode = this.opCode;
    	this.opCode = newCode;
        return oldCode;
    }

    @Override
    public int setParamCount(int newParamCount) {
    	int oldCount = this.paramCount;
    	this.paramCount = newParamCount;
        return oldCount;
    }
}
