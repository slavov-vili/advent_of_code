package day21;

public class Pair<T1, T2> {
	private T1 value1;
	private T2 value2;
	
	public Pair(T1 value1, T2 value2) {
		this.value1 = value1;
		this.value2 = value2;
	}
	
	public T1 getValue1() {
		return this.value1;
	}
	
	public T2 getValue2() {
		return this.value2;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("Pair(");
		builder.append("value1=" + this.getValue1().toString());
		builder.append(", ");
		builder.append("value2=" + this.getValue2().toString());
		builder.append(")");
		return builder.toString();
	}
}
