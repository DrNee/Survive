package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;

public class Player implements Serializable {
    private Tuple oldPos;
    private Tuple pos;

    protected static int round = 0;

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
            processStringInput();
//            Game.ter.renderFrame(Game.world);
        }
    }

    public Player() {
        pos = oldPos = Map.getRooms().get(0).calcCenter();
        Game.world[pos.x][pos.y] = Tileset.PLAYER;
    }

    private void updatePlayer() {
        Game.world[oldPos.x][oldPos.y] = Tileset.FLOOR;
        Game.world[pos.x][pos.y] = Tileset.PLAYER;
    }

    // takes input
    // Using linked list allows for :Q to work more efficiently, could add close to 0 complexity as
    // it is constant time to access the first values;
    private void input() {
        if (StdDraw.hasNextKeyTyped()) {
            Game.a.add(StdDraw.nextKeyTyped());
            if (Game.a.peekFirst() == ':') {
                colonQ();
            }
            letterCheck(Game.a.removeFirst());
//            enemyMove();
            updatePlayer();
            round++;
        }
    }

    // moves the player
    private void move(Tuple vec) {
        if (canMove(vec)) {
            oldPos = pos;
            pos = new Tuple(pos.x + vec.x, pos.y + vec.y);
        }
    }

    // checks if can move in direction
    private boolean canMove(Tuple vec) {
        int newX = pos.x + vec.x;
        int newY = pos.y + vec.y;
        return Game.world[newX][newY].equals(Tileset.FLOOR);
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

    public void processStringInput() {
        if (Game.a.peekFirst() == ':') {
            colonQString();
        }
        letterCheck(Game.a.removeFirst());
        updatePlayer();
//        enemyMove();
        round++;
    }

    // checks the quit case
    public void colonQString() {
        Game.a.removeFirst();
        if (Game.a.peekFirst() == 'q') {
            save();
            System.exit(0);
        }
    }

    public void letterCheck(char letter) {
        switch (letter) {
            case 'w': move(new Tuple(0, 1)); break;
            case 'a': move(new Tuple(-1, 0)); break;
            case 's': move(new Tuple(0, -1)); break;
            case 'd': move(new Tuple(1, 0)); break;
            default: break;
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
        StdDraw.textLeft(1, Game.HEIGHT, "Current tile: " + x);
        StdDraw.show();
    }
//
//    public void enemyMove() {
//        for (Enemy e: Game.enemies) {
//            e.randomMove();
//            e.updateEnemy();
//        }
//    }

    // saves the current game state
    public void save() {
        Data.save(Game.world, "proj2/byog/SaveFiles/world.txt");
        Data.save(this, "proj2/byog/SaveFiles/input.txt");
        Data.save(Game.map, "proj2/byog/SaveFiles/map.txt");
//        Data.save(Game.enemies, "proj2/byog/SaveFiles/enemies.txt");
    }
}
