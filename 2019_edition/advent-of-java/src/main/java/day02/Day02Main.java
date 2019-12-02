package day02;

import java.util.Arrays;
import java.util.List;

import utils.AdventOfCodeUtils;

public class Day02Main {
    
    public static void main(String[] args) {
	String intCodesString = AdventOfCodeUtils.readClasspathFileLines(Day02Main.class, "input.txt").get(0);
	List<Integer> intCodesListInt = AdventOfCodeUtils.parseAllStringsToInt(Arrays.asList(intCodesString.split(",")));
	
	// Part A
	intCodesListInt.set(1, 12);
	intCodesListInt.set(2, 2);
	
	
	IntCodeComputer computerA = new IntCodeComputer(intCodesListInt, 4);
	    
	try {
	    computerA.processInput();
	    System.out.println(computerA.getValueAt(0));
	} catch (Exception e) {
	    e.printStackTrace();
	    System.exit(1);
	}
    }
}
