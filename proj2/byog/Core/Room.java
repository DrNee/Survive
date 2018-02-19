package byog.Core;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Room {
    // tuples are in the form (x, y)
    private Tuple c1, c2, c3, c4;

    /**
     * All rooms rectangular right now
     * @param c1 bottom left
     * @param c2 bottom right
     * @param c3 top left
     * @param c4 top right
     */
    public Room(Tuple c1, Tuple c2, Tuple c3, Tuple c4) {
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.c4 = c4;
    }

    // checks if two rectangles intersect with each other
    public boolean intersect(ArrayList<Room> rooms) {
        for (Room r: rooms) {
            if (c1.x < r.c2.x && c2.x > r.c1.x && c1.y < r.c3.y && c3.y > r.c1.y) {
                return true;
            }
        }
        return false;
        // (c3.x >= r.c2.x || r.c3.x >= c2.x) && (c3.y <= r.c2.y || r.c3.y <= c2.y)
        // c1.x <= r.c2.x && c2.x >= r.c2.x && c3.y <= r.c1.y && r.c3.y >= c1.y
    }

    // get method for each corner
    public Tuple get(int corner) {
        Tuple tmp = new Tuple(0, 0);
        switch (corner) {
            case 1: tmp = c1; break;
            case 2: tmp = c2; break;
            case 3: tmp = c3; break;
            case 4: tmp = c4; break;
            default: break;
        }
        return tmp;
    }
}
