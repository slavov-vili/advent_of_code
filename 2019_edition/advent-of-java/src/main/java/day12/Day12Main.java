package day12;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.AdventOfCodeUtils;
import utils.ListUtils;
import utils.PointUtils;
import utils.ThreeDPoint;

public class Day12Main {

    public static void main(String[] args) {
        Set<ThreeDPoint> moons = getInput();
        Map<ThreeDPoint, ThreeDPoint> moonToVelocity = new HashMap<>();
        for (ThreeDPoint moon : moons)
            moonToVelocity.put(moon, new ThreeDPoint(0, 0, 0));

        int solutionA = solveA(1000, moonToVelocity);
        System.out.println("Solution A: " + solutionA);
    }

    protected static int solveA(int timeStepCount, Map<ThreeDPoint, ThreeDPoint> moonToVelocityInitial) {
        Map<ThreeDPoint, ThreeDPoint> moonToVelocityNew = new HashMap<>(moonToVelocityInitial);
        for (int i = 0; i < timeStepCount; i++)
            moonToVelocityNew = performTimeStep(moonToVelocityNew);
        return calcTotalEnergy(moonToVelocityNew);
    }

    protected static Map<ThreeDPoint, ThreeDPoint> performTimeStep(Map<ThreeDPoint, ThreeDPoint> moonToVelocityOld) {
        Map<ThreeDPoint, ThreeDPoint> moonToVelocityNew = new HashMap<>();
        for (Map.Entry<ThreeDPoint, ThreeDPoint> curMoonToVelocity : moonToVelocityOld.entrySet()) {
            ThreeDPoint newVelocity = calcVelocity(curMoonToVelocity.getKey(), moonToVelocityOld);
            ThreeDPoint newPosition = curMoonToVelocity.getKey().translate(newVelocity);
            moonToVelocityNew.put(newPosition, newVelocity);
        }
        return moonToVelocityNew;
    }

    protected static int calcTotalEnergy(Map<ThreeDPoint, ThreeDPoint> moonToVelocity) {
        return moonToVelocity.keySet().stream().mapToInt(moon -> calcTotalEnergyOfMoon(moon, moonToVelocity.get(moon)))
                .sum();
    }

    protected static int calcTotalEnergyOfMoon(ThreeDPoint moon, ThreeDPoint velocity) {
        int potentialEnergy = PointUtils.calcAbsoluteSum(moon);
        int kineticEnergy = PointUtils.calcAbsoluteSum(velocity);
        return potentialEnergy * kineticEnergy;
    }

    protected static ThreeDPoint calcVelocity(ThreeDPoint curMoon, Map<ThreeDPoint, ThreeDPoint> moonToVelocity) {
        int countBiggerX = ListUtils.countWhere(moonToVelocity.keySet(), (moon -> moon.x > curMoon.x));
        int countLowerX = ListUtils.countWhere(moonToVelocity.keySet(), (moon -> moon.x < curMoon.x));
        int countBiggerY = ListUtils.countWhere(moonToVelocity.keySet(), (moon -> moon.y > curMoon.y));
        int countLowerY = ListUtils.countWhere(moonToVelocity.keySet(), (moon -> moon.y < curMoon.y));
        int countBiggerZ = ListUtils.countWhere(moonToVelocity.keySet(), (moon -> moon.z > curMoon.z));
        int countLowerZ = ListUtils.countWhere(moonToVelocity.keySet(), (moon -> moon.z < curMoon.z));

        return new ThreeDPoint(moonToVelocity.get(curMoon).x + countBiggerX - countLowerX,
                moonToVelocity.get(curMoon).y + countBiggerY - countLowerY,
                moonToVelocity.get(curMoon).z + countBiggerZ - countLowerZ);
    }

    protected static Set<ThreeDPoint> getInput() {
        List<String> inputLines = AdventOfCodeUtils.readClasspathFileLines(Day12Main.class, "input.txt");
        Set<ThreeDPoint> moons = new HashSet<>();
        for (String line : inputLines)
            moons.add(extractPointFromInputLine(line));

        return moons;
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
