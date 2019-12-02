package day02;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exceptions.InvalidArgumentException;
import utils.AdventOfCodeUtils;

public class IntCodeComputer {
	private int haltCode;
	private Map<Integer, IntCodeInstruction> instructionCodeToInstruction;

	public IntCodeComputer(int haltCode) {
		this.haltCode = haltCode;
		this.instructionCodeToInstruction = new HashMap<>();
	}

	public List<Integer> processInput(List<Integer> inputCodes, int startIndex) throws InvalidIntCodeException {
		List<Integer> memory = AdventOfCodeUtils.cloneList(inputCodes);

		int curInstructionIdx = startIndex;
		int curInstructionCode = memory.get(curInstructionIdx);

		while (!this.codeIsHaltCode(curInstructionCode)) {
			IntCodeInstruction curInstruction = this.getInstructionByCode(curInstructionCode);
			List<Integer> instructionInput = this.extractInputForInstruction(memory, curInstructionIdx, curInstruction);

			int outputIndex = this.extractOutputIndexForInstruction(memory, curInstructionIdx, curInstruction);
			int outputValue = curInstruction.apply(instructionInput);
			
			memory.set(outputIndex, outputValue);
			curInstructionIdx = this.calcNextInstructionIndex(curInstructionIdx, curInstruction);
			curInstructionCode = memory.get(curInstructionIdx);
		}

		return memory;
	}

	private int calcNextInstructionIndex(int curInstructionIdx, IntCodeInstruction curInstruction) {
		return curInstructionIdx + curInstruction.getInputSize() + 2;
	}

	private int extractOutputIndexForInstruction(List<Integer> memory, int curInstructionIdx, IntCodeInstruction curInstruction) {
		int indexOfOutputIndex = curInstructionIdx + curInstruction.getInputSize() + 1;
		return memory.get(indexOfOutputIndex);
	}

	private List<Integer> extractInputForInstruction(List<Integer> memory, int curInstructionIdx, IntCodeInstruction curInstruction) {
		List<Integer> inputIndices = memory.subList(curInstructionIdx + 1, curInstructionIdx + curInstruction.getInputSize() + 1);
		return AdventOfCodeUtils.getElementsAt(memory, inputIndices);
	}

	public IntCodeInstruction getInstructionByCode(Integer instructionCode) throws InvalidIntCodeException {
		if (!this.instructionCodeToInstruction.containsKey(instructionCode))
			throw new InvalidIntCodeException("Instruction code " + instructionCode + " is unknown!");
		
		return this.instructionCodeToInstruction.get(instructionCode);
	}

	public IntCodeInstruction addNewInstruction(IntCodeInstruction instruction)
			throws InvalidArgumentException {
		int instructionCode = instruction.getCode();
		
		if (instructionCode == this.haltCode)
			throw new InvalidArgumentException("Instruction code " + instructionCode + " cannot be overriden");

		return this.instructionCodeToInstruction.put(instructionCode, instruction);
	}
	
	private boolean codeIsHaltCode(int code) {
		return code == this.haltCode;
	}

}
