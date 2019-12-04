package day02;

import java.util.HashMap;
import java.util.Map;

import day02.instructions.IntCodeInstruction;
import exceptions.InvalidArgumentException;
import exceptions.InvalidIntCodeException;

public class IntCodeInstructionProvider {

    private Map<Integer, IntCodeInstruction> instructionCodeToInstruction;

    public IntCodeInstructionProvider() {
        this.instructionCodeToInstruction = new HashMap();
    }

    public IntCodeInstruction getInstructionByCode(Integer instructionCode) throws InvalidIntCodeException {
        if (!this.instructionCodeToInstruction.containsKey(instructionCode))
            throw new InvalidIntCodeException("Instruction code " + instructionCode + " is unknown!");

        return this.instructionCodeToInstruction.get(instructionCode);
    }

    public IntCodeInstruction addNewInstruction(IntCodeInstruction instruction) throws InvalidArgumentException {
        int instructionCode = instruction.getCode();
        if (this.instructionCodeToInstruction.containsKey(instructionCode))
            throw new InvalidArgumentException("Instruction code " + instructionCode + " already exists!");

        return this.instructionCodeToInstruction.put(instructionCode, instruction);
    }
}