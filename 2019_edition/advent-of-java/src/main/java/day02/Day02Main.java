package day02;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import utils.AdventOfCodeUtils;

public class Day02Main {

    public static void main(String[] args) {
        IntCodeComputer computerA = getComputerA();
        try {
           System.out.println("Solution A: " + computerA.processInput(getInputA(), 0).get(0));
       } catch (Exception e) {
           e.printStackTrace();
           System.exit(1);
       }
        
        
   }
    
    protected static IntCodeComputer getComputerA() {
    	IntCodeComputer computerA = new IntCodeComputer(99);
    	try {
    		computerA.addNewInstruction(new IntCodeInstructionAdd(1, 2));
    		computerA.addNewInstruction(new IntCodeInstructionMultiply(2, 2));
    	} catch (Exception e) {
    	}
    	
    	return computerA;
    }

    protected static List<Integer> getInputA() {
        List<Integer> inputCodes = getInput();
        inputCodes.set(1, 12);
        inputCodes.set(2, 2);

        return inputCodes;
    }

    protected static List<Integer> getInput() {
        String intCodesString = AdventOfCodeUtils.readClasspathFileLines(Day02Main.class, "input.txt").get(0);
        return AdventOfCodeUtils.parseAllStringsToInt(Arrays.asList(intCodesString.split(",")));
    }
}
