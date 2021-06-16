package day02.instructions;

import java.util.List;

import day02.IntCodeComputer;

public interface IntCodeInstruction {
	
    public void apply(IntCodeComputer computer, List<Long> parameters);

    public Long getCode();

    public int getParamCount();
    
    public Long setCode(Long newCode);

    public int setParamCount(int newParamCount);
}
