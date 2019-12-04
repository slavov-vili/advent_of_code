package day03;

public class DirectionChars {

    public char charUp;
    public char charRight;
    public char charDown;
    public char charLeft;

    public DirectionChars(char charUp, char charRight, char charDown, char charLeft) {
        this.charUp = charUp;
        this.charRight = charRight;
        this.charDown = charDown;
        this.charLeft = charLeft;
    }

    public DirectionChars clone() {
        return new DirectionChars(this.charUp, this.charRight, this.charDown, this.charLeft);
    }
}
