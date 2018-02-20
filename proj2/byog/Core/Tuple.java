package byog.Core;

// @source, googled this online to get coordinates to work easier
// https://stackoverflow.com/questions/2670982/using-pairs-or-2-tuples-in-java
public class Tuple {
    public final int x;
    public final int y;
    public Tuple(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}