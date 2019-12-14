package utils;

import java.util.Objects;

public class ThreeDPoint {

    public int x;
    public int y;
    public int z;
    
    public ThreeDPoint(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public ThreeDPoint(ThreeDPoint otherPoint) {
        this.x = otherPoint.x;
        this.y = otherPoint.y;
        this.z = otherPoint.z;
    }

    public ThreeDPoint translate(ThreeDPoint otherPoint) {
        return new ThreeDPoint(this.x + otherPoint.x, this.y + otherPoint.y, this.z + otherPoint.z);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ThreeDPoint))
            return false;
        ThreeDPoint objPoint = (ThreeDPoint) obj;
        return (this.x == objPoint.x) && (this.y == objPoint.y) && (this.z == objPoint.z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y, this.z);
    }

    @Override
    public String toString() {
        return "ThreeDPoint(" + this.x + ", " + this.y + ", " + this.z + ")";
    }
}
