package day17;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import utils.AdventOfCodeUtils;

public class Day17Main {
    public static Rock HLINE = new Rock(Set.of(new Point(0,0), new Point(1,0), new Point(2,0), new Point(3,0)));
    public static Rock CROSS = new Rock(Set.of(new Point(1,0), new Point(1,1), new Point(1,2), new Point(0,1), new Point(2, 1)));
    public static Rock EDGE = new Rock(Set.of(new Point(0,0), new Point(1,0), new Point(2,0), new Point(2,1), new Point(2,2)));
    public static Rock VLINE = new Rock(Set.of(new Point(0,0), new Point(0,1), new Point(0,2), new Point(0,3)));
    public static Rock SQUARE = new Rock(Set.of(new Point(0,0), new Point(1,0), new Point(0,1), new Point(1,1)));
    public static List<Rock> ROCKS = List.of(HLINE, CROSS, EDGE, VLINE, SQUARE);

    public static void main(String[] args) {
        String line = AdventOfCodeUtils.readInput(Day17Main.class).get(0);
        List<Character> wind = IntStream.range(0, line.length()).mapToObj(line::charAt).toList();

        System.out.println("Max height = " + solveA(wind));
    }

    public static int solveA(List<Character> wind) {
        var fallenRockParts = new HashSet<Point>();
        int maxHeight = 0;
        int windIndex = 0;
        for(int rockIndex=0; rockIndex < 2022; rockIndex++) {
            boolean hasLanded = false;
            var curRock = ROCKS.get(rockIndex % ROCKS.size()).spawn(maxHeight);

            while (!hasLanded) {
                // FIXME: wind depends on tick, not on rock
                char direction = wind.get(windIndex % wind.size());
                curRock = curRock.move(direction, fallenRockParts);

                var nextRock = curRock.move('v', fallenRockParts);
                hasLanded = (curRock.equals(nextRock));
                curRock = nextRock;

                windIndex++;
            }
            fallenRockParts.addAll(curRock.parts);
            maxHeight = curRock.parts.stream().map(p -> p.y).reduce(maxHeight, Integer::max, Integer::max);
            System.out.printf("Rock %d, maxHeight = %d\n", rockIndex, maxHeight);
        }

        return maxHeight;
    }

    public static void solveB() {
    }

    public record Rock(Set<Point> parts) {
        public Rock spawn(int curHeight) {
            var newParts = this.parts.stream()
                    .map(p -> new Point(p.x + 2, p.y + curHeight + 4)).collect(Collectors.toSet());
            return new Rock(newParts);
        }

        public Rock move(char direction, Set<Point> fallenParts) {
            Function<Point, Point> movement = getMovementFunction(direction);
            var newParts = this.parts.stream()
                    .map(movement).collect(Collectors.toSet());
            var canMove = newParts.stream().allMatch(p -> p.x >= 0 && p.x < 7);
            canMove = canMove && newParts.stream().allMatch(p -> p.y >= 1 && !fallenParts.contains(p));

            if (canMove)
                return new Rock(newParts);
            else
                return this;
        }
    }

    public static Function<Point, Point> getMovementFunction(char direction) {
        Function<Point, Point> movement;
        if (direction == '>')
            movement = p -> new Point(p.x + 1, p.y);
        else if (direction == '<')
            movement = p -> new Point(p.x - 1, p.y);
        else if (direction == 'v')
            movement = p -> new Point(p.x, p.y - 1);
        else
            movement = p -> new Point(p.x, p.y);

        return movement;
    }
}
