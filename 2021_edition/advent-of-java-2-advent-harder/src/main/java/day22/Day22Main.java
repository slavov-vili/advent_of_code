package day22;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import utils.AdventOfCodeUtils;
import utils.Cuboid;
import utils.ThreeDPoint;

public class Day22Main {

    public static final Pattern LINE_PATTERN = Pattern
            .compile("(on|off) x=(-?\\d+)..(-?\\d+),y=(-?\\d+)..(-?\\d+),z=(-?\\d+)..(-?\\d+)");

    public static void main(String[] args) {
        List<RebootInstruction> instructions = parseInput();

        System.out.println("On cubes in initialization area: " + solveA(instructions));

        System.out.println("On cubes total: " + solveB(instructions));
    }

    public static int solveA(List<RebootInstruction> instructions) {
        Set<ThreeDPoint> onCubes = new HashSet<>();
        for (RebootInstruction instruction : instructions) {
            if (isWithinInitArea(instruction.getArea())) {
                Set<ThreeDPoint> cubes = getCubes(instruction.getArea());
                if (instruction.isOn())
                    onCubes.addAll(cubes);
                else
                    onCubes.removeAll(cubes);
            }
        }
        return onCubes.size();
    }

    public static long solveB(List<RebootInstruction> instructions) {
        Collection<Cuboid> noOverlapsOnCuboids = new ArrayList<>();
        for (RebootInstruction newInstruction : instructions) {
            if (newInstruction.isOn()) {
                Collection<Cuboid> additions = Arrays.asList(newInstruction.getArea());
                for (Cuboid onCuboid : noOverlapsOnCuboids) {
                    Collection<Cuboid> newAdditions = new ArrayList<>();
                    for (Cuboid addition : additions)
                        newAdditions.addAll(onCuboid.calcAdditionsFor(addition));

                    additions = newAdditions;
                }

                noOverlapsOnCuboids.addAll(additions);
            } else {
                Collection<Cuboid> newOnCuboids = new ArrayList<>();
                for (Cuboid onCuboid : noOverlapsOnCuboids) {
                    Optional<Cuboid> intersection = onCuboid.findIntersectionWith(newInstruction.getArea());
                    if (intersection.isPresent())
                        newOnCuboids.addAll(onCuboid.split(intersection.get()));
                    else
                        newOnCuboids.add(onCuboid);
                }

                noOverlapsOnCuboids = newOnCuboids;
            }
        }

        return noOverlapsOnCuboids.stream().mapToLong(Cuboid::calcSize).sum();
    }

    public static boolean isWithinInitArea(Cuboid area) {
        boolean areXSmaller = area.getFromX() < -50 && area.getToX() < -50;
        boolean areXBigger = area.getFromX() > 50 && area.getToX() > 50;
        boolean isXOutside = areXSmaller || areXBigger;

        boolean areYSmaller = area.getFromY() < -50 && area.getToY() < -50;
        boolean areYBigger = area.getFromY() > 50 && area.getToY() > 50;
        boolean isYOutside = areYSmaller || areYBigger;

        boolean areZSmaller = area.getFromZ() < -50 && area.getToZ() < -50;
        boolean areZBigger = area.getFromZ() > 50 && area.getToZ() > 50;
        boolean isZOutside = areZSmaller || areZBigger;

        return !(isXOutside || isYOutside || isZOutside);
    }

    public static Set<ThreeDPoint> getCubes(Cuboid area) {
        Set<ThreeDPoint> onCubes = new HashSet<>();
        for (int x = area.getFromX(); x <= area.getToX(); x++)
            for (int y = area.getFromY(); y <= area.getToY(); y++)
                for (int z = area.getFromZ(); z <= area.getToZ(); z++)
                    onCubes.add(new ThreeDPoint(x, y, z));
        return onCubes;
    }

    public static List<RebootInstruction> parseInput() {
        return AdventOfCodeUtils.readInput(Day22Main.class).stream().map(Day22Main::parseInstruction)
                .collect(Collectors.toList());
    }

    public static RebootInstruction parseInstruction(String line) {
        Matcher m = LINE_PATTERN.matcher(line);
        m.find();

        boolean on = "on".equals(m.group(1));
        ThreeDPoint from = new ThreeDPoint(Integer.parseInt(m.group(2)), Integer.parseInt(m.group(4)),
                Integer.parseInt(m.group(6)));
        ThreeDPoint to = new ThreeDPoint(Integer.parseInt(m.group(3)), Integer.parseInt(m.group(5)),
                Integer.parseInt(m.group(7)));

        return new RebootInstruction(on, new Cuboid(from, to));
    }

}
