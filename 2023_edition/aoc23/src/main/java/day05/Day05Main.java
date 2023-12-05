package day05;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import utils.AdventOfCodeUtils;

public class Day05Main {

	public static final String SEEDS_PREFIX = "seeds: ";

	public static void main(String[] args) {
		Garden garden = parseGarden();
//		garden.printMaps();

		System.out.println("Min location for seeds: " + solveA(garden));

		System.out.println("Min location for ranges: " + solveB(garden));
	}

	public static long solveA(Garden garden) {
		return garden.mapAllSeedRangesToLocations().stream().mapToLong(l -> l).min().getAsLong();
	}

	public static long solveB(Garden garden) {
		return garden.toRanges().calcLocationRanges().stream().min(SeedRange::compareTo).get().start;
	}

	record Garden(List<SeedRange> seedRanges, List<GardenMap> maps) {
		public static Garden fromSeeds(List<Long> seeds, List<GardenMap> maps) {
			return new Garden(seeds.stream().map(s -> new SeedRange(s, s)).toList(), maps);
		}

		public Garden toRanges() {
			List<SeedRange> newSeedRanges = new ArrayList<>();
			for (int i = 0; i < this.seedRanges.size() / 2; i++) {
				long start = this.seedRanges.get(2 * i).start;
				long end = start + this.seedRanges.get(2 * i + 1).end;
				newSeedRanges.add(new SeedRange(start, end));
			}
			return new Garden(newSeedRanges, this.maps);
		}

		public List<Long> mapAllSeedRangesToLocations() {
			return this.seedRanges.stream().flatMap(sr -> this.mapSeedRangeToLocation(sr).stream()).toList();
		}

		public List<Long> mapSeedRangeToLocation(SeedRange range) {
			return range.stream().mapToObj(this::mapSeedToLocation).toList();
		}

		public long mapSeedToLocation(long key) {
			long val = key;
			for (GardenMap map : maps)
				val = map.map(val);
//			System.out.printf("Seed = %d, Value = %d\n", key, val);
			return val;
		}

		public List<SeedRange> calcLocationRanges() {
			List<SeedRange> curRanges = this.seedRanges;
			for (GardenMap map : this.maps)
				curRanges = map.splitSrcRanges(curRanges);
			return curRanges;
		}

		public void printMaps() {
			for (GardenMap map : this.maps) {
				map.printRanges();
				System.out.println();
			}
		}
	}

	record GardenMap(List<MapRange> mapRanges) {
		public GardenMap(List<MapRange> mapRanges) {
			this.mapRanges = mapRanges.stream().sorted().toList();
		}

		public long map(long value) {
			Optional<MapRange> matchedRange = this.mapRanges.stream().dropWhile(mr -> mr.map(value) == value)
					.findFirst();
			return matchedRange.map(range -> range.map(value)).orElse(value);
		}

		public List<SeedRange> splitSrcRanges(List<SeedRange> srcRanges) {
			List<SeedRange> curSrcRanges = srcRanges;
			for (MapRange mr : this.mapRanges)
				curSrcRanges = mr.splitSrcRanges(curSrcRanges);
			return curSrcRanges.stream().map(sr -> sr.map(this.mapRanges)).toList();
		}

		public void printRanges() {
			this.mapRanges.forEach(MapRange::print);
		}
	}

	// inclusive
	record MapRange(SeedRange src, SeedRange dst) implements Comparable<MapRange> {

		public SeedRange map(SeedRange range) {
			if (this.contains(range.start) && this.contains(range.end)) {
				long start = this.map(range.start);
				long end = this.map(range.end);
				return new SeedRange(start, end);
			} else {
				return range;
			}
		}

		public long map(long value) {
			if (this.contains(value))
				return this.dst.start + (value - this.src.start);
			else
				return value;
		}

		public boolean contains(long value) {
			return value >= this.src.start && value < this.src.end;
		}

		public List<SeedRange> splitSrcRanges(List<SeedRange> ranges) {
			return ranges.stream().flatMap(r -> this.splitSrcRange(r).stream()).toList();
		}

		public List<SeedRange> splitSrcRange(SeedRange range) {
			return range.split(new SeedRange(this.src.start, this.src.end));
		}

		@Override
		public int compareTo(MapRange other) {
			return Long.compare(this.src.start, other.src.start);
		}

		public void print() {
			System.out.printf("[%d - %d] -> [%d - %d]\n", this.src.start, this.src.end, this.dst.start, this.dst.end);
		}

		public static MapRange fromString(String str) {
			List<Long> parts = parseNumbers(str);
			long dstStart = parts.get(0);
			long srcStart = parts.get(1);
			long length = parts.get(2);
			return new MapRange(new SeedRange(srcStart, srcStart + length - 1),
					new SeedRange(dstStart, dstStart + length - 1));
		}
	}

	// inclusive
	record SeedRange(long start, long end) implements Comparable<SeedRange> {

		public List<SeedRange> split(SeedRange other) {
			if (!this.overlapsWith(other))
				return List.of(this);

			List<SeedRange> parts = new ArrayList<>();
			long curStart = this.start;
			Set<Long> possibleEnds = List.of(this.end, other.start, other.end).stream().filter(end -> this.start <= end)
					.collect(Collectors.toSet());
			long curEnd = -this.end;
			while (curEnd != this.end) {
				curEnd = possibleEnds.stream().mapToLong(l -> l).min().getAsLong();
				possibleEnds.remove(curEnd);
				parts.add(new SeedRange(curStart, curEnd));
				curStart = curEnd;
			}
			parts.add(new SeedRange(curStart, curEnd));

			return parts;
		}

		public boolean overlapsWith(SeedRange other) {
			return this.start <= other.end && this.end >= other.start;
		}

		public SeedRange map(List<MapRange> mapRanges) {
			return mapRanges.stream().map(mr -> mr.map(this)).filter(newRange -> !newRange.equals(this)).findFirst()
					.orElse(this);
		}

		public LongStream stream() {
			return LongStream.rangeClosed(this.start, this.end);
		}

		@Override
		public int compareTo(SeedRange other) {
			return Long.compare(this.start, other.start);
		}
	}

	public static Garden parseGarden() {
		List<String> lines = AdventOfCodeUtils.readInput(Day05Main.class);
		List<GardenMap> maps = new ArrayList<>();
		List<MapRange> curRanges = new ArrayList<>();
		List<Long> seeds = new ArrayList<>();

		for (String line : lines) {
			if (line.startsWith(SEEDS_PREFIX))
				seeds = parseNumbers(line.substring(SEEDS_PREFIX.length()));
			else if (line.endsWith("map:"))
				curRanges = new ArrayList<>();
			else if (line.isBlank()) {
				if (!curRanges.isEmpty())
					maps.add(new GardenMap(curRanges));
			} else
				curRanges.add(MapRange.fromString(line));
		}
		if (!lines.get(lines.size() - 1).isBlank())
			maps.add(new GardenMap(curRanges));

		return Garden.fromSeeds(seeds, maps);
	}

	public static List<Long> parseNumbers(String str) {
		return Arrays.stream(str.split(" ")).map(Long::parseLong).toList();
	}
}
