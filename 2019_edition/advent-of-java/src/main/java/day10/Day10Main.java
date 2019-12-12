package day10;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import utils.AdventOfCodeUtils;
import utils.PointUtils;

public class Day10Main {

    public static void main(String[] args) {
        Set<Point> asteroids = getInput();
        Point bestAsteroid = asteroids.stream()
                .max(Comparator.comparingInt(curAsteroid -> findVisibleAsteroidsOf(curAsteroid, asteroids).size()))
                .get();

        int solutionA = solveA(bestAsteroid, asteroids);
        System.out.println("Solution A: " + solutionA);

        int solutionB = solveB(bestAsteroid, asteroids);
        System.out.println("Solution B: " + solutionB);

    }

    protected static int solveA(Point bestAsteroid, Set<Point> asteroidsMap) {
        return findVisibleAsteroidsOf(bestAsteroid, asteroidsMap).size();
    }

    protected static int solveB(Point bestAsteroid, Set<Point> asteroidsMap) {
        List<Point> killedAsteroids = killAsteroids(bestAsteroid, 200, asteroidsMap);
        Point killedAsteroid200 = killedAsteroids.get(199);
        return (killedAsteroid200.x * 100) + killedAsteroid200.y;
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
                .filter(asteroidInArea -> PointUtils.calcSlopeOfLine(fromAsteroid, asteroidInArea)
                        .equals(PointUtils.calcSlopeOfLine(asteroidInArea, goalAsteroid)))
                .collect(Collectors.toSet());
    }

    private static List<Point> killAsteroids(Point killerAsteroid, int asteroidsToKill, Set<Point> asteroidsMap) {
        List<Point> killedAsteroids = new ArrayList<>();
        Set<Point> curAsteroidsMap = new HashSet<>(asteroidsMap);
        TreeSet<Point> curVisibleAsteroidsInOrder = new TreeSet<>(Comparator.comparing(curAsteroid -> PointUtils.calcAngleOfLine(killerAsteroid, curAsteroid)));
        while (killedAsteroids.size() < asteroidsToKill) {
            Point curAsteroid = curVisibleAsteroidsInOrder.pollFirst();
            if (curAsteroid == null) {
                curVisibleAsteroidsInOrder.addAll(findVisibleAsteroidsOf(killerAsteroid, curAsteroidsMap));
                continue;
            }
            
            killedAsteroids.add(curAsteroid);
            curAsteroidsMap.remove(curAsteroid);
        }
        
        return killedAsteroids;
    }

    protected static Set<Point> getInput() {
        List<String> inputLines = AdventOfCodeUtils.readClasspathFileLines(Day10Main.class, "input.txt");
        Set<Point> asteroids = new HashSet<>();
        for (int i = 0; i < inputLines.size(); i++) {
            String[] charsInRow = inputLines.get(i).split("");
            for (int y = 0; y < charsInRow.length; y++)
                if (charsInRow[y].charAt(0) == '#')
                    asteroids.add(new Point(y, i));
        }
        return asteroids;
    }
}
