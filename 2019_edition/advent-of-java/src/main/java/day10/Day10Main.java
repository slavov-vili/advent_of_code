package day10;

import java.awt.Point;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import utils.AdventOfCodeUtils;
import utils.PointUtils;

public class Day10Main {

    public static void main(String[] args) {
        Set<Point> asteroids = getAsteroids(getInput());
        System.out.println(asteroids);
        System.out.println(findVisibleAsteroidsOf(new Point(3, 4), asteroids));
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
        Optional<Point> blockerPoint = findBlockerAsteroid(fromAsteroid, goalAsteroid, asteroidMap);
        return (blockerPoint.isPresent()) ? false : true;
    }

    // TODO: IDEA 1 - divide and conquer, recursively find all asteroids between the two asteroids
    // TODO: IDEA 2 - below formula to see if a position exists between the two asteroids
    protected static Optional<Point> findBlockerAsteroid(Point fromAsteroid, Point goalAsteroid, Set<Point> asteroidsMap) {
        int potentialBlockerX = (fromAsteroid.x + goalAsteroid.x) / 2;
        int potentialBlockerY = (fromAsteroid.y + goalAsteroid.y) / 2;
        Point potentialBlocker = new Point(potentialBlockerX, potentialBlockerY);
        
        return (asteroidsMap.contains(potentialBlocker) && (fromAsteroid.distance(potentialBlocker) == goalAsteroid.distance(potentialBlocker))) ?
                Optional.of(potentialBlocker) : Optional.empty();
//        return PointUtils.generatePointsInAreaInclusive(fromAsteroid, goalAsteroid)
//                .stream()
//                .filter(point -> asteroidMap.contains(point))
//                .filter(asteroid -> fromAsteroid.distance(asteroid) == goalAsteroid.distance(asteroid))
//                .findFirst();
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
