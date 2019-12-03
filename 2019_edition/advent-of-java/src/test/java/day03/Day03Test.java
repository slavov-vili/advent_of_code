package day03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Point;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class Day03Test {

    @Test
    void calcStepsToPreviousPosition_BasicTest() {
        int expected = 40;
        Wire wireA = new Wire(Day03Main.getOriginPoint());
        Wire wireB = new Wire(Day03Main.getOriginPoint());
        wireA.moveAlongPath(Arrays.asList("R8","U5","L5","D3"));
        wireB.moveAlongPath(Arrays.asList("U7","R6","D4","L4"));
        
        Point goal = new Point(3, 3);
        
        int actual = Day03Main.calcCombinedDistance(goal, wireA, wireB);
        
        assertEquals(expected, actual);
    }
}
