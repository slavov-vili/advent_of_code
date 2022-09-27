package utils;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class Cuboid {

	private ThreeDPoint from;
	private ThreeDPoint to;

	public Cuboid(ThreeDPoint from, ThreeDPoint to) {
		this.setFrom(from);
		this.setTo(to);
	}

	public long calcSize() {
		return (long) IntegerUtils.calcDistance(this.getFromX(), this.getToX() + 1)
				* (long) IntegerUtils.calcDistance(this.getFromY(), this.getToY() + 1)
				* (long) IntegerUtils.calcDistance(this.getFromZ(), this.getToZ() + 1);
	}

	public Collection<Cuboid> calcAdditionsFor(Cuboid other) {
		Optional<Cuboid> intersection = this.findIntersectionWith(other);
		if (intersection.isEmpty())
			return Arrays.asList(other);
		else
			return other.split(intersection.get());
	}

	public Optional<Cuboid> findIntersectionWith(Cuboid other) {
		Optional<Point> intersectX = findCoordIntersect(other, Cuboid::getFromX, Cuboid::getToX);
		Optional<Point> intersectY = findCoordIntersect(other, Cuboid::getFromY, Cuboid::getToY);
		Optional<Point> intersectZ = findCoordIntersect(other, Cuboid::getFromZ, Cuboid::getToZ);

		if (intersectX.isPresent() && intersectY.isPresent() && intersectZ.isPresent()) {
			ThreeDPoint intersectFrom = new ThreeDPoint(intersectX.get().x, intersectY.get().x, intersectZ.get().x);
			ThreeDPoint intersectTo = new ThreeDPoint(intersectX.get().y, intersectY.get().y, intersectZ.get().y);
			return Optional.of(new Cuboid(intersectFrom, intersectTo));
		} else
			return Optional.empty();
	}

	public List<Cuboid> split(Cuboid innerCuboid) {
		List<Cuboid> parts = new ArrayList<>();
		if (!this.contains(innerCuboid)) {
			parts.add(this);
		} else {
			boolean existsBefore = this.getFromX() != innerCuboid.getFromX();
			if (existsBefore)
				parts.add(new Cuboid(this.getFrom(),
						new ThreeDPoint(innerCuboid.getFromX() - 1, this.getToY(), this.getToZ())));
			boolean existsAfter = innerCuboid.getToX() != this.getToX();
			if (existsAfter)
				parts.add(new Cuboid(new ThreeDPoint(innerCuboid.getToX() + 1, this.getFromY(), this.getFromZ()),
						this.getTo()));

			boolean existsBelow = this.getFromY() != innerCuboid.getFromY();
			if (existsBelow)
				parts.add(new Cuboid(new ThreeDPoint(innerCuboid.getFromX(), this.getFromY(), this.getFromZ()),
						new ThreeDPoint(innerCuboid.getToX(), innerCuboid.getFromY() - 1, this.getToZ())));
			boolean existsAbove = innerCuboid.getToY() != this.getToY();
			if (existsAbove)
				parts.add(new Cuboid(new ThreeDPoint(innerCuboid.getFromX(), innerCuboid.getToY() + 1, this.getFromZ()),
						new ThreeDPoint(innerCuboid.getToX(), this.getToY(), this.getToZ())));

			boolean existsInFront = this.getFromZ() != innerCuboid.getFromZ();
			if (existsInFront)
				parts.add(new Cuboid(new ThreeDPoint(innerCuboid.getFromX(), innerCuboid.getFromY(), this.getFromZ()),
						new ThreeDPoint(innerCuboid.getToX(), innerCuboid.getToY(), innerCuboid.getFromZ() - 1)));
			boolean existsBehind = innerCuboid.getToZ() != this.getToZ();
			if (existsBehind)
				parts.add(new Cuboid(
						new ThreeDPoint(innerCuboid.getFromX(), innerCuboid.getFromY(), innerCuboid.getToZ() + 1),
						new ThreeDPoint(innerCuboid.getToX(), innerCuboid.getToY(), this.getToZ())));
		}
		return parts;
	}

	public boolean contains(Cuboid other) {
		boolean xIsWithin = IntegerUtils.integerIsWithinRange(other.getFromX(), this.getFromX(), this.getToX())
				&& IntegerUtils.integerIsWithinRange(other.getToX(), this.getFromX(), this.getToX());
		boolean yIsWithin = IntegerUtils.integerIsWithinRange(other.getFromY(), this.getFromY(), this.getToY())
				&& IntegerUtils.integerIsWithinRange(other.getToY(), this.getFromY(), this.getToY());
		boolean zIsWithin = IntegerUtils.integerIsWithinRange(other.getFromZ(), this.getFromZ(), this.getToZ())
				&& IntegerUtils.integerIsWithinRange(other.getToZ(), this.getFromZ(), this.getToZ());
		return xIsWithin && yIsWithin && zIsWithin;
	}

	private Optional<Point> findCoordIntersect(Cuboid other, Function<Cuboid, Integer> coordGetterFrom,
			Function<Cuboid, Integer> coordGetterTo) {
		int fromX = Math.max(coordGetterFrom.apply(this), coordGetterFrom.apply(other));
		int toX = Math.min(coordGetterTo.apply(this), coordGetterTo.apply(other));

		if ((fromX > toX) || (toX < fromX))
			return Optional.empty();
		else
			return Optional.of(new Point(fromX, toX));
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Cuboid))
			return false;
		Cuboid objCuboid = (Cuboid) obj;
		return (this.getFrom().equals(objCuboid.getFrom())) && (this.getTo().equals(objCuboid.getTo()));
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getFrom(), this.getTo());
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Cuboid{");
		builder.append(String.format("from=%s,", this.getFrom().toString()));
		builder.append(String.format("to=%s}", this.getTo().toString()));
		return builder.toString();
	}

	public ThreeDPoint getFrom() {
		return this.from;
	}

	public void setFrom(ThreeDPoint from) {
		this.from = from;
	}

	public ThreeDPoint getTo() {
		return this.to;
	}

	public void setTo(ThreeDPoint to) {
		this.to = to;
	}

	public int getFromX() {
		return this.from.x;
	}

	public void setFromX(int x) {
		this.from.x = x;
	}

	public int getFromY() {
		return this.from.y;
	}

	public void setFromY(int y) {
		this.from.y = y;
	}

	public int getFromZ() {
		return this.from.z;
	}

	public void setFromZ(int z) {
		this.from.z = z;
	}

	public int getToX() {
		return this.to.x;
	}

	public void setToX(int x) {
		this.to.x = x;
	}

	public int getToY() {
		return this.to.y;
	}

	public void setToY(int y) {
		this.to.y = y;
	}

	public int getToZ() {
		return this.to.z;
	}

	public void setToZ(int z) {
		this.to.z = z;
	}
}
