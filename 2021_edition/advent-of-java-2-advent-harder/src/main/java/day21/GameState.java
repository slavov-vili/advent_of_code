package day21;

import java.util.Objects;

public class GameState {

	private PlayerState statePlayer1;
	private PlayerState statePlayer2;

	public GameState(PlayerState statePlayer1, PlayerState statePlayer2) {
		this.setStatePlayer1(statePlayer1);
		this.setStatePlayer2(statePlayer2);
	}

	public PlayerState getStatePlayer1() {
		return statePlayer1;
	}

	public void setStatePlayer1(PlayerState statePlayer1) {
		this.statePlayer1 = statePlayer1;
	}

	public PlayerState getStatePlayer2() {
		return statePlayer2;
	}

	public void setStatePlayer2(PlayerState statePlayer2) {
		this.statePlayer2 = statePlayer2;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("GameState{");
		builder.append("player1=" + this.getStatePlayer1().toString());
		builder.append(", ");
		builder.append("player2=" + this.getStatePlayer2().toString());
		builder.append("}");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getStatePlayer1(), this.getStatePlayer2());
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof GameState))
			return false;

		GameState otherState = (GameState) other;
		return this.getStatePlayer1().equals(otherState.getStatePlayer1())
				&& this.getStatePlayer2().equals(otherState.getStatePlayer2());
	}

	public static class PlayerState {
		private int position;
		private int score;

		public PlayerState(int position, int score) {
			this.position = position;
			this.score = score;
		}

		public int getPosition() {
			return this.position;
		}

		public PlayerState setPosition(int newPosition) {
			this.position = newPosition;
			return this;
		}

		public int getScore() {
			return this.score;
		}

		public PlayerState setScore(int newScore) {
			this.score = newScore;
			return this;
		}
		
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder("PlayerState{");
			builder.append("position=" + this.getPosition());
			builder.append(", ");
			builder.append("score=" + this.getScore());
			return builder.toString();
		}
		
		@Override
		public PlayerState clone() {
			return new PlayerState(this.getPosition(), this.getScore());
		}

		@Override
		public int hashCode() {
			return Objects.hash(this.position, this.score);
		}

		@Override
		public boolean equals(Object other) {
			if (!(other instanceof PlayerState))
				return false;

			PlayerState otherState = (PlayerState) other;
			return this.position == otherState.getPosition() && this.score == otherState.getScore();
		}
	}
}
