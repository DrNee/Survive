package byog.Core;

import java.io.Serializable;

// @source, googled this online to get coordinates to work easier
// https://stackoverflow.com/questions/2670982/using-pairs-or-2-tuples-in-java
public class Tuple implements Serializable {
    protected final int x;
    protected final int y;
    public Tuple(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
