package day07;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import day02.IntCodeComputer;

public class IntCodeAmplifier {

    private int phaseSetting;
    private IntCodeComputer computer;
    private List<Integer> computerInput;

    public IntCodeAmplifier(int phaseSetting, IntCodeComputer computer, List<Integer> computerInput) {
        this.phaseSetting = phaseSetting;
        this.computer = computer;
        this.computerInput = computerInput;
    }

    public int amplifySignal(int inputSignal) {
        String instructionInputs = this.phaseSetting + " " + inputSignal;
        // TODO: try with resource and close the reader / writer
        Reader inputReader = new StringReader(instructionInputs);
        StringWriter outputWriter = new StringWriter();
        try {
            this.computer.run(inputReader, outputWriter);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return Integer.parseInt(outputWriter.toString().trim());
    }
}
