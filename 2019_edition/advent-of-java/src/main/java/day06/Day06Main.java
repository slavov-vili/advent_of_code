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

import exceptions.NoPathBetweenObjectsException;
import utils.AdventOfCodeUtils;

public class Day06Main {

    public static void main(String[] args) {
        Map<String, Set<String>> orbiteeToDirectOrbiters = getInput();
        Map<String, Set<String>> orbiteeToAllOrbiters = new HashMap<>();
        for (String orbitee : orbiteeToDirectOrbiters.keySet())
            orbiteeToAllOrbiters.put(orbitee, getAllOrbitersOf(orbitee, orbiteeToDirectOrbiters));

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
        int distanceFromSanta = meAndSanta.stream().mapToInt(name -> getPathToOrbiter(leastCommonOrbitee.getKey(), name,
                orbiteeToDirectOrbiters, orbiteeToAllOrbiters).size()).sum();
        System.out.println(distanceFromSanta);
    }

    protected static Set<String> getAllOrbitersOf(String orbitee, Map<String, Set<String>> orbiteeToDirectOrbiters) {
        if (!orbiteeToDirectOrbiters.containsKey(orbitee))
            return new HashSet<>();

        Set<String> directOrbiters = orbiteeToDirectOrbiters.get(orbitee);
        Set<String> allOrbiters = new HashSet<>(directOrbiters);
        for (String orbiter : directOrbiters)
            allOrbiters.addAll(getAllOrbitersOf(orbiter, orbiteeToDirectOrbiters));
        return allOrbiters;
    }

    protected static LinkedList<String> getPathToOrbiter(String startObject, String orbiter,
            Map<String, Set<String>> orbiteeToDirectOrbiters, Map<String, Set<String>> orbiteeToAllOrbiters) {
        if (!orbiteeToDirectOrbiters.containsKey(startObject))
            return new LinkedList<>();

        Set<String> directOrbiters = orbiteeToDirectOrbiters.get(startObject);
        if (directOrbiters.contains(orbiter))
            return new LinkedList<>();

        String nextStepToEndObject = "";
        for (String directOrbiter : directOrbiters)
            if (orbiteeToAllOrbiters.getOrDefault(directOrbiter, new HashSet<>()).contains(orbiter)) {
                nextStepToEndObject = directOrbiter;
                break;
            }

        if (nextStepToEndObject.isEmpty())
            throw new NoPathBetweenObjectsException(orbiter + " cannot be reached by any path from " + startObject);

        LinkedList<String> pathToEndObject = getPathToOrbiter(nextStepToEndObject, orbiter, orbiteeToDirectOrbiters,
                orbiteeToAllOrbiters);
        pathToEndObject.addFirst(nextStepToEndObject);
        return pathToEndObject;
    }

    protected static Map<String, Set<String>> getInput() {
        Map<String, Set<String>> orbiteeToOrbiters = new HashMap<>();
        List<String> lines = AdventOfCodeUtils.readClasspathFileLines(Day06Main.class, "input.txt");
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
