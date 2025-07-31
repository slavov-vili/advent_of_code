package day23;

import utils.AdventOfCodeUtils;
import utils.PointUtils;

import java.awt.Point;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day23Main {

    public static final Set<Point> ROOM_FRONTS = Set.of(
            new Point(1, 3), new Point(1, 5),
            new Point(1, 7), new Point(1, 9)
    );

    public static void main(String[] args) {
        AmphipodDiagram diagram = parseDiagram();

        System.out.println("Answer A: " + solveA(diagram));

        System.out.println("Answer B: " + solveB(diagram));
    }

    public static int solveA(AmphipodDiagram diagram) {
        var newDiagram = diagram;
        if (diagram.pods.size() > 8) {
            var newPods = diagram.pods.stream()
                    .filter(pod -> pod.position.x == 2 || pod.position.x == 5)
                    .map(pod -> pod.position.x == 5 ? pod.move(new Point(3, pod.position.y)) : pod)
                    .collect(Collectors.toSet());
            int newMaxX = diagram.maxX - 2;
            newDiagram = new AmphipodDiagram(newPods, diagram.freePositions, newMaxX);
        }

        return solve(newDiagram, 0, Integer.MAX_VALUE, new HashSet<>());
    }

    public static int solveB(AmphipodDiagram diagram) {
        return solve(diagram, 0, Integer.MAX_VALUE, new HashSet<>());
    }

    public static int solve(AmphipodDiagram diagram, int curEnergy, int curMinEnergy, Set<AmphipodDiagram> seen) {
        if (seen.contains(diagram))
            return curMinEnergy;

        List<Amphipod> podsToMove = diagram.pods.stream()
                .filter(pod -> pod.needToMove(diagram)).toList();
        if (podsToMove.isEmpty()) {
            System.out.println("Current final energy: " + curEnergy);
            return curEnergy;
        }

        int nextMinEnergy = curMinEnergy;
        for (Amphipod pod : podsToMove) {
            Set<Point> nextPositions = pod.findNextPositions(diagram);
            for (Point nextPos : nextPositions) {
                var nextEnergy = curEnergy + pod.calcEnergyTo(nextPos);
                if (nextEnergy >= nextMinEnergy)
                    continue;

                var nextFreePositions = new HashSet<>(diagram.freePositions);
                nextFreePositions.remove(nextPos);
                nextFreePositions.add(pod.position);

                var nextPods = new HashSet<>(diagram.pods);
                nextPods.remove(pod);
                nextPods.add(pod.move(nextPos));

                var nextDiagram = new AmphipodDiagram(nextPods, nextFreePositions, diagram.maxX);
                int newMinEnergy = solve(nextDiagram, nextEnergy, nextMinEnergy, seen);

                if (newMinEnergy < nextMinEnergy) {
                    nextMinEnergy = newMinEnergy;
                }
            }
        }

        seen.add(diagram);
        return nextMinEnergy;
    }

    public record AmphipodDiagram(Set<Amphipod> pods, Set<Point> freePositions, int maxX) {

        public boolean isFree(Point pos) {
            return this.freePositions.contains(pos);
        }

        public Optional<Amphipod> getPodAt(Point position) {
            return pods.stream().filter(pod -> pod.position.equals(position)).findFirst();
        }

        public void print() {
            for (int row = 0; row < 5; row++) {
                String line = "";
                for (int col = 0; col < 13; col++) {
                    Point pos = new Point(row, col);
                    char ch = '#';
                    if (this.isFree(pos))
                        ch = '.';
                    else {
                        Optional<Amphipod> pod = this.getPodAt(pos);
                        if (pod.isPresent())
                            ch = pod.get().id;
                    }

                    line += ch;
                }
                System.out.println(line);
            }
        }
    }

    public record Amphipod(char id, Point position) {

        public Amphipod move(Point newPos) {
            return new Amphipod(this.id, newPos);
        }

        public Set<Point> findNextPositions(AmphipodDiagram diagram) {
            if (!this.needToMove(diagram))
                return Set.of();

            var nextPositions = PointUtils.getPositionsInPaths(this.position, diagram::isFree);

            Predicate<Point> positionValidator = pos -> this.isMyRoom(pos) && this.canEnterRoom(pos, diagram);
            Optional<Point> myRoom = nextPositions.stream().filter(positionValidator).findFirst();
            if (myRoom.isPresent())
                return Set.of(myRoom.get());

            if (isRoom(this.position))
                positionValidator = pos -> isHallway(pos) && !ROOM_FRONTS.contains(pos);

            return nextPositions.stream().filter(positionValidator).collect(Collectors.toSet());
        }

        public boolean needToMove(AmphipodDiagram diagram) {
            if (this.isInMyRoom())
                return !this.isAboveRoommates(diagram);

            return true;
        }

        public boolean isAboveRoommates(AmphipodDiagram diagram) {
            return wouldBeAboveRoommates(this.position, diagram);
        }

        public boolean canEnterRoom(Point pos, AmphipodDiagram diagram) {
            if (this.isMyRoom(pos))
                return wouldBeAboveRoommates(pos, diagram);

            return false;
        }

        public boolean wouldBeAboveRoommates(Point newPos, AmphipodDiagram diagram) {
            var belowMap = diagram.pods.stream()
                    .filter(pod -> pod.position.y == newPos.y && pod.position.x > newPos.x)
                    .collect(Collectors.toMap(pod -> pod.position.x, pod -> pod.id));
            return IntStream.rangeClosed(newPos.x + 1, diagram.maxX).allMatch(pos -> belowMap.containsKey(pos) && belowMap.get(pos) == this.id);
        }

        public boolean isInMyRoom() {
            return this.isMyRoom(this.position);
        }

        public boolean isMyRoom(Point pos) {
            return isRoom(pos) && pos.y == 2 * (this.id - 0x41 + 1) + 1;
        }

        public int calcEnergyTo(Point destination) {
            return (int) Math.pow(10, this.id - 0x41) * calcDistance(this.position, destination);
        }

        @Override
        public String toString() {
            return String.format("Amphipod[%c, (%d,%d)]", this.id, this.position.x, this.position.y);
        }

    }

    public static int calcDistance(Point a, Point b) {
        return Math.abs(a.y - b.y) + calcDistToHallway(a) + calcDistToHallway(b);
    }

    public static int calcDistToHallway(Point p) {
        return p.x - 1;
    }

    public static boolean isRoom(Point pos) {
        return pos.x > 1;
    }

    public static boolean isHallway(Point pos) {
        return !isRoom(pos);
    }

    public static AmphipodDiagram parseDiagram() {
        List<String> input = AdventOfCodeUtils.readInput(Day23Main.class);
        Set<Amphipod> pods = new HashSet<>();
        Set<Point> freePositions = new HashSet<>();
        int maxX = 0;
        for (int row = 0; row < input.size(); row++) {
            for (int col = 0; col < input.get(row).length(); col++) {
                char curChar = input.get(row).charAt(col);
                Point curPos = new Point(row, col);
                if (curChar == '.')
                    freePositions.add(curPos);
                else if (Character.isLetter(curChar)) {
                    pods.add(new Amphipod(curChar, curPos));
                    if (curPos.x > maxX)
                        maxX = curPos.x;
                }
            }
        }

        return new AmphipodDiagram(pods, freePositions, maxX);
    }
}
