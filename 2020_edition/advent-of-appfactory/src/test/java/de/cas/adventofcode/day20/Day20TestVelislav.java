package de.cas.adventofcode.day20;

import de.cas.adventofcode.shared.Day;
import de.cas.adventofcode.shared.DayRealTest;
import de.cas.adventofcode.shared.FileReader;
import java.util.HashMap;
import java.util.Map;

class Day20TestVelislav extends DayRealTest<Long> {
    private final Day<Long> day20 = new Day20Velislav();

    @Override
    public Day<Long> getDay() {
        return this.day20;
    }

    @Override
    public Map<String, Long> getExpectedResults1Personal() {
        Map<String, Long> nameToExpectedResult = new HashMap<>();

        nameToExpectedResult.put(FileReader.HENNING, null);
        nameToExpectedResult.put(FileReader.OSWALDO, null);
        nameToExpectedResult.put(FileReader.VELISLAV, 66020135789767L);

        return nameToExpectedResult;
    }

    @Override
    public Map<String, Long> getExpectedResults2Personal() {
        Map<String, Long> nameToExpectedResult = new HashMap<>();

        nameToExpectedResult.put(FileReader.HENNING, null);
        nameToExpectedResult.put(FileReader.OSWALDO, null);
        nameToExpectedResult.put(FileReader.VELISLAV, 1537L);

        return nameToExpectedResult;
    }
}
