package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.Serializable;

public class Input implements Serializable {
    private Tuple oldPos;
    private TETile oldTile, firstTile;
    private Tuple pos;

    private int round = 0;

    // run keyboard game
    public void run() {
        while (true) {
            mouseRead();
            input();
            Game.ter.renderFrame(Game.world);
        }
    }

    public void run(String input) {
        Game.a.removeFirst();
        while (Game.a.size() > 0) {
            mouseRead();
            processStringInput();
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

    @SuppressWarnings("Duplicates")
    // takes input
    // Using linked list allows for :Q to work more efficiently, could add close to 0 complexity as
    // it is constant time to access the first values;
    private void input() {
        if (StdDraw.hasNextKeyTyped()) {
            Game.a.add(StdDraw.nextKeyTyped());
            if (Game.a.peekFirst() == ':') {
                colonQ();
            }
            switch (Game.a.removeFirst()) {
                case 'w': move(new Tuple(0, 1)); break;
                case 'a': move(new Tuple(-1, 0)); break;
                case 's': move(new Tuple(0, -1)); break;
                case 'd': move(new Tuple(1, 0)); break;
            }
            updatePlayer();
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
    private boolean canMove(Tuple vec) {
        int newX = pos.x + vec.x;
        int newY = pos.y + vec.y;
        return !Game.world[newX][newY].equals(Tileset.WALL)
                && Game.world[newX][newY].equals(Tileset.FLOOR);
    }

    // checks the quit case
    public void colonQ() {
        Game.a.removeFirst();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                Game.a.add(StdDraw.nextKeyTyped());
                if (Game.a.peekFirst() == 'q') {
                    save();
                    System.exit(0);
                } else {
                    break;
                }
            }
        }
    }

    @SuppressWarnings("Duplicates")
    public void processStringInput() {
        if (Game.a.peekFirst() == ':') {
            colonQString();
        }
        switch (Game.a.removeFirst()) {
            case 'w': move(new Tuple(0, 1)); break;
            case 'a': move(new Tuple(-1, 0)); break;
            case 's': move(new Tuple(0, -1)); break;
            case 'd': move(new Tuple(1, 0)); break;
        }
        updatePlayer();
        round++;
    }

    @SuppressWarnings("Duplicates")
    // checks the quit case
    // l:q not working
    public void colonQString() {
        Game.a.removeFirst();
        if (Game.a.peekFirst() == 'q') {
            save();
            System.exit(0);
        }
    }

    // find position of mouse and check what tile it is
    public void mouseRead() {
        int x = (int) Math.floor(StdDraw.mouseX());
        int y = (int) Math.floor(StdDraw.mouseY());
        if (y < Game.HEIGHT) {
            displayInfo(tileType(Game.world[x][y]));
        }
    }

    // checks the tile type
    public String tileType(TETile x) {
        if (x.equals(Tileset.PLAYER)) {
            return "player";
        } else if (x.equals(Tileset.FLOOR)) {
            return "floor";
        } else if (x.equals(Tileset.WALL)) {
            return "wall";
        } else if (x.equals(Tileset.NOTHING)) {
            return "nothing";
        }
        return "";
    }

    // add bar later, for now, displays the tile on the top left
    public void displayInfo(String x) {
        Font font = new Font("Comic Sans", Font.BOLD, 30);
        StdDraw.setPenColor(Color.white);
        StdDraw.textLeft(1, Game.HEIGHT - 1, "Current tile: " + x);
        StdDraw.show();
    }

    // saves the current game state
    public void save() {
        Data.save(Game.world, "proj2/byog/SaveFiles/world");
        Data.save(this, "proj2/byog/SaveFiles/input");
        Data.save(Game.ter, "proj2/byog/SaveFiles/ter");
        Data.save(Game.map, "proj2/byog/SaveFiles/map");
    }
}
