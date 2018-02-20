package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.Random;

// TODO: can try to connect rooms by putting a random opening on a room and connecting it
// to another room with the shortest distance (can create method)

// TODO: alternative connecting rooms by finding centers, connecting centers
// TODO: to fix method above // to be able to connect different rooms everytime,
// TODO: combine one room at a time, move onto the next room and find the next closest
// TODO: to that one and connect those two, eventually, we will reach the final

// TODO: another alt, have rooms and just add a hallway randomly because we know
//      placement of rooms, can do random length, min just has to be the distance to the room

// NEW IDEA: WHAT IF WE RUN CELLULAR AUTOMATA FIRST AND  THEN PLACE ROOMS ON TOP
// WILL THIS ALREADY CREATE HALLWAYS?
// TODO: Brute force method, fill every place with room and check if it intersects,
//       fill until desired amount of rooms
//       hallways can be implemented later to connect rooms
public class Map {
    private static final int minsize = 5, maxsize = 12;
    private static final int thresh = 4; //threshold for intersection

    private Random random;
    private int seed, width, height;
    private ArrayList<Room> rooms;
    private TETile[][] world;

    public Map(int seed, int width, int height) {
        this.seed = seed;
        this.width = width;
        this.height = height;
        random = new Random(seed);
        rooms = new ArrayList<>();
        world = new TETile[width][height];
    }

    /**
     * generate a room, have to check the size of the room
     * it has to be connected to hallways
     * we have to leave an opening somewhere and link up the room and hallway
     */
    private Room generateRoom() {
        int rwidth = minsize + random.nextInt(maxsize - minsize + 1);
        int rlength = minsize + random.nextInt(maxsize - minsize + 1);
        int rx = random.nextInt(width);
        int ry = random.nextInt(height);

        if (rx < (width - maxsize) && ry < (height- maxsize)) {
            return new Room(new Tuple(rx, ry), new Tuple(rx + rwidth, ry),
                new Tuple(rx, ry + rlength), new Tuple(rx + rwidth, ry + rlength));
        }
        return generateRoom();
    }

    // adds the room to the map
    // change later makesure work
    private void addToMap(Room room) {
        for (int i = room.get(1).x; i < room.get(2).x; i++) {
            for (int j = room.get(1).y; j < room.get(3).y; j++) {
                world[i][j] = Tileset.FLOOR;
            }
        }
        // adding walls to the room
        for (int i = room.get(1).x; i < room.get(2).x; i++) {
            world[i][room.get(1).y] = Tileset.WALL;
            world[i][room.get(3).y - 1] = Tileset.WALL;
        }
        for (int i = room.get(1).y; i < room.get(3).y; i++) {
            world[room.get(1).x][i] = Tileset.WALL;
            world[room.get(2).x - 1][i] = Tileset.WALL;
        }
    }

    /**
     * Adds rooms to both an arraylist and the map
     * @param numRooms takes in the max amount of rooms per map
     */
    public void addRooms(int numRooms) {
        while (rooms.size() < numRooms && rooms.size() <= maxRooms()) {
            Room room = generateRoom();
            if (rooms.size() == 0 || !room.intersect(rooms, thresh)) {
                rooms.add(room);
                addToMap(room);
            }
        }
    }

    // Used to calculate the maximum amount of rooms
    private int maxRooms() {
        int max = (maxsize + thresh) * (maxsize + thresh);
        int area = width * height;
        return area/max;
    }

    /**
     * generate a hallway, have to check the size of the hallway
     * it has to be connected to room
     * hallways are probably with a width of three and random length (pref not too long)
     * might split into two methods, vertical and horizontal
     * can override walls to connect rooms
     * @param type refers to horizontal (0) or vertical (1)
     */
    public Hallway generateHallway(Room r1, Room r2, int type) {
        int args1 = 0, args2 = 0, args3 = 0;
        if (type == 0) {
            args1 = r1.calcCenter().x;
            args2 = r2.calcCenter().x;
            args3 = r2.calcCenter().y;
        }
        if (type == 1) {
            args1 = r1.calcCenter().y;
            args2 = r2.calcCenter().y;
            args3 = r2.calcCenter().x;
        }
        return new Hallway(args1, args2, args3, type);
    }

    public void addHallways() {
        ArrayList<Room> copy = new ArrayList<>(rooms);
        int temp = random.nextInt(rooms.size());
        for (int i = 0; i < rooms.size(); i++) {
            int index = rooms.get(temp).calcNearest(rooms, copy, temp);
            int rand = random.nextInt(2);
            Hallway hallway = generateHallway(rooms.get(temp), rooms.get(index), rand);
            Hallway hallway2 = generateHallway(rooms.get(index), rooms.get(temp), Math.abs(rand - 1));
            addToMap(hallway);
            addToMap(hallway2);
            copy.remove(rooms.get(temp));
            copy.add(temp, new Room(new Tuple(1000, 1000), new Tuple(1000, 1000), new Tuple(1000, 1000), new Tuple(1000, 1000)));
            temp = index;
        }
    }

    public void addToMap(Hallway hw) {
        int min = Math.min(hw.getP1(), hw.getP2());
        int max = Math.max(hw.getP1(), hw.getP2());
        if (hw.getType() == 0) {
            for (int i = min; i <= max; i++) {
                world[i][hw.getLw()] = Tileset.FLOOR;
            }
        } else {
            for (int i = min; i <= max; i++) {
                world[hw.getLw()][i] = Tileset.FLOOR;
            }
        }
//        int centerPlus = (r1.get(2).x - r1.get(1).x) / 2;
//        int centerPlus1 = (r2.get(2).x - r2.get(1).x) / 2;
//        int centerPlus2 = (r1.get(3).x - r1.get(1).x) / 2;
//        int centerPlus3 = (r2.get(3).x - r2.get(1).x) / 2;
//        if (hw.getType() == 0) {
//            for (int i = min + centerPlus; i < max - centerPlus1; i++) {
//                world[i][hw.getLw() + 1] = Tileset.WALL;
//                world[i][hw.getLw() - 1] = Tileset.WALL;
//            }
//        } else {
//            for (int i = min + centerPlus2; i < max - centerPlus3; i++) {
//                world[hw.getLw() - 1][i] = Tileset.WALL;
//                world[hw.getLw() + 1][i] = Tileset.WALL;
//            }
//        }
        //System.out.println(min + " " + max + " " + hw.getLw());
    }

    /**
     * Creates empty grid for starting map
     * should only be called on first round
     */
    public void fillEmpty() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
    }

    public void fillAutomata() {
        double life = .2;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (random.nextDouble() < life) {
                    world[i][j] = Tileset.WALL;
                } else {
                    world[i][j] = Tileset.FLOOR;
                }
            }
        }
    }

    public TETile[][] getWorld() {
        return world;
    }
}
