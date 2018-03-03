package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.io.Serializable;

public class Player implements Serializable {
    private Tuple oldPos;
    private Tuple pos;
    protected static boolean alive = true;
    protected static boolean win = false;
    private int hunger = 100;

    protected static int round = 0;

    // run keyboard game
    public void run() {
        while (alive && !win) {
            rayCast();
            gui();
            input();
            Game.ter.renderFrame(Game.renWorld);
        }
    }

    public void run(String input) {
        if (!Game.a.isEmpty()) {
            Game.a.removeFirst();
        }
        while (Game.a.size() > 0) {
            processStringInput();
            Game.ter.renderFrame(Game.world);
        }
    }

    public Player() {
        pos = oldPos = Map.getRooms().get(0).calcCenter();
        Game.world[pos.x][pos.y] = Tileset.PLAYER;
    }

    private void updatePlayer() {
        Game.world[oldPos.x][oldPos.y] = Tileset.FLOOR;
        Game.world[pos.x][pos.y] = Tileset.PLAYER;
        hunger--;
        if (hunger == 0) {
            alive = false;
        }
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
            enemyMove();
            letterCheck(Game.a.removeFirst());
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
        if (Game.world[newX][newY].equals(Tileset.ENEMY)) {
            alive = false;
        } else if (Game.world[newX][newY].equals(Tileset.STAR)) {
            hunger += 15;
            return true;
        } else if (Game.world[newX][newY].equals(Tileset.LOCKED_DOOR)) {
            win = true;
            return true;
        }
        return Game.world[newX][newY].equals(Tileset.FLOOR);
    }

    // checks the quit case
    private void colonQ() {
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

    private void processStringInput() {
        if (Game.a.peekFirst() == ':') {
            colonQString();
        }
        enemyMove();
        letterCheck(Game.a.removeFirst());
        updatePlayer();
        round++;
    }

    // checks the quit case
    private void colonQString() {
        Game.a.removeFirst();
        if (Game.a.peekFirst() == 'q') {
            save();
        }
    }

    private void letterCheck(char letter) {
        switch (letter) {
            case 'w': move(new Tuple(0, 1)); break;
            case 'a': move(new Tuple(-1, 0)); break;
            case 's': move(new Tuple(0, -1)); break;
            case 'd': move(new Tuple(1, 0)); break;
            default: break;
        }
    }

    // find position of mouse and check what tile it is
    private void mouseRead() {
        int x = (int) Math.floor(StdDraw.mouseX());
        int y = (int) Math.floor(StdDraw.mouseY());
        if (y < Game.HEIGHT) {
            displayInfo(tileType(Game.world[x][y]));
        }
    }

    // checks the tile type
    private String tileType(TETile x) {
        if (x.equals(Tileset.PLAYER)) {
            return "player";
        } else if (x.equals(Tileset.ENEMY)) {
            return "enemy";
        } else if (x.equals(Tileset.STAR)) {
            return "food";
        } else if (x.equals(Tileset.LOCKED_DOOR)) {
            return "locked_door";
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
    private void displayInfo(String x) {
        StdDraw.setPenColor(Color.white);
        StdDraw.textLeft(1, Game.HEIGHT, "Current tile: " + x);
    }

    private void enemyMove() {
        for (Enemy e: Game.enemies) {
            e.randomMove();
            e.updateEnemy();
        }
    }

    private void gui() {
        mouseRead();
        StdDraw.setPenColor(Color.white);
        StdDraw.text(Game.WIDTH / 2, Game.HEIGHT, "Hunger: " + hunger);
        StdDraw.show();
    }

    public void placeDoor() {
        boolean placed = false;
        while (!placed) {
            int w = Game.random.nextInt(Game.WIDTH);
            int h = Game.random.nextInt(Game.HEIGHT);
            if (Game.world[w][h].equals(Tileset.FLOOR)) {
                Game.world[w][h] = Tileset.LOCKED_DOOR;
                placed = true;
            }
        }
    }

    public void rayCast() {
        Vision rc = new Vision(this);
        rc.renderWorld();
    }

    public Tuple getPos() {
        return pos;
    }

    // saves the current game state
    private void save() {
        Data.save(Game.random, "random.txt");
        Data.save(Game.world, "world.txt");
        Data.save(this, "input.txt");
        Data.save(Game.map, "map.txt");
        Data.save(Game.enemies, "enemies.txt");
        Data.save(Game.renWorld, "renworld.txt");
    }
}
