package day07;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Optional;

import day02.IntCodeComputerState;
import exceptions.InvalidIntCodeException;

public class IntCodeAmplifier {

    private int phaseSetting;
    private IntCodeComputer7 computer;
    private Optional<Integer> output;

    public IntCodeAmplifier(int phaseSetting, IntCodeComputer7 computer) {
        this.phaseSetting = phaseSetting;
        this.computer = computer;
        this.output = Optional.empty();
    }

    public Optional<Integer> amplify(String programInput) throws InvalidIntCodeException {
    	// TODO: close these things
        Reader inputReader = new StringReader(programInput);
        StringWriter outputWriter = new StringWriter();
        
        this.computer.run(inputReader, outputWriter);

        String computerOutput = outputWriter.toString().trim();
        this.output = (computerOutput.isEmpty()) ? Optional.empty() : Optional.of(Integer.parseInt(computerOutput));
        return this.getOutput();
    }

    public IntCodeComputerState resetComputerState(IntCodeComputerState newState) {
        return this.computer.resetState(newState);
    }

    public IntCodeComputerState getComputerState() {
        return this.computer.getCurrentState();
    }
    
    public boolean computerIsHalted() {
    	return this.computer.isHalted();
    }

    public int getPhaseSetting() {
        return this.phaseSetting;
    }

    public Optional<Integer> getOutput() {
        return this.output;
    }
}
