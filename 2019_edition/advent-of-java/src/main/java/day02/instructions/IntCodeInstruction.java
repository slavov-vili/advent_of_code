package day02.instructions;

import java.util.List;
import java.util.Optional;

public interface IntCodeInstruction {
	
	// TODO: Returns the index of the next instruction (in case of jumps, etc)
    public Optional<Integer> apply(List<Integer> memory, List<Integer> parameters);

    public int getCode();

    public int getParamCount();
}
