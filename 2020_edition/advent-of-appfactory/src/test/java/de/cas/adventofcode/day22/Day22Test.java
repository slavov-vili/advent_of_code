package de.cas.adventofcode.day22;

import de.cas.adventofcode.shared.Day;
import de.cas.adventofcode.shared.DayRealTest;
import de.cas.adventofcode.shared.FileReader;

import java.util.HashMap;
import java.util.Map;

class Day22Test extends DayRealTest<Long> {
    private final Day22 day22 = new Day22();

    @Override
    public Day<Long> getDay() {
        return this.day22;
    }

    @Override
    public Map<String, Long> getExpectedResults1Personal() {
        Map<String, Long> nameToExpectedResult = new HashMap<>();

        nameToExpectedResult.put(FileReader.HENNING, 32495L);
        nameToExpectedResult.put(FileReader.OSWALDO, 33421L);
        nameToExpectedResult.put(FileReader.VELISLAV, 30780L);

        return nameToExpectedResult;
    }

    @Override
    public Map<String, Long> getExpectedResults2Personal() {
        Map<String, Long> nameToExpectedResult = new HashMap<>();

        nameToExpectedResult.put(FileReader.HENNING, 32665L);
        nameToExpectedResult.put(FileReader.OSWALDO, 33651L);
        nameToExpectedResult.put(FileReader.VELISLAV, 36621L);

        return nameToExpectedResult;
    }
}
