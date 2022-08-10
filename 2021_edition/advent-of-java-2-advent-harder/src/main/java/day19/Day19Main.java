package day19;

import java.util.ArrayList;
import java.util.List;
import utils.AdventOfCodeUtils;

public class Day19Main {
	public static String BEGIN_SCANNER = "--- scanner \\d ---";

	public static void main(String[] args) {
		List<Scanner> scanners = parseInput();
		int intersects = scanners.get(0).countIntersects(scanners.get(1));
		System.out.println("Intersects: " + intersects);
	}
	
	public static List<Scanner> parseInput() {
		List<Scanner> scanners = new ArrayList<>();
		List<String> input = AdventOfCodeUtils.readInput(Day19Main.class);
		Scanner curScanner = new Scanner();;
		for (String line : input) {
			if (line.matches(BEGIN_SCANNER))
				curScanner = new Scanner();
			else if (line.isBlank())
				scanners.add(curScanner);
			else
				curScanner.addBeacon(Beacon.fromLine(line));
		}
		
		return scanners;
	}
}
