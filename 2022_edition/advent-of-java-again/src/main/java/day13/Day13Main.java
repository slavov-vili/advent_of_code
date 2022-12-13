package day13;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import utils.AdventOfCodeUtils;

public class Day13Main {
	public static final Character LIST_START = '[';
	public static final Character LIST_END = ']';

	public static void main(String[] args) {
		List<Packet> packets = parsePackets();

		System.out.println("Sum of packet pair indices in the right order: " + solveA(packets));

		System.out.println("Decoder key (product of divider indices after sort): " + solveB(packets));
	}

	public static int solveA(List<Packet> input) {
		int correctPairIndexSum = 0;

		for (int i = 0; i < input.size() - 1; i += 2) {
			var left = input.get(i);
			var right = input.get(i + 1);

			if (left.compareTo(right) < 0)
				correctPairIndexSum += (i / 2) + 1;
		}

		return correctPairIndexSum;
	}

	public static int solveB(List<Packet> input) {
		var packets = new ArrayList<Packet>(input);
		var dividers = List.of(Packet.createDivider(2), Packet.createDivider(6));
		packets.addAll(dividers);
		packets.sort(Packet::compareTo);
		return dividers.stream().mapToInt(div -> packets.indexOf(div) + 1).reduce((x, y) -> x * y).getAsInt();
	}

	public record Packet(List<Object> contents, int length) implements Comparable<Packet> {

		private static int calcLengthOf(Object o) {
			if (o instanceof Integer)
				return o.toString().length();
			else if (o instanceof Packet)
				return ((Packet) o).length;
			else
				return -1;
		}

		@Override
		public int compareTo(Packet other) {
			int i = 0;

			while (true) {
				boolean imOut = i >= this.contents.size();
				boolean otherIsOut = i >= other.contents().size();

				if (imOut && otherIsOut)
					return 0;
				else if (imOut)
					return -1;
				else if (otherIsOut)
					return 1;

				int curComparison = compareContent(this.contents.get(i), other.contents().get(i));
				if (curComparison != 0)
					return curComparison;
				else
					i++;
			}

		}

		private static int compareContent(Object c1, Object c2) {
			boolean c1IsInt = c1 instanceof Integer;
			boolean c2IsInt = c2 instanceof Integer;

			if (c1IsInt && c2IsInt)
				return Integer.compare((int) c1, (int) c2);

			var p1 = (c1IsInt) ? Packet.fromInt((int) c1) : (Packet) c1;
			var p2 = (c2IsInt) ? Packet.fromInt((int) c2) : (Packet) c2;
			return p1.compareTo(p2);
		}

		public static Packet createDivider(int value) {
			var inner = Packet.fromInt(value);
			return new Packet(List.of(inner), 5);
		}

		public static Packet fromInt(int value) {
			return new Packet(List.of(value), 3);
		}
	}

	public static List<Packet> parsePackets() {
		return AdventOfCodeUtils.readInput(Day13Main.class).stream().filter(line -> !line.isBlank())
				.map(Day13Main::parsePacket).collect(Collectors.toList());
	}

	public static Packet parsePacket(String line) {
		var contents = new ArrayList<>();
		int i = 1;
		while (i < line.length()) {
			var curChar = line.charAt(i);

			if (LIST_END.equals(curChar)) {
				break;
			}

			if (Character.isDigit(curChar)) {
				var number = "";
				while (Character.isDigit(curChar)) {
					number += curChar;
					curChar = line.charAt(++i);
				}
				contents.add(Integer.parseInt(number));

			} else if (LIST_START.equals(curChar)) {
				var innerPacket = parsePacket(line.substring(i));
				contents.add(innerPacket);
				i += innerPacket.length();

			} else {
				i++;
			}
		}

		int contentLength = contents.stream().mapToInt(Packet::calcLengthOf).sum();
		int commaCount = (contents.isEmpty()) ? 0 : contents.size() - 1;
		return new Packet(contents, contentLength + commaCount + 2);
	}
}
