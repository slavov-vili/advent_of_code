package day12;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.AdventOfCodeUtils;
import utils.ThreeDPoint;

public class Day12Main {
    
    public static void main(String[] args) {
        Set<ThreeDPoint> moons = getInput();
        Map<ThreeDPoint, ThreeDPoint> moonToVelocity = new HashMap<>();
        for (ThreeDPoint moon : moons)
            moonToVelocity.put(moon, new ThreeDPoint(0, 0, 0));

        DecimalFormat formatter = new DecimalFormat("");
        DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols();
        formatSymbols.setDecimalSeparator(',');
        formatSymbols.setGroupingSeparator('.');
        formatter.setDecimalFormatSymbols(formatSymbols);
        formatter.setGroupingUsed(false);
        try {
            System.out.println(formatter.parse("3.13"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    protected static Set<ThreeDPoint> getInput() {
        List<String> inputLines = AdventOfCodeUtils.readClasspathFileLines(Day12Main.class, "input.txt");
        Set<ThreeDPoint> moons = new HashSet<>();
        for (String line : inputLines)
            moons.add(extractPointFromInputLine(line));
        
        return moons;
    }

    protected static ThreeDPoint extractPointFromInputLine(String inputLine) {
        Matcher matcher = Pattern.compile("pos=<x=(.*?), y=(.*?), z=(.*?)>").matcher(inputLine);
        matcher.find();
        int x = Integer.parseInt(matcher.group(1).trim());
        int y = Integer.parseInt(matcher.group(2).trim());
        int z = Integer.parseInt(matcher.group(3).trim());
        
        return new ThreeDPoint(x, y, z);
    }
}
