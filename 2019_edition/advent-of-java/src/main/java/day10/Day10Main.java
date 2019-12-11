package day10;

import java.awt.Point;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import utils.AdventOfCodeUtils;
import utils.PointUtils;

public class Day10Main {

    public static void main(String[] args) {
        Set<Point> asteroids = getAsteroids(getInput());
        int solutionA = solveA(asteroids);
        System.out.println("Solution A: " + solutionA);
    }

    protected static int solveA(Set<Point> asteroidsMap) {
        Point bestAsteroid = asteroidsMap.stream()
                .max(Comparator.comparingInt(asteroid -> findVisibleAsteroidsOf(asteroid, asteroidsMap).size())).get();
        return findVisibleAsteroidsOf(bestAsteroid, asteroidsMap).size();
    }

    protected static Set<Point> findVisibleAsteroidsOf(Point curAsteroid, Set<Point> asteroidsMap) {
        return asteroidsMap.stream().filter(nextAsteroid -> isVisibleFrom(curAsteroid, nextAsteroid, asteroidsMap))
                .collect(Collectors.toSet());
    }

    protected static boolean isVisibleFrom(Point fromAsteroid, Point goalAsteroid, Set<Point> asteroidMap) {
        Set<Point> blockerAsteroids = findBlockerAsteroids(fromAsteroid, goalAsteroid, asteroidMap);
        return (blockerAsteroids.isEmpty()) ? true : false;
    }

    protected static Set<Point> findBlockerAsteroids(Point fromAsteroid, Point goalAsteroid, Set<Point> asteroidsMap) {
        return PointUtils.generatePointsInAreaInclusive(fromAsteroid, goalAsteroid).stream()
                .filter(point -> asteroidsMap.contains(point))
                .filter(asteroidInArea -> PointUtils.calcSlopeOf(fromAsteroid, asteroidInArea)
                        .equals(PointUtils.calcSlopeOf(asteroidInArea, goalAsteroid)))
                .collect(Collectors.toSet());
    }

    protected static Set<Point> getAsteroids(List<String> asteroidsMapRows) {
        Set<Point> asteroids = new HashSet<>();
        for (int i = 0; i < asteroidsMapRows.size(); i++) {
            String[] charsInRow = asteroidsMapRows.get(i).split("");
            for (int y = 0; y < charsInRow.length; y++)
                if (charsInRow[y].charAt(0) == '#')
                    asteroids.add(new Point(y, i));
        }
        return asteroids;
    }

    protected static List<String> getInput() {
        return AdventOfCodeUtils.readClasspathFileLines(Day10Main.class, "input.txt");
    }
}
