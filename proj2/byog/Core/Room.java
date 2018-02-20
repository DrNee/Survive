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
    public boolean intersect(ArrayList<Room> rooms, int t) {
        for (Room r: rooms) {
            if (c1.x < r.c2.x + t && c2.x + t > r.c1.x && c1.y < r.c3.y + t && c3.y + t > r.c1.y) {
                return true;
            }
        }
        return false;
    }

    // Calculates center of room, can be used to find distance between two rooms
    public Tuple calcCenter() {
        return new Tuple((c1.x + c2.x) / 2, (c3.y + c1.y) / 2);
    }

    // Calculates distance between a single room
    public double calcDist(Room r1) {
        Tuple sqC = r1.calcCenter();
        Tuple sqR = this.calcCenter();
        return Math.sqrt(Math.abs(
                (sqC.x - sqR.x) * (sqC.x - sqR.x) + (sqC.y - sqR.y) * (sqC.y - sqR.y)));
    }

    // Calculates distance between all rooms, returns nearest room index
    // After calculating nearest, get rid of the selection to not have a million hallways
    public int calcNearest(ArrayList<Room> rooms, ArrayList<Room> copy, int temp) {
        int temp2 = temp + 1;
        if (temp >= rooms.size() - 1) {
            temp2 = temp - 2;
        }
        double nearest = calcDist(copy.get(temp2));
        int index = 0;
        for (int i = 0; i < copy.size(); i++) {
            if (temp == i) {
                continue;
            }
            if (nearest >= calcDist(copy.get(i))) {
                nearest = calcDist(copy.get(i));
                index = i;
            }
        }
        return index;
//        int index = 0;
//        Room nearest = rooms.get(0);
//        for (int i = 0; i < rooms.size(); i++) {
//            if (!rooms.get(i).entrance) {
//                nearest = rooms.get(i);
//                break;
//            }
//        }
//        for (int i = 0; i < rooms.size(); i++) {
//            if (this.calcDist(nearest) <= this.calcDist(rooms.get(i)) && !rooms.get(i).entrance) {
//                nearest = rooms.get(i);
//                index = i;
//            }
//        }
//        return index;

//        Room nearest = rooms.get(0);
//        for (int i = 1; i < rooms.size(); i++) {
//            if (nearest.calcDist(rooms.get(i)) <= i)
//        }
//
//        Room nearest = rooms.get(0);
//        double min = nearest.calcDist(rooms.get(1));
//        System.out.println(min);
//        int index = 0;
//        for (int i = 2; i < rooms.size(); i++) {
//            System.out.println(nearest.calcDist(rooms.get(i)));
//            if (nearest.calcDist(rooms.get(i)) <= min) {
//                min = nearest.calcDist(rooms.get(i));
//                index = i;
//            }
//        }
//        System.out.println(index);
//        return index;
//
//        int index = 0;
//        Room nearest = rooms.get(0);
//        for (int i = 1; i < rooms.size(); i++) {
//            System.out.println(nearest.calcDist(rooms.get(i)));
//            System.out.println(this.calcDist(nearest));
//            if (nearest.calcDist(rooms.get(i)) <= this.calcDist(nearest)) {
//
//                nearest = rooms.get(i);
//                index = i;
//            }
//        }
//        return index;
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

    public String toString() {
        return "Room " + Integer.toHexString(hashCode()) + ": " + c1.toString()
                + ", " + c2.toString() + ", " + c3.toString() + ", " + c4.toString();
    }
}
