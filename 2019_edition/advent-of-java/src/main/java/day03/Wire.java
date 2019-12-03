package day03;

import java.awt.Point;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Wire {

    private Point curPosition;
    private Set positionHistory;

    public Wire() {
        this.curPosition = new Point(0, 0);
        this.positionHistory = new HashSet();
    }

    public Point moveAlongPath(List<String> pathToFollow) {
        for (String step : pathToFollow) {
            // TODO: generate positions based on step
            Point curGoal = createGoalFromStep(step);
            this.positionHistory.addAll(this.generatePositionsToGoal(curGoal));
        }
    }
    
    private Set<Point> generatePositionsToGoal(Point curGoal) {
        Set<Point> positionsToGoal = new HashSet();
        
        for (int x : )
    }

    // TODO: move along step instead
    private Point createGoalFromStep(String step) {
        Point goal = new Point(this.curPosition);
        char stepDirection = step.charAt(0);
        int stepValue = Integer.parseInt(step.substring(1));
        
        switch (stepDirection) {
        case 'R': {
            goal.translate(stepValue, 0);
        }
        case 'U': {
            goal.translate(0, stepValue);
        }
        case 'L': {
            goal.translate(-stepValue, 0);
        }
        case 'D': {
            goal.translate(0, -stepValue);
        }
        }
        
        return goal;
    }
    
    
}
