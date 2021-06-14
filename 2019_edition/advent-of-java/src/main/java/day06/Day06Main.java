package day06;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import utils.AdventOfCodeUtils;

public class Day06Main {

    public static void main(String[] args) {
        Map<String, Set<String>> orbiteeToDirectOrbiters = getInput();
        Map<String, Set<String>> orbiteeToAllOrbiters = mapOrbiteeToAllOrbiters("COM", orbiteeToDirectOrbiters,
                new HashMap<>());

        // Part A
        int mapSize = 0;
        for (Entry<String, Set<String>> curOrbiteeMap : orbiteeToAllOrbiters.entrySet())
            mapSize += curOrbiteeMap.getValue().size();
        System.out.println(mapSize);

        // Part B
        final List<String> meAndSanta = Arrays.asList("YOU", "SAN");
        Entry<String, Set<String>> leastCommonOrbitee = orbiteeToAllOrbiters.entrySet().stream()
                .filter(entry -> entry.getValue().containsAll(meAndSanta))
                .min(Comparator.comparing(entry -> entry.getValue().size())).get();
        int distanceFromSanta = meAndSanta.stream().mapToInt(
                meOrSanta -> getPathToOrbiter(leastCommonOrbitee.getKey(), meOrSanta, orbiteeToDirectOrbiters).size()
                        - 2)
                .sum();
        System.out.println(distanceFromSanta);
    }

    protected static Map<String, Set<String>> mapOrbiteeToAllOrbiters(String orbitee,
            Map<String, Set<String>> orbiteeToDirectOrbiters, Map<String, Set<String>> orbiteeToAllOrbitersOld) {
        Map<String, Set<String>> orbiteeToAllOrbitersNew = new HashMap<>(orbiteeToAllOrbitersOld);
        if (!orbiteeToDirectOrbiters.containsKey(orbitee)) {
            orbiteeToAllOrbitersNew.put(orbitee, new HashSet<>());
            return orbiteeToAllOrbitersNew;
        }

        Set<String> directOrbiters = orbiteeToDirectOrbiters.get(orbitee);
        Set<String> allOrbiters = new HashSet<>(directOrbiters);
        for (String directOrbiter : directOrbiters) {
            if (!orbiteeToAllOrbitersNew.containsKey(directOrbiter))
                orbiteeToAllOrbitersNew = mapOrbiteeToAllOrbiters(directOrbiter, orbiteeToDirectOrbiters,
                        orbiteeToAllOrbitersNew);

            allOrbiters.addAll(orbiteeToAllOrbitersNew.get(directOrbiter));
        }
        orbiteeToAllOrbitersNew.put(orbitee, allOrbiters);

        return orbiteeToAllOrbitersNew;
    }

    protected static LinkedList<String> getPathToOrbiter(String startObject, String endOrbiter,
            Map<String, Set<String>> orbiteeToDirectOrbiters) {
        if (startObject.equals(endOrbiter))
            return new LinkedList<>(Arrays.asList(endOrbiter));

        if (!orbiteeToDirectOrbiters.containsKey(startObject))
            return new LinkedList<>();

        Set<String> directOrbiters = orbiteeToDirectOrbiters.get(startObject);
        LinkedList<String> pathToEndOrbiter = new LinkedList<>();
        for (String directOrbiter : directOrbiters) {
            LinkedList<String> curOrbiterToEndOrbiter = getPathToOrbiter(directOrbiter, endOrbiter,
                    orbiteeToDirectOrbiters);
            if (!curOrbiterToEndOrbiter.isEmpty()) {
                pathToEndOrbiter = curOrbiterToEndOrbiter;
                pathToEndOrbiter.addFirst(directOrbiter);
                break;
            }
        }

        return pathToEndOrbiter;
    }

    protected static Map<String, Set<String>> getInput() {
        Map<String, Set<String>> orbiteeToOrbiters = new HashMap<>();
        List<String> lines = AdventOfCodeUtils.readInputLines(Day06Main.class);
        for (String line : lines) {
            String[] parts = line.split("\\)");
            String orbitee = parts[0];
            String orbiter = parts[1];
            if (orbiteeToOrbiters.containsKey(orbitee))
                orbiteeToOrbiters.get(orbitee).add(orbiter);
            else
                orbiteeToOrbiters.put(orbitee, new HashSet<>(Arrays.asList(orbiter)));
        }
        return orbiteeToOrbiters;
    }
}
