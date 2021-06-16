package day02.instructions;

public abstract class IntCodeInstructionAbstract implements IntCodeInstruction {

    private Long opCode;
    private int paramCount;

    public IntCodeInstructionAbstract(Long opCode, int paramCount) {
        this.opCode = opCode;
        this.paramCount = paramCount;
    }

    @Override
    public Long getCode() {
        return this.opCode;
    }

    @Override
    public int getParamCount() {
        return this.paramCount;
    }

    @Override
    public Long setCode(Long newCode) {
    	Long oldCode = this.opCode;
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
