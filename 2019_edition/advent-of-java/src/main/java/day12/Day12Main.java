package day12;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import utils.AdventOfCodeUtils;
import utils.ThreeDPoint;

public class Day12Main {

    public static void main(String[] args) {
        List<Moon> moons = getMoons();

        int solutionA = solveA(1000, moons);
        System.out.println("Solution A: " + solutionA);
    }

    protected static int solveA(int timeStepCount, List<Moon> moonsInitial) {
        List<Moon> moonsNew = new ArrayList<>(moonsInitial);
        for (int i = 0; i < timeStepCount; i++)
            moonsNew = performTimeStep(moonsNew);
        return calcTotalEnergy(moonsNew);
    }

    protected static List<Moon> performTimeStep(List<Moon> moonsOld) {
        List<Moon> moonsNew = new ArrayList<>();
        for (Moon curMoon : moonsOld) {
            ThreeDPoint newVelocity = curMoon.calcNewVelocity(moonsOld);
            ThreeDPoint newPosition = curMoon.calcNewPosition(newVelocity);
            moonsNew.add(new Moon(newPosition, newVelocity));
        }
        return moonsNew;
    }

    protected static int calcTotalEnergy(List<Moon> moons) {
        return moons.stream()
                .mapToInt(moon -> moon.calcTotalEnergy())
                .sum();
    }

    protected static List<Moon> getMoons() {
        List<String> inputLines = AdventOfCodeUtils.readClasspathFileLines(Day12Main.class, "input.txt");
        return inputLines.stream()
                .map(line -> new Moon(extractPointFromInputLine(line), new ThreeDPoint(0, 0, 0)))
                .collect(Collectors.toList());
    }

    protected static ThreeDPoint extractPointFromInputLine(String inputLine) {
        Matcher matcher = Pattern.compile("<x=(.*?), y=(.*?), z=(.*?)>").matcher(inputLine);
        matcher.find();
        int x = Integer.parseInt(matcher.group(1).trim());
        int y = Integer.parseInt(matcher.group(2).trim());
        int z = Integer.parseInt(matcher.group(3).trim());

        return new ThreeDPoint(x, y, z);
    }
}
