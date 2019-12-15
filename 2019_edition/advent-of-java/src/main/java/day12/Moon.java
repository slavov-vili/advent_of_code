package day12;

import java.awt.Point;
import java.util.List;
import java.util.Objects;

import utils.ListUtils;
import utils.PointUtils;
import utils.ThreeDPoint;

public class Moon {

    private ThreeDPoint position;
    private ThreeDPoint velocity;

    public Moon(ThreeDPoint position, ThreeDPoint velocity) {
        this.setPosition(position);
        this.setVelocity(velocity);
    }
    
    public int calcTotalEnergy() {
        int potentialEnergy = PointUtils.calcAbsoluteSum(this.getPosition());
        int kineticEnergy = PointUtils.calcAbsoluteSum(this.getVelocity());
        return potentialEnergy * kineticEnergy;
    }
    
    public ThreeDPoint calcNewVelocity(List<Moon> moons) {
        int countBiggerPosX = ListUtils.countWhere(moons, (moon -> moon.getPosition().x > this.getPosition().x));
        int countLowerPosX = ListUtils.countWhere(moons,  (moon -> moon.getPosition().x < this.getPosition().x));
        int countBiggerPosY = ListUtils.countWhere(moons, (moon -> moon.getPosition().y > this.getPosition().y));
        int countLowerPosY = ListUtils.countWhere(moons,  (moon -> moon.getPosition().y < this.getPosition().y));
        int countBiggerPosZ = ListUtils.countWhere(moons, (moon -> moon.getPosition().z > this.getPosition().z));
        int countLowerPosZ = ListUtils.countWhere(moons,  (moon -> moon.getPosition().z < this.getPosition().z));

        return new ThreeDPoint(this.getVelocity().x + countBiggerPosX - countLowerPosX,
                this.getVelocity().y + countBiggerPosY - countLowerPosY,
                this.getVelocity().z + countBiggerPosZ - countLowerPosZ);
    }
    
    public ThreeDPoint calcNewPosition(ThreeDPoint newVelocity) {
        return this.getPosition().translate(newVelocity);
    }
    
    public static Point getStateX(Moon moon) {
        return new Point(moon.getPosition().x, moon.getVelocity().x);
    }
    
    public static Point getStateY(Moon moon) {
        return new Point(moon.getPosition().y, moon.getVelocity().y);
    }
    
    public static Point getStateZ(Moon moon) {
        return new Point(moon.getPosition().z, moon.getVelocity().z);
    }

    public ThreeDPoint getPosition() {
        return position;
    }

    public ThreeDPoint getVelocity() {
        return velocity;
    }

    public ThreeDPoint setPosition(ThreeDPoint position) {
        this.position = position;
        return this.getPosition();
    }

    public ThreeDPoint setVelocity(ThreeDPoint velocity) {
        this.velocity = velocity;
        return this.getVelocity();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Moon))
            return false;
        Moon objMoon = (Moon) obj;
        return this.getPosition().equals(objMoon.getPosition()) &&
                this.getVelocity().equals(objMoon.getVelocity());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.getPosition(), this.getVelocity());
    }
    
    @Override
    public String toString() {
        return String.format("Moon{pos=%s; vel=%s}", this.getPosition(), this.getVelocity());
    }
}
