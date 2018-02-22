package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;

public class Player {
    private TETile[][] world;
    private ArrayList<Room> rooms;

    public Player(TETile[][] world, ArrayList<Room> rooms) {
        this.world = world;
        this.rooms = rooms;
        addPlayer();
    }

    public void addPlayer() {
        world[rooms.get(0).calcCenter().x][rooms.get(0).calcCenter().y] = Tileset.PLAYER;
    }
}
