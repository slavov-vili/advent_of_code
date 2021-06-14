package day02.instructions;

import java.util.List;

import day02.IntCodeComputer;

public interface IntCodeInstruction {
	
    public void apply(IntCodeComputer computer, List<Integer> parameters);

    public int getCode();

    public int getParamCount();
    
    public int setCode(int newCode);

    public int setParamCount(int newParamCount);
}
