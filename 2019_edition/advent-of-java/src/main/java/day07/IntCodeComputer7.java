package day07;

import java.io.Reader;
import java.io.Writer;
import java.util.List;

import day02.IntCodeInstructionProvider;
import day05.IntCodeInstructionParameterModeHandler;
import day05.B.IntCodeComputer5B;
import exceptions.InvalidIntCodeException;

//An IntCodeComputer5B with support for:
//- Pausing the computer when input is required, but not present
public class IntCodeComputer7 extends IntCodeComputer5B {
	private boolean isWaitingForInput;
	
	public IntCodeComputer7(List<? extends Number> initialMemory, IntCodeInstructionProvider instructionProvider,
			IntCodeInstructionParameterModeHandler modeHandler) {
		super(initialMemory, instructionProvider, modeHandler);
		this.isWaitingForInput = false;
	}
	
	public void run(Reader inputReader, Writer outputWriter) throws InvalidIntCodeException {
		this.setWaitingForInput(false);
		super.run(inputReader, outputWriter);
	}
	
	@Override
	public boolean shouldStop() {
		return super.shouldStop() || this.isWaitingForInput;
	}

	public boolean hasNextInput() {
		return this.inputScanner.hasNext();
	}
	
	public boolean isWaitingForInput() {
		return this.isWaitingForInput;
	}
	
	public void setWaitingForInput(boolean waiting) {
		this.isWaitingForInput = waiting;
		if (waiting)
			System.out.println("Computer paused and is waiting for input");
		else
			System.out.println("Computer is no longer waiting for input");
	}
}
