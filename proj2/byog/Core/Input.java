package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import edu.princeton.cs.introcs.StdDraw;

import java.io.Serializable;

public class Input implements Serializable {
    private Tuple oldPos;
    private TETile oldTile, firstTile;
    private Tuple pos;

    private int round = 0;

    public void run() {
        while (true) {
            input();
            Game.ter.renderFrame(Game.world);
        }
    }

    public Input() {
        pos = oldPos = Map.getRooms().get(0).calcCenter();
        firstTile = Game.world[oldPos.x][oldPos.y];
        Game.world[pos.x][pos.y] = Tileset.PLAYER;
    }

    private void updatePlayer() {
        Game.world[oldPos.x][oldPos.y] = oldTile;
        Game.world[pos.x][pos.y] = Tileset.PLAYER;
    }

    // takes input
    private void input() {
        if (StdDraw.hasNextKeyTyped()) {
            switch (StdDraw.nextKeyTyped()) {
                case ':': colonQ(); // check this line
                case 'w': move(new Tuple(0, 1)); break;
                case 'a': move(new Tuple(-1, 0)); break;
                case 's': move(new Tuple(0, -1)); break;
                case 'd': move(new Tuple(1, 0)); break;
            }
            updatePlayer();
//            System.out.println(pos.toString());
            round++;
        }
    }

    // moves the player
    private void move(Tuple vec) {
        if (canMove(vec)) {
            oldTile = Game.world[oldPos.x][oldPos.y];
            if (round == 0) {
                oldTile = firstTile;
            }
            oldPos = pos;
            pos = new Tuple(pos.x + vec.x, pos.y + vec.y);
        }
    }

    // checks if can move in direction
    // temporary fix, change this later probably...
    private boolean canMove(Tuple vec) {
        int newX = pos.x + vec.x;
        int newY = pos.y + vec.y;
        return Game.world[newX][newY].getCharacter() != Tileset.WALL.getCharacter()
                && Game.world[newX][newY].getCharacter() == Tileset.FLOOR.getCharacter();
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

    /**
     * change to a folder for saving files later
     * right now, saving does not alter the positions
     */
    public void save() {
        Data.save(Game.world, "proj2/byog/SaveFiles/world");
        Data.save(this, "proj2/byog/SaveFiles/input");
        Data.save(Game.ter, "proj2/byog/SaveFiles/ter");
        Data.save(Game.map, "proj2/byog/SaveFiles/map");
    }
}
