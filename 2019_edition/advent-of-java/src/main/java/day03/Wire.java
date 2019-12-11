package day03;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import utils.PointUtils;

public class Wire {

    private Point initialPosition;
    private Point curPosition;
    private List<Point> positionHistory;

    public Wire(Point initialPosition) {
        this.initialPosition = initialPosition;
        this.curPosition = initialPosition;
        this.positionHistory = new ArrayList<>();
        this.positionHistory.add(initialPosition);
    }

    public Point moveAlongPath(List<String> pathToFollow) {
        for (String step : pathToFollow) {
            Point curGoal = createGoalFromStep(step);
            this.positionHistory.addAll(this.generatePositionsToGoal(curGoal));
            this.setPosition(curGoal);
        }

        return this.getPosition();
    }

    public int calcStepsToPreviousPosition(Point previousPosition) throws PositionNotInKnownPathException {
        if (!this.positionHistory.contains(previousPosition))
            throw new PositionNotInKnownPathException("The position " + previousPosition + " is not on any known path");

        return this.positionHistory.indexOf(previousPosition);

    }

    private List<Point> generatePositionsToGoal(Point goal) {
        List<Point> pointsInAreaBetweenPositionAndGoal = PointUtils.generatePointsInAreaInclusive(this.curPosition,
                goal);
        return new ArrayList<>(
                pointsInAreaBetweenPositionAndGoal.subList(1, pointsInAreaBetweenPositionAndGoal.size()));
    }

    // TODO: finish the DirectionChars class and take this stuff out of here
    private Point createGoalFromStep(String step) {
        Point goal = new Point(this.curPosition);
        char stepDirection = step.charAt(0);
        int stepValue = Integer.parseInt(step.substring(1));

        switch (stepDirection) {
        case 'U': {
            goal.translate(0, stepValue);
            break;
        }
        case 'R': {
            goal.translate(stepValue, 0);
            break;
        }
        case 'D': {
            goal.translate(0, -stepValue);
            break;
        }
        case 'L': {
            goal.translate(-stepValue, 0);
            break;
        }
        }

        return goal;
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

    public List<Point> getPositionHistory() {
        return new ArrayList<>(this.positionHistory);
    }

    @Override
    public String toString() {
        return "Wire [curPosition=" + curPosition + ", positionHistory=" + positionHistory + "]";
    }
}
