package day02;

import java.util.HashMap;
import java.util.Map;

import day02.instructions.IntCodeInstruction;
import day02.instructions.IntCodeInstructionHalt;
import exceptions.InvalidArgumentException;
import exceptions.InvalidIntCodeException;

public class IntCodeInstructionProvider {

    private Map<Integer, IntCodeInstruction> instructionCodeMap;
	private Integer haltInstructionOpcode;

    public IntCodeInstructionProvider(IntCodeInstructionHalt haltInstruction) {
        this.instructionCodeMap = new HashMap<>();
        this.haltInstructionOpcode = haltInstruction.getCode();
        this.instructionCodeMap.put(this.haltInstructionOpcode, haltInstruction);
    }

    public IntCodeInstruction getInstructionByOpCode(Integer instructionCode) throws InvalidIntCodeException {
        if (!this.instructionCodeMap.containsKey(instructionCode))
            throw new InvalidIntCodeException("Instruction code " + instructionCode + " is unknown!");

        return this.instructionCodeMap.get(instructionCode);
    }

    public IntCodeInstruction addNewInstruction(IntCodeInstruction newInstruction) throws InvalidArgumentException {
        int instructionCode = newInstruction.getCode();
        if (this.instructionCodeMap.containsKey(instructionCode))
            throw new InvalidArgumentException("Instruction code " + instructionCode + " already exists!");

        return this.instructionCodeMap.put(instructionCode, newInstruction);
    }
    
    public IntCodeInstruction replaceInstruction(IntCodeInstruction newInstruction) {
        int instructionCode = newInstruction.getCode();
        IntCodeInstruction oldInstruction = this.instructionCodeMap.get(instructionCode);
        
        this.instructionCodeMap.put(instructionCode, newInstruction);
        return oldInstruction;
    }
    
    public Integer getHaltInstructionOpCode() {
    	return this.haltInstructionOpcode;
    }
}
