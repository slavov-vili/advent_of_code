package de.cas.adventofcode.day16;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TicketRule {
    private String name;
    private int firstRangeMin;
    private int firstRangeMax;
    private int secondRangeMin;
    private int secondRangeMax;

    public static TicketRule fromLine(String line) {
        Pattern pattern = Pattern.compile("([a-z ]+): (\\d+)\\-(\\d+) or (\\d+)\\-(\\d+)");
        Matcher matcher = pattern.matcher(line);
        matcher.find();
        return new TicketRule(
                matcher.group(1),
                Integer.parseInt(matcher.group(2)),
                Integer.parseInt(matcher.group(3)),
                Integer.parseInt(matcher.group(4)),
                Integer.parseInt(matcher.group(5)));
    }

    public TicketRule(String name, int firstRangeMin, int firstRangeMax, int secondRangeMin, int secondRangeMax) {
        this.name = name;
        this.firstRangeMin = firstRangeMin;
        this.firstRangeMax = firstRangeMax;
        this.secondRangeMin = secondRangeMin;
        this.secondRangeMax = secondRangeMax;
    }

    public boolean isValid(int value) {
        return (value >= firstRangeMin && value <= firstRangeMax) || (value >= secondRangeMin && value <= secondRangeMax);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketRule that = (TicketRule) o;
        return firstRangeMin == that.firstRangeMin && firstRangeMax == that.firstRangeMax && secondRangeMin == that.secondRangeMin && secondRangeMax == that.secondRangeMax && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, firstRangeMin, firstRangeMax, secondRangeMin, secondRangeMax);
    }

    public String getName() {
        return name;
    }
}
