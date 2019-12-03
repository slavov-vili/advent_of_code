package day03;

import java.awt.Point;
import java.io.ObjectInputStream.GetField;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import utils.AdventOfCodeUtils;


public class Wire {

	private Point initialPosition;
    private Point curPosition;
    private Set<Point> positionHistory;

    public Wire(Point initialPosition) {
        this.initialPosition = initialPosition;
        this.curPosition = initialPosition;
        this.positionHistory = new HashSet();
    }

    public Point moveAlongPath(List<String> pathToFollow) {
        for (String step : pathToFollow) {
            Point curGoal = createGoalFromStep(step);
            this.positionHistory.addAll(this.generatePositionsToGoal(curGoal));
            this.setPosition(curGoal);
        }
        
        return this.getPosition();
    }

	private Set<Point> generatePositionsToGoal(Point goal) {
    	Set<Point> pointsInAreaBetweenPositionAndGoal = AdventOfCodeUtils.generatePointsInArea(this.curPosition, goal);
        return new HashSet(pointsInAreaBetweenPositionAndGoal);
    }

    private Point createGoalFromStep(String step) {
        Point goal = new Point(this.curPosition);
        char stepDirection = step.charAt(0);
        int stepValue = Integer.parseInt(step.substring(1));
        
        switch (stepDirection) {
        case 'R': {
            goal.translate(stepValue, 0);
            break;
        }
        case 'U': {
            goal.translate(0, stepValue);
            break;
        }
        case 'L': {
            goal.translate(-stepValue, 0);
            break;
        }
        case 'D': {
            goal.translate(0, -stepValue);
            break;
        }
        }
        
        return goal;
    }

	public Set<Point> getIntersectionsWith(Wire anotherWire) {
		Set<Point> intersections = this.getPositionHistory();
		intersections.retainAll(anotherWire.getPositionHistory());
		intersections.remove(this.getInitialPosition());
		intersections.remove(anotherWire.getInitialPosition());
		return intersections;
	}
    
    private void setPosition(Point curGoal) {
		this.curPosition = new Point(curGoal);
	}
    
    public Point getInitialPosition() {
		return new Point(this.initialPosition);
	}
    
    public Point getPosition() {
		return new Point(this.curPosition);
	}
    
    public Set<Point> getPositionHistory() {
    	return new HashSet(this.positionHistory);
    }

	@Override
	public String toString() {
		return "Wire [curPosition=" + curPosition + ", positionHistory=" + positionHistory + "]";
	}
}
