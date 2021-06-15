package day05.A;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import day02.IntCodeComputer;
import day02.IntCodeInstructionProvider;
import day02.instructions.IntCodeInstruction;
import day05.IntCodeInstructionParameterModeHandler;
import day05.instructions.IntCodeInstructionWriting;
import exceptions.InvalidIntCodeException;

// An IntCodeComputer with support for:
//  - Instruction Parameter Modes
//  - Input Instructions
//  - Output Instructions
public class IntCodeComputer5A extends IntCodeComputer {

	private IntCodeInstructionParameterModeHandler<IntCodeComputer5A> modeHandler;
	protected Scanner inputScanner;
	protected Writer outputWriter;

	public IntCodeComputer5A(List<Integer> initialMemory, IntCodeInstructionProvider instructionProvider,
			IntCodeInstructionParameterModeHandler<IntCodeComputer5A> modeHandler) {
		super(initialMemory, instructionProvider);
		this.modeHandler = modeHandler;
		this.inputScanner = new Scanner(System.in);
		this.outputWriter = new OutputStreamWriter(System.out);
	}
	
	public void run(Reader inputReader, Writer outputWriter) throws InvalidIntCodeException {
		this.inputScanner = new Scanner(inputReader);
		this.outputWriter = outputWriter;
		
		super.run();
	}
	
	@Override
    public IntCodeInstruction getCurInstruction() throws InvalidIntCodeException {
    	int instructionCode = this.getCurInstructionCode();
    	IntCodeInstruction curInstruction = this.getInstructionProvider()
			.getInstructionByOpCode(instructionCode % 100);
    	// The provider only knows the opcode, but we need to preserve the Mode info
    	curInstruction.setCode(instructionCode);
    	return curInstruction;
    }

	@Override
	public void handleCurrentInstruction(IntCodeInstruction curInstruction, List<Integer> curInstructionParameters) {
		List<Integer> parameterModes = calcParameterModes(curInstruction);
		List<Integer> parameterValues = calcParameterValues(curInstruction,
				curInstructionParameters, parameterModes);
		curInstruction.apply(this, parameterValues);
	}
	
	public String getInput() {
        String input = this.inputScanner.next();
        return input;
    }
	
	public void writeOutput(String outputValue) throws IOException {
		this.outputWriter.append(outputValue + "\n");
        this.outputWriter.flush();
	}

	private List<Integer> calcParameterModes(IntCodeInstruction instruction) {
		int paramCount = instruction.getParamCount();
		List<Integer> parameterModes = new ArrayList<>();
		int modes = instruction.getCode() / 100;

		for (int i = 0; i < paramCount; i++) {
			parameterModes.add(modes % 10);
			modes /= 10;
		}

		return parameterModes;
	}

	private List<Integer> calcParameterValues(IntCodeInstruction instruction, List<Integer> parameters,
			List<Integer> parameterModes) {
		List<Integer> parameterValues = new ArrayList<>();
		for (int i=0; i<parameters.size(); i++) {
			Integer paramValue = modeHandler.handleParameter(this,
					parameters.get(i), parameterModes.get(i));
			parameterValues.add(paramValue);
		}
		fixWriteParam(instruction, parameters, parameterValues);
		return parameterValues;
	}

	private void fixWriteParam(IntCodeInstruction instruction, List<Integer> parameters,
			List<Integer> parameterValues) {
		if (instruction instanceof IntCodeInstructionWriting) {
			IntCodeInstructionWriting writeInstruction = (IntCodeInstructionWriting) instruction;
			int writeParamIndex = writeInstruction.getWriteParamIndex();
			parameterValues.set(writeParamIndex, parameters.get(writeParamIndex));
		}
	}
}
