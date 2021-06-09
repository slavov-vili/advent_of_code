package de.cas.adventofcode.day04;

import com.google.common.base.Strings;
import de.cas.adventofcode.shared.Day;

import java.util.ArrayList;
import java.util.List;

public class Day04 extends Day<Long> {
    protected Day04() {
        super(4);
    }

    public static void main(final String[] args) {
        new Day04().run();
    }

    @Override
    public Long solvePart1(final List<String> input) {
        final List<Passport> passports = this.parsePassports(input);
        return passports.stream().filter(Passport::areRequiredKeysPresent).count();
    }

    @Override
    public Long solvePart2(final List<String> input) {
        final List<Passport> passports = this.parsePassports(input);
        return passports.stream().filter(Passport::areValuesValid).count();
    }

    private List<Passport> parsePassports(final List<String> input) {
        final List<Passport> passports = new ArrayList<>();
        Passport passport = new Passport();

        for (final String line : input) {
            if (Strings.isNullOrEmpty(line)) {
                passports.add(passport);
                passport = new Passport();
            } else {
                final String[] properties = line.split(" ");

                for (final String property : properties) {
                    final String[] keyAndValue = property.split(":");
                    passport.addProperty(keyAndValue[0], keyAndValue[1]);
                }
            }
        }

        passports.add(passport);
        return passports;
    }

}
