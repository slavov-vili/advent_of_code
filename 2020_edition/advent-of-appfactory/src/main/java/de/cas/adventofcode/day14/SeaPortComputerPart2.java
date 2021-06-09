package de.cas.adventofcode.day14;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class SeaPortComputerPart2 extends AbstractSeaPortComputer {
    private String currentMask;
    private long currentMaskForOr = 0;

    @Override
    protected void handleMask(String mask) {
        this.currentMask = mask;
        currentMaskForOr = Long.parseLong(mask.replaceAll("X", "0"), 2);
    }

    @Override
    protected void handleMem(Map<Long, Long> memoryMap, long address, long value) {
        final List<Integer> xIndices = this.getXIndices(this.currentMask);
        final List<String> permutations = this.getPermutations(xIndices.size(), 0);

        for (final String permutation : permutations) {
            final char[] chars = StringUtils.leftPad(Long.toBinaryString(address), 36, "0").toCharArray();
            for (int i = 0; i < permutation.length(); i++) {
                final int xIndex = xIndices.get(i);
                chars[xIndex] = permutation.charAt(i);
            }
            final long maskedAddress = Long.parseLong(String.valueOf(chars), 2) | currentMaskForOr;
            memoryMap.put(maskedAddress, value);
        }
    }

    private List<Integer> getXIndices(final String maskStr) {
        final List<Integer> indices = new ArrayList<>();
        int i = -1;

        do {
            i = maskStr.indexOf('X', i + 1);

            if (i != -1) {
                indices.add(i);
            }
        } while (i != -1);

        return indices;
    }

    private List<String> getPermutations(final int length, final int i) {
        final List<String> permutations = new ArrayList<>();

        if (i >= length) {
            return Collections.singletonList("");
        }

        for (final String permutation : this.getPermutations(length, i + 1)) {
            permutations.add(permutation + "0");
            permutations.add(permutation + "1");
        }

        return permutations;
    }
}
