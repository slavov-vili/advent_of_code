package day23;

import java.awt.Point;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import utils.AdventOfCodeUtils;
import utils.PointUtils;

public class Day23Main {

	public static final Set<Point> ROOM_FRONTS = Set.of(new Point(1, 3), new Point(1, 5), new Point(1, 7),
			new Point(1, 9));
	public static final Set<Point> ROOMS = generateRooms(ROOM_FRONTS);

	public static void main(String[] args) {
		AmphipodDiagram diagram = parseDiagram();

		System.out.println("Min energy to reorder: " + solveA(diagram));
	}

	public static int solveA(AmphipodDiagram diagram) {
		return solveA(diagram, 0, Map.of()).get(diagram);
	}

	public static Map<AmphipodDiagram, Integer> solveA(AmphipodDiagram diagram, int energy,
			Map<AmphipodDiagram, Integer> minEnergyMap) {

		if (minEnergyMap.containsKey(diagram))
			return minEnergyMap;

		Map<AmphipodDiagram, Integer> newMinEnergyMap = new HashMap<>(minEnergyMap);
		List<Amphipod> podsToMove = diagram.pods.stream().filter(pod -> pod.needToMove(diagram.pods)).toList();
		if (podsToMove.isEmpty()) {
			newMinEnergyMap.put(diagram, 0);
			return newMinEnergyMap;
		}

		int curMinEnergy = newMinEnergyMap.getOrDefault(diagram, Integer.MAX_VALUE);
		System.out.println("Cur state:");
		diagram.print();
		System.out.println("State ID: " + diagram.hashCode());
		System.out.println("MinEnergy = " + curMinEnergy);
		System.out.println();
		for (Amphipod pod : podsToMove) {
			Set<Point> nextPositions = pod.findNextPositions(diagram);
			for (Point nextPos : nextPositions) {
				// TODO: remove and add instead of re-creating
				var nextFreePositions = new HashSet<>(diagram.freePositions);
				nextFreePositions.remove(nextPos);
				nextFreePositions.add(pod.position);
				var nextPods = new HashSet<>(diagram.pods);
				nextPods.remove(pod);
				nextPods.add(pod.move(nextPos));

				var nextDiagram = new AmphipodDiagram(nextPods, nextFreePositions);
				newMinEnergyMap = solveA(nextDiagram, energy, newMinEnergyMap);

				int nextEnergy = newMinEnergyMap.get(nextDiagram);

				// FIXME: dead ends relative to current movement + maybe add to map?
				// dead end
				if (nextEnergy == Integer.MAX_VALUE) {
//					System.out.println("Dead end state:");
//					nextDiagram.print();
//					System.out.printf("Moving %s to %s\n", pod, nextPos);
//					System.out.println("MinEnergy = " + nextEnergy);
//					System.out.println();
					continue;
				}

				int newMinEnergy = nextEnergy + pod.calcEnergyTo(nextPos);

				if (newMinEnergy < curMinEnergy) {
					curMinEnergy = newMinEnergy;
//					System.out.println("Cur state:");
//					diagram.print();
//					System.out.println("MinEnergy = " + curMinEnergy);
//					System.out.println();
				}

//				System.out.println("Cur state:");
//				diagram.print();
//				System.out.printf("Moving %s to %s\n", pod, nextPos);
//				System.out.println("MinEnergy = " + newMinEnergy);
//				System.out.println();
//
//				System.out.println("Next state:");
//				nextDiagram.print();
//				System.out.println("MinEnergy = " + newMinEnergyMap.get(nextDiagram));
//				System.out.println();

			}
		}

		// FIXME: when do you put stuff?
		newMinEnergyMap.put(diagram, curMinEnergy);
		return newMinEnergyMap;
	}

	public record AmphipodDiagram(Set<Amphipod> pods, Set<Point> freePositions) {

		public AmphipodDiagram(Set<Amphipod> pods, Set<Point> freePositions) {
			this.pods = pods;
			this.freePositions = freePositions;
		}

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

		// FIXME: if can enter my room - no need to return hallways
		public Set<Point> findNextPositions(AmphipodDiagram diagram) {
			if (!this.needToMove(diagram.pods))
				return Set.of();

			var nextPositions = PointUtils.getPositionsInPaths(this.position, diagram::isFree);

			Predicate<Point> positionValidator = pos -> this.isMyRoom(pos) && this.canEnterRoom(pos, diagram.pods);
			Optional<Point> myRoom = nextPositions.stream().filter(positionValidator).findFirst();
			if (myRoom.isPresent())
				return Set.of(myRoom.get());

			if (isRoom(this.position))
				positionValidator = pos -> isHallway(pos) && !ROOM_FRONTS.contains(pos);
			return nextPositions.stream().filter(positionValidator).collect(Collectors.toSet());
		}

		public boolean needToMove(Collection<Amphipod> others) {
			if (isRoomTop(this.position))
				return !(this.isInMyRoom() && this.isWithRoommate(others));
			else
				return !this.isInMyRoom();
		}

		public boolean isWithRoommate(Collection<Amphipod> others) {
			return wouldBeWithRoommate(this.position, others);
		}

		// neither can be at the entrance => must be both in the room
		public boolean wouldBeWithRoommate(Point newPos, Collection<Amphipod> others) {
			var roommate = others.stream().filter(other -> other.id == this.id && !this.equals(other)).findFirst()
					.get();
			return roommate.position.y == newPos.y && Math.abs(newPos.x - roommate.position.x) == 1;
		}

		public boolean canEnterRoom(Point pos, Collection<Amphipod> others) {
			if (isRoomTop(pos))
				return wouldBeWithRoommate(pos, others);
			else
				return true;
		}

		public boolean isInMyRoom() {
			return this.isMyRoom(this.position);
		}

		public boolean isMyRoom(Point pos) {
			return ROOMS.contains(pos) && pos.y == 2 * (this.id - 0x41 + 1) + 1;
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
		return (int) Math.abs(a.y - b.y) + calcDistToHallway(a) + calcDistToHallway(b);
	}

	public static int calcDistToHallway(Point p) {
		return p.x - 1;
	}

	public static boolean isRoom(Point pos) {
		return ROOMS.contains(pos);
	}

	public static boolean isHallway(Point pos) {
		return !isRoom(pos);
	}

	public static boolean isRoomTop(Point pos) {
		return isRoom(pos) && ROOM_FRONTS.contains(new Point(pos.x - 1, pos.y));
	}

	public static Set<Point> generateRooms(Set<Point> roomFronts) {
		return roomFronts.stream()
				.flatMap(front -> Set.of(new Point(front.x + 1, front.y), new Point(front.x + 2, front.y)).stream())
				.collect(Collectors.toSet());
	}

	public static AmphipodDiagram parseDiagram() {
		List<String> input = AdventOfCodeUtils.readInput(Day23Main.class);
		Set<Amphipod> pods = new HashSet<>();
		Set<Point> freePositions = new HashSet<>();
		for (int row = 0; row < input.size(); row++) {
			for (int col = 0; col < input.get(row).length(); col++) {
				char curChar = input.get(row).charAt(col);
				Point curPos = new Point(row, col);
				if (curChar == '.')
					freePositions.add(curPos);
				else if (Character.isLetter(curChar))
					pods.add(new Amphipod(curChar, curPos));
			}
		}

		return new AmphipodDiagram(pods, freePositions);
	}
}
