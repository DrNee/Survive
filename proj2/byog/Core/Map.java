package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;

// can try to connect rooms by putting a random opening on a room and connecting it
// to another room with the shortest distance (can create method)

// alternative connecting rooms by finding centers, connecting centers
// to fix method above // to be able to connect different rooms everytime,
// combine one room at a time, move onto the next room and find the next closest
// to that one and connect those two, eventually, we will reach the final

// another alt, have rooms and just add a hallway randomly because we know
//      placement of rooms, can do random length, min just has to be the distance to the room

// NEW IDEA: WHAT IF WE RUN CELLULAR AUTOMATA FIRST AND  THEN PLACE ROOMS ON TOP
// WILL THIS ALREADY CREATE HALLWAYS?
// Brute force method, fill every place with room and check if it intersects,
//       fill until desired amount of rooms
//       hallways can be implemented later to connect rooms
public class Map {
    private static final int MINSIZE = 7, MAXSIZE = 12;
    private static final int THRESH = 1; //threshold for intersection

    private Random random;
    private Long seed;
    private int width, height;
    private ArrayList<Room> rooms;
    private TETile[][] world;

    public Map(Long seed, int width, int height) {
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
        int rwidth = MINSIZE + random.nextInt(MAXSIZE - MINSIZE + 1);
        int rlength = MINSIZE + random.nextInt(MAXSIZE - MINSIZE + 1);
        int rx = random.nextInt(width);
        int ry = random.nextInt(height);

        if (rx < (width - MAXSIZE) && ry < (height - MAXSIZE)) {
            return new Room(new Tuple(rx, ry), new Tuple(rx + rwidth, ry),
                new Tuple(rx, ry + rlength), new Tuple(rx + rwidth, ry + rlength));
        }
        return generateRoom();
    }

    // adds the room to the map
    // change later makesure work
    private void addToMap(Room room) {
        for (int i = room.get(1).x; i < room.get(2).x - 1; i++) {
            for (int j = room.get(1).y; j < room.get(3).y - 1; j++) {
                world[i][j] = Tileset.FLOOR;
            }
        }
        // adding walls to the room // temporarily do not need this
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
            if (rooms.size() == 0 || !room.intersect(rooms, THRESH)) {
                rooms.add(room);
                addToMap(room);
            }
        }
    }

    // Used to calculate the maximum amount of rooms
    private int maxRooms() {
        int max = (MAXSIZE + THRESH) * (MAXSIZE + THRESH);
        int area = width * height;
        return area / max;
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

    /**
     * connects hallways to rooms, using a copy arraylist to make a room not connect to
     * a room that has already been connected
     * randomly selects which room to begin with
     */
    // can alternatively do rooms.size() - 1 to not connect the last room (may be good for gameplay)
    public void addHallways() {
        ArrayList<Room> copy = new ArrayList<>(rooms);
        int temp = random.nextInt(rooms.size());
        for (int i = 0; i < rooms.size(); i++) {
            int index = rooms.get(temp).calcNearest(rooms, copy, temp);
            int rand = random.nextInt(2);
            Hallway hw = generateHallway(rooms.get(temp), rooms.get(index), rand);
            Hallway hw2 = generateHallway(rooms.get(index), rooms.get(temp), Math.abs(rand - 1));
            addToMap(hw);
            addToMap(hw2);
            copy.remove(rooms.get(temp));
            copy.add(temp, new Room(new Tuple(1000, 1000), new Tuple(1000, 1000),
                    new Tuple(1000, 1000), new Tuple(1000, 1000)));
            temp = index;
        }
    }

    /**
     * adds hallway to map, checks if horizontal or vertical (0 or 1)
     * @param hw the current hallway, parameters passed in checks the type
     */
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
        fillWalls();
    }

    /**
     * changes all nothing tiles to walls that surround a floor
     */
    public void fillWalls() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (world[i][j].equals(Tileset.NOTHING) && checkSurroundings(i, j)) {
                    world[i][j] = Tileset.WALL;
                }
            }
        }
    }

    /**
     * looks at the eight blocks surrounding, if its a floor, surround it with a wall
     * @param x,y the tile being checked
     * @return true/false for whether or not it should be changed
     */
    public boolean checkSurroundings(int x, int y) {
        boolean check = false;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int nx = x + i;
                int ny = y + j;

                if (i == 0 && j == 0) {
                    continue;
                } else if (nx <= 0 || ny <= 0 || nx >= width || ny >= height) {
                    continue;
                } else if (world[nx][ny].equals(Tileset.FLOOR)) {
                    check = true;
                }
            }
        }
        return check;
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

    /**
     * to fill the map using cellular automata, not yet implemented
     */
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

    public void generate() {
        fillEmpty();
        addRooms(15);
        addHallways();
    }

    public TETile[][] getWorld() {
        return world;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }
}
