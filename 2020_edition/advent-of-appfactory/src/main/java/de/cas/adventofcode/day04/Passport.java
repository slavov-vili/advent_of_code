package de.cas.adventofcode.day04;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Passport {
    private static final String BIRTH_YEAR = "byr";
    public static final String ISSUE_YEAR = "iyr";
    public static final String EXPIRATION_YEAR = "eyr";
    public static final String HEIGHT = "hgt";
    public static final String HAIR_COLOR = "hcl";
    public static final String EYE_COLOR = "ecl";
    public static final String PASSPORT_ID = "pid";
    private static final Set<String> REQUIRED_KEYS = Set.of(BIRTH_YEAR, ISSUE_YEAR, EXPIRATION_YEAR, HEIGHT, HAIR_COLOR, EYE_COLOR, PASSPORT_ID);
    public static final String HEIGHT_REGEX = "(\\d+)(in|cm)";
    public static final String HAIR_COLOR_REGEX = "#[0-9a-f]{6}";
    public static final String EYE_COLOR_REGEX = "(amb|blu|brn|gry|grn|hzl|oth)";
    public static final String PASSPORT_ID_REGEX = "\\d{9}";
    public static final String INCH = "in";
    public static final String CENTIMETERS = "cm";

    private final Map<String, String> properties = new HashMap<>();

    public void addProperty(final String key, final String value) {
        this.properties.put(key, value);
    }

    public boolean areRequiredKeysPresent() {
        return this.properties.keySet().containsAll(REQUIRED_KEYS);
    }

    public boolean areValuesValid() {
        return this.areRequiredKeysPresent() &&
                isValidBirthYear() &&
                isValidIssueYear() &&
                isValidExpirationYear() &&
                isValidHeight() &&
                isValidHairColor() &&
                isValidEyeColor() &&
                isValidPassportId();
    }

    private boolean isValidBirthYear() {
        final int birthYear = Integer.parseInt(this.properties.get(BIRTH_YEAR));
        return birthYear >= 1920 && birthYear <= 2002;
    }

    private boolean isValidIssueYear() {
        final int issueYear = Integer.parseInt(this.properties.get(ISSUE_YEAR));
        return issueYear >= 2010 && issueYear <= 2020;
    }

    private boolean isValidExpirationYear() {
        final int expirationYear = Integer.parseInt(this.properties.get(EXPIRATION_YEAR));
        return expirationYear >= 2020 && expirationYear <= 2030;
    }

    private boolean isValidHeight() {
        String height = this.properties.get(HEIGHT);
        final Pattern pattern = Pattern.compile(HEIGHT_REGEX);
        final Matcher matcher = pattern.matcher(height);

        if (!matcher.find()) {
            return false;
        }

        final int heightValue = Integer.parseInt(matcher.group(1));
        final String heightUnit = matcher.group(2);

        if (heightUnit.equalsIgnoreCase(INCH)) {
            return heightValue >= 59 && heightValue <= 76;
        } else if (heightUnit.equalsIgnoreCase(CENTIMETERS)) {
            return heightValue >= 150 && heightValue <= 193;
        }

        return false;
    }

    private boolean isValidHairColor() {
        final String hairColor = this.properties.get(HAIR_COLOR);
        return hairColor.matches(HAIR_COLOR_REGEX);
    }

    private boolean isValidEyeColor() {
        final String eyeColor = this.properties.get(EYE_COLOR);
        return eyeColor.matches(EYE_COLOR_REGEX);
    }

    private boolean isValidPassportId() {
        final String passportId = this.properties.get(PASSPORT_ID);
        return passportId.matches(PASSPORT_ID_REGEX);
    }
}
