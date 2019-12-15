package day12;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import utils.AdventOfCodeUtils;
import utils.IntegerUtils;
import utils.ThreeDPoint;

public class Day12Main {

    public static void main(String[] args) {
        List<Moon> moons = getMoons();

        int solutionA = solveA(1000, moons);
        System.out.println("Solution A: " + solutionA);

        long solutionB = solveB(moons);
        System.out.println("Solution B: " + solutionB);

    }

    protected static int solveA(int timeStepCount, List<Moon> moonsInitial) {
        List<Moon> moonsCurrent = new ArrayList<>(moonsInitial);
        for (int i = 0; i < timeStepCount; i++)
            moonsCurrent = performTimeStep(moonsCurrent);
        return calcTotalEnergy(moonsCurrent);
    }

    protected static long solveB(List<Moon> moons) {
        return Arrays.<Function<Moon, Point>>asList(Moon::getStateX, Moon::getStateY, Moon::getStateZ).stream()
                .map(axisStateGetter -> countStepsUntilAxisStateRepetition(moons, axisStateGetter))
                .mapToLong(x -> Long.valueOf(x))
                .reduce((a, b) -> IntegerUtils.findLCM(a, b)).getAsLong();
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

    protected static int countStepsUntilAxisStateRepetition(List<Moon> moonsInitial,
            Function<Moon, Point> getAxisState) {
        Set<List<Point>> prevAxisStates = new HashSet<>();
        List<Moon> moonsCurrent = new ArrayList<>(moonsInitial);
        List<Point> curAxisState = getAxisStateFromMoons(moonsCurrent, getAxisState);
        int count = 0;
        do {
            prevAxisStates.add(curAxisState);
            moonsCurrent = performTimeStep(moonsCurrent);
            curAxisState = getAxisStateFromMoons(moonsCurrent, getAxisState);
            count++;
        } while (!prevAxisStates.contains(curAxisState));
        return count;
    }

    protected static List<Point> getAxisStateFromMoons(List<Moon> moons, Function<Moon, Point> getAxisState) {
        List<Point> axisStates = new ArrayList<>();
        for (Moon moon : moons)
            axisStates.add(getAxisState.apply(moon));
        return axisStates;
    }

    protected static int calcTotalEnergy(List<Moon> moons) {
        return moons.stream().mapToInt(moon -> moon.calcTotalEnergy()).sum();
    }

    protected static List<Moon> getMoons() {
        List<String> inputLines = AdventOfCodeUtils.readClasspathFileLines(Day12Main.class, "input.txt");
        return inputLines.stream().map(line -> new Moon(extractPointFromInputLine(line), new ThreeDPoint(0, 0, 0)))
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
