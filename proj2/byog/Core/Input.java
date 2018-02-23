package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;

import edu.princeton.cs.introcs.StdDraw;

public class Input {
    private TERenderer ter;
    private TETile[][] world;

    private ArrayList<Room> rooms;
    private Tuple oldPos;
    private TETile oldTile, firstTile;
    private Tuple pos;

    private int round = 0;

    public void run() {
        while (true) {
            input();
            ter.renderFrame(world);
        }
    }

    public Input(TERenderer ter, TETile[][] world, ArrayList<Room> rooms) {
        this.ter = ter;
        this.world = world;
        this.rooms = rooms;
        pos = oldPos = rooms.get(0).calcCenter();
        firstTile = world[oldPos.x][oldPos.y];
        world[pos.x][pos.y] = Tileset.PLAYER;
    }

    private void updatePlayer() {
        world[oldPos.x][oldPos.y] = oldTile;
        world[pos.x][pos.y] = Tileset.PLAYER;
    }

    // takes input
    private void input() {
        if (StdDraw.hasNextKeyTyped()) {
            switch (StdDraw.nextKeyTyped()) {
                case ':': colonQ();
                case 'w': move(0, new Tuple(0, 1)); break;
                case 'a': move(1, new Tuple(-1, 0)); break;
                case 's': move(2, new Tuple(0, -1)); break;
                case 'd': move(3, new Tuple(1, 0)); break;
            }
            updatePlayer();
            round++;
        }
    }

    // moves the player
    private void move(int dir, Tuple vec) {
        if (canMove(dir, vec)) {
            oldTile = world[oldPos.x][oldPos.y];
            if (round == 0) {
                oldTile = firstTile;
            }
            oldPos = pos;
            pos = new Tuple(pos.x + vec.x, pos.y + vec.y);
        }
    }

    // checks if can move in direction
    private boolean canMove(int dir, Tuple vec) {
        int newX = pos.x + vec.x;
        int newY = pos.y + vec.y;
        return !world[newX][newY].equals(Tileset.WALL)
                && world[newX][newY].equals(Tileset.FLOOR);
    }

    // checks the quit case
    public void colonQ() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                if (StdDraw.nextKeyTyped() == 'q') {
                    save();
                    System.exit(0);
                } else {
                    break;
                }
            }
        }
    }

    public void save() {

    }
}
