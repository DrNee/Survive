package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;

// NEW IDEA: WHAT IF WE RUN CELLULAR AUTOMATA FIRST AND  THEN PLACE ROOMS ON TOP
// WILL THIS ALREADY CREATE HALLWAYS?
// TODO: Brute force method, fill every place with room and check if it intersects,
//       fill until desired amount of rooms
//       hallways can be implemented later to connect rooms
public class Map {
    private static final int WIDTH = 85;
    private static final int HEIGHT = 50;

    private static final long SEED = 1234567;
    private static final Random RANDOM = new Random(SEED);

    private static final int minsize = 5, maxsize = 12;
    private ArrayList<Room> rooms;
    private TETile[][] world;

    public Map() {
        rooms = new ArrayList<>();
        world = new TETile[WIDTH][HEIGHT];
    }

    /**
     * generate a hallway, have to check the size of the hallway
     * it has to be connected to room
     * hallways are probably with a width of three and random length (pref not too long)
     * might split into two methods, vertical and horizontal
     * can override walls to connect rooms
     */
    public static void generateHallway() {

    }


    /**
     * generate a room, have to check the size of the room
     * it has to be connected to hallways
     * we have to leave an opening somewhere and link up the room and hallway
     */
    public static Room generateRoom() {
        int rwidth = minsize + RANDOM.nextInt(maxsize - minsize + 1);
        int rlength = minsize + RANDOM.nextInt(maxsize - minsize + 1);
        int rx = RANDOM.nextInt(WIDTH);
        int ry = RANDOM.nextInt(HEIGHT);

        if (rx < (WIDTH - maxsize) && ry < (HEIGHT- maxsize)) {
            return new Room(new Tuple(rx, ry), new Tuple(rx + rwidth, ry),
                new Tuple(rx, ry + rlength), new Tuple(rx + rwidth, ry + rlength));
        }
        return generateRoom();
    }

    // adds the room to the map
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
            if (rooms.size() == 0 || !room.intersect(rooms)) {
                rooms.add(room);
                addToMap(room);
            }
        }
    }

    // Used to calculate the maximum amount of rooms
    private int maxRooms() {
        int max = maxsize*maxsize;
        int area = WIDTH * HEIGHT;
        return area/max;
    }

    /**
     * Creates empty grid for starting map
     * should only be called on first round
     */
    public void fillEmpty() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
    }

    public void fillAutomata() {
        double life = .4;
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (RANDOM.nextDouble() < life) {
                    world[i][j] = Tileset.FLOOR;
                } else {
                    world[i][j] = Tileset.NOTHING;
                }
            }
        }
    }

    public TETile[][] getWorld() {
        return world;
    }
}
