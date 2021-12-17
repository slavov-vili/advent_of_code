package day16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import utils.AdventOfCodeUtils;

public class Day16Main {

	public static final char ZERO = '0';
	public static final char ONE = '1';

	public static void main(String[] args) {
		String bits = parseInput();
		System.out.println(bits);

		Packet rootPacket = findNextPacket(bits);
		System.out.println(rootPacket);
		
		System.out.println("Packet version sum: " + sumVersions(rootPacket));
		System.out.println("Packet value: " + calcPackageValue(rootPacket));
	}
	
	public static Packet findNextPacket(String bits) {
		int i = 0;
		int version = Integer.parseInt(bits.substring(i, i + 3), 2);
		i += 3;
		int typeID = Integer.parseInt(bits.substring(i, i + 3), 2);
		i += 3;

		if (typeID == 4) {
			boolean lastGroupReached = false;
			String valueBits = "";
			while (!lastGroupReached) {
				String curGroup = bits.substring(i, i + 5);
				lastGroupReached = curGroup.charAt(0) == ZERO;
				valueBits += curGroup.substring(1);
				i += 5;
			}
			long value = Long.parseLong(valueBits, 2);
			return new Packet(version, typeID, i, value);
		} else {
			List<Packet> children = new ArrayList<>();
			boolean lengthInBits = bits.charAt(i) == ZERO;
			i += 1;

			if (lengthInBits) {
				int totalChildrenLength = Integer.parseInt(bits.substring(i, i + 15), 2);
				i += 15;
				int childrenLength = 0;
				while (childrenLength < totalChildrenLength) {
					Packet nextChild = findNextPacket(bits.substring(i));
					children.add(nextChild);
					childrenLength += nextChild.length;
					i += nextChild.length;
				}

				return new Packet(version, typeID, i, children);
			} else {
				int expectedChildrenCount = Integer.parseInt(bits.substring(i, i + 11), 2);
				i += 11;
				while (children.size() < expectedChildrenCount) {
					Packet nextChild = findNextPacket(bits.substring(i));
					children.add(nextChild);
					i += nextChild.length;
				}

				return new Packet(version, typeID, i, children);
			}
		}
	}
	
	public static long calcPackageValue(Packet packet) {
		if (packet.typeId == 4)
			return packet.value;
		else {
			if (packet.typeId == 0)
				return packet.children.stream()
						.mapToLong(Day16Main::calcPackageValue).sum();
			else if (packet.typeId == 1)
				return packet.children.stream()
						.mapToLong(Day16Main::calcPackageValue).reduce(Math::multiplyExact).getAsLong();
			else if (packet.typeId == 2)
				return packet.children.stream()
						.mapToLong(Day16Main::calcPackageValue).min().getAsLong();
			else if (packet.typeId == 3)
				return packet.children.stream()
						.mapToLong(Day16Main::calcPackageValue).max().getAsLong();
			else {
				long firstChildValue = calcPackageValue(packet.children.get(0));
				long secondChildValue = calcPackageValue(packet.children.get(1));
				long value = 0;
				
				if ((packet.typeId == 5) && (firstChildValue > secondChildValue))
					value = 1;
				else if ((packet.typeId == 6) && (firstChildValue < secondChildValue))
					value = 1;
				else if ((packet.typeId == 7) && (firstChildValue == secondChildValue))
					value = 1;
				
				return value;
			}
		}
	}
	
	public static int sumVersions(Packet rootPacket) {
		int versionSum = rootPacket.version; 
		
		if (!rootPacket.children.isEmpty()) {
		int childrenVersionSum = rootPacket.children.stream()
				.mapToInt(Day16Main::sumVersions).sum();
		versionSum += childrenVersionSum;
		}
		return versionSum;
	}

	public static String parseInput() {
		String hexDigits = AdventOfCodeUtils.readInput(Day16Main.class).get(0);
		return Arrays.stream(hexDigits.split("")).map(hexDigit -> Day16Main.parseHexDigitToBits(hexDigit))
				.reduce((bits1, bits2) -> bits1.concat(bits2)).get();
	}

	public static String parseHexDigitToBits(String hexDigit) {
		return Arrays.stream(padWithZeros(Integer.toBinaryString(Integer.parseInt(hexDigit, 16)), 4).split(""))
				.reduce((bits1, bits2) -> bits1.concat(bits2)).get();
	}

	public static String padWithZeros(String originalString, int expectedLength) {
		int curLength = originalString.length();
		String padding = "";
		if (curLength < expectedLength) {
			for (int i = 0; i < expectedLength - curLength; i++)
				padding += "0";
		}
		return padding + originalString;
	}

	public static class Packet {
		public int version;
		public int typeId;
		public int length;
		public long value;
		public List<Packet> children;

		public Packet(int version, int typeId, int length, long value) {
			this(version, typeId, length);
			this.value = value;
		}

		public Packet(int version, int typeId, int length, List<Packet> children) {
			this(version, typeId, length);
			this.children = new ArrayList<>(children);
		}

		public Packet(int version, int typeId, int length) {
			this.version = version;
			this.typeId = typeId;
			this.length = length;
			this.children = new ArrayList<>();
			this.value = 0;
		}

		public List<Packet> addChild(Packet child) {
			this.children.add(child);
			return this.children;
		}

		public String toString() {
			if (children.isEmpty())
				return String.format("Packet(v=%d, id=%d, val=%d)", this.version, this.typeId, this.value);
			return String.format("Packet(v=%d, id=%d, children=%s)", this.version, this.typeId,
					this.children.toString());
		}
	}
}
