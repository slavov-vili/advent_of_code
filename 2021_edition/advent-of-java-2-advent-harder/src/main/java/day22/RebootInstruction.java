package day22;

import java.util.Objects;
import utils.Cuboid;

public class RebootInstruction {

	private boolean on;
	private Cuboid area;

	public RebootInstruction(boolean on, Cuboid area) {
		this.setOn(on);
		this.setArea(area);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof RebootInstruction))
			return false;
		RebootInstruction objInstruction = (RebootInstruction) obj;
		return (this.isOn() && objInstruction.isOn()) && (this.getArea().equals(objInstruction.getArea()));
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.isOn(), this.getArea());
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		String on = (this.isOn()) ? "on" : "off";
		builder.append("RebootInstruction{" + on);
		builder.append(String.format(", %s}", this.getArea().toString()));
		return builder.toString();
	}

	public boolean isOn() {
		return on;
	}

	public void setOn(boolean on) {
		this.on = on;
	}

	public Cuboid getArea() {
		return this.area;
	}

	public void setArea(Cuboid area) {
		this.area = area;
	}

}
