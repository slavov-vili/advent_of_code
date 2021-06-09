package de.cas.adventofcode.day15;

import java.util.Optional;

public class LastEncounters {
    private int lastEncounter = -1;
    private int secondToLastEncounter = -1;

    public static LastEncounters create(int firstIndex) {
        LastEncounters lastEncounters = new LastEncounters();
        lastEncounters.encounter(firstIndex);
        return lastEncounters;
    }

    public void encounter(int index) {
        secondToLastEncounter = lastEncounter;
        lastEncounter = index;
    }

    public Optional<Integer> calculateAge() {
        if (lastEncounter == -1) {
            return Optional.empty();
        } else if (secondToLastEncounter == -1) {
            return Optional.of(0);
        } else {
            return Optional.of(lastEncounter - secondToLastEncounter);
        }
    }
}
