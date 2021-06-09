package de.cas.adventofcode.day17;

public class IntQuadruple {
	private int x;
	private int y;
	private int z;
	private int w;

	@Override
	public int hashCode() {
		int result = x;
		result = 31 * result + y;
		result = 31 * result + z;
		result = 31 * result + w;
		return result;
	}

	public IntQuadruple(int x, int y, int z, int w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		IntQuadruple quadruple = (IntQuadruple) o;

		if (x != quadruple.x)
			return false;
		if (y != quadruple.y)
			return false;
		if (z != quadruple.z)
			return false;
		return w == quadruple.w;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}
}
