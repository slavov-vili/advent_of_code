package de.cas.adventofcode.day14;

import java.util.Map;

public class SeaPortComputerPart1 extends AbstractSeaPortComputer {
    private long currentMaskForAnd = 0;
    private long currentMaskForOr = 0;

    @Override
    protected void handleMask(String mask) {
        currentMaskForAnd = Long.parseLong(mask.replaceAll("X", "1"), 2);
        currentMaskForOr = Long.parseLong(mask.replaceAll("X", "0"), 2);
    }

    @Override
    protected void handleMem(Map<Long, Long> memoryMap, long address, long value) {
        memoryMap.put(address, (value & currentMaskForAnd) | currentMaskForOr);
    }
}
