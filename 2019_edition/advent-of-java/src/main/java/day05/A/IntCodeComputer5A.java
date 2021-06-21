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
import day05.IntCodeInstructionParameterEvaluator;
import day05.instructions.IntCodeInstructionWriting;
import exceptions.InvalidIntCodeException;

// An IntCodeComputer with support for:
//  - Instruction Parameter Modes
//  - Input Instructions
//  - Output Instructions
public class IntCodeComputer5A extends IntCodeComputer {
	
	public static final String OUTPUT_SEPARATOR = "\n";

	private IntCodeInstructionParameterEvaluator modeHandler;
	protected Scanner inputScanner;
	protected Writer outputWriter;

	private boolean isDebug;

	public IntCodeComputer5A(List<? extends Number> initialMemory, IntCodeInstructionProvider instructionProvider,
			IntCodeInstructionParameterEvaluator modeHandler) {
		super(initialMemory, instructionProvider);
		this.modeHandler = modeHandler;
		this.inputScanner = new Scanner(System.in);
		this.outputWriter = new OutputStreamWriter(System.out);
		this.isDebug = false;
	}
	
	public void run(Reader inputReader, Writer outputWriter) throws InvalidIntCodeException {
		this.inputScanner = new Scanner(inputReader);
		this.outputWriter = outputWriter;
		
		super.run();
	}
	
	@Override
    public IntCodeInstruction getCurInstruction() throws InvalidIntCodeException {
    	Long instructionCode = this.getCurInstructionCode();
    	IntCodeInstruction curInstruction = this.getInstructionProvider()
			.getInstructionByOpCode(instructionCode % 100);
    	// The provider only knows the opcode, but we need to preserve the Mode info
    	curInstruction.setCode(instructionCode);
    	return curInstruction;
    }

	@Override
	public void handleCurrentInstruction(IntCodeInstruction curInstruction, List<Long> curInstructionParameters) {
		List<Long> parameterModes = calcParameterModes(curInstruction);
		List<Long> parameterValues = calcParameterValues(curInstruction,
				curInstructionParameters, parameterModes);
		curInstruction.apply(this, parameterValues);
	}
	
	public String getInput() {
        String input = this.inputScanner.next();
        return input;
    }
	
	public Long parseNumber(String stringToParse) {
		return Long.parseLong(stringToParse);
	}
	
	public void writeOutput(String outputValue) throws IOException {
		this.outputWriter.append(outputValue + OUTPUT_SEPARATOR);
        this.outputWriter.flush();
	}
	
	public boolean isDebug() {
		return this.isDebug;
	}
	
	public void toggleDebug() {
		this.isDebug = !this.isDebug;
	}
	
	private List<Long> calcParameterModes(IntCodeInstruction instruction) {
		int paramCount = instruction.getParamCount();
		List<Long> parameterModes = new ArrayList<>();
		Long modes = instruction.getCode() / 100;

		for (int i = 0; i < paramCount; i++) {
			parameterModes.add(modes % 10);
			modes /= 10;
		}

		return parameterModes;
	}

	protected List<Long> calcParameterValues(IntCodeInstruction instruction, List<Long> parameters,
			List<Long> parameterModes) {
		List<Long> parameterValues = new ArrayList<>();
		for (int i=0; i<parameters.size(); i++) {
			Long paramValue = this.modeHandler.handleParameter(this, parameters.get(i),
					parameterModes.get(i), isWriteParam(instruction, i));
			parameterValues.add(paramValue);
		}
		return parameterValues;
	}

	private boolean isWriteParam(IntCodeInstruction instruction, int paramIndex) {
		boolean result = false;
		if (instruction instanceof IntCodeInstructionWriting) {
			IntCodeInstructionWriting writeInstruction = (IntCodeInstructionWriting) instruction;
			result = (writeInstruction.getWriteParamIndex() == paramIndex);
		}
		return result;
	}
}
