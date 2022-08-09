package day21;

public class DeterministicDie {
	
	private int lastDiceNumber;
	private int rollCount;
	
	public DeterministicDie() {
		this.lastDiceNumber = 0;
		this.rollCount = 0;
	}
	
	public int roll() {
		int roll = 0;
		int nextDiceNumber = this.lastDiceNumber;
		for (int i = 0; i < 3; i++) {
			nextDiceNumber = nextDiceNumber % 100 + 1;
			this.rollCount++;
			roll += nextDiceNumber;
		}
		
		this.lastDiceNumber = nextDiceNumber;
		return roll;
	}
	
	public int getRollCount() {
		return this.rollCount;
	}
}
