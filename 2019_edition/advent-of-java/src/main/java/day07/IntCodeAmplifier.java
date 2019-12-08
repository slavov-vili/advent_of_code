package day07;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Optional;

import day02.IntCodeComputer;
import day02.IntCodeComputerState;
import exceptions.InvalidIntCodeException;

public class IntCodeAmplifier {

    private int phaseSetting;
    private IntCodeComputer computer;
    private Optional<Integer> output;

    public IntCodeAmplifier(int phaseSetting, IntCodeComputer computer) {
        this.phaseSetting = phaseSetting;
        this.computer = computer;
        this.output = Optional.empty();
    }

    public Optional<Integer> amplifySignal(int inputSignal) {
        // TODO: close these things
        Reader inputReader = new StringReader(String.valueOf(inputSignal));
        StringWriter outputWriter = new StringWriter();
        try {
            this.computer.run(inputReader, outputWriter);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        String computerOutput = outputWriter.toString().trim();
        this.output = (computerOutput.isEmpty()) ? Optional.empty() : Optional.of(Integer.parseInt(computerOutput));
        return this.getOutput();
    }

    public IntCodeComputerState resetComputerState(IntCodeComputerState newState) {
        return this.computer.resetState(newState);
    }

    public int getPhaseSetting() {
        return this.phaseSetting;
    }
    
    public Optional<Integer> getOutput() {
        return this.output;
    }
}
