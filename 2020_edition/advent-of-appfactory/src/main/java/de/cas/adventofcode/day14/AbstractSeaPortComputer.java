package de.cas.adventofcode.day14;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractSeaPortComputer {

    public Long run(final List<String> input) {
        final Pattern maskPattern = Pattern.compile("mask = ([0-9X]+)");
        final Pattern memPattern = Pattern.compile("mem\\[(\\d+)\\] = (\\d+)");
        final Map<Long, Long> memoryMap = new HashMap<>();

        for (final String line : input) {
            final Matcher maskMatcher = maskPattern.matcher(line);
            final Matcher memMatcher = memPattern.matcher(line);

            if (maskMatcher.find()) {
                final String mask = maskMatcher.group(1);
                handleMask(mask);
            } else if (memMatcher.find()) {
                final long address = Long.parseLong(memMatcher.group(1));
                final long value = Long.parseLong(memMatcher.group(2));
                handleMem(memoryMap, address, value);
            }
        }

        return memoryMap.values().stream().reduce(Long::sum).orElseThrow(() -> new RuntimeException("Cannot find result."));
    }

    protected abstract void handleMask(String mask);

    protected abstract void handleMem(Map<Long, Long> memoryMap, long address, long value);
}
