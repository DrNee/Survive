package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Font;
import java.awt.Color;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class Menu implements Serializable {
    private static final Color BGCOLOR = new Color(224, 236, 224);
    private int WIDTH, HEIGHT;
    protected Long seed;

    public void run() {
        mainMenu();
        menuInput();
    }

    public Menu(int w, int h) {
        WIDTH = w;
        HEIGHT = h;
    }

    public void mainMenu() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        Font font = new Font("Comic Sans", Font.BOLD, 80);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(BGCOLOR);
        StdDraw.setPenColor(79, 175, 255);
        StdDraw.text(WIDTH / 2, HEIGHT * 3 / 4, "CS61B: THE GAME!");
        font = new Font("Comic Sans", Font.PLAIN, 30);
        StdDraw.setFont(font);
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "New Game (N)");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 2, "Load Game (L)");
        StdDraw.text(WIDTH / 2, HEIGHT / 2 - 4, "Quit (Q)");
        StdDraw.enableDoubleBuffering();
        StdDraw.show();
    }

    public void menuInput() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                switch (Character.toLowerCase(StdDraw.nextKeyTyped())) {
                    case 'n':
                        seed = seedEnter();
                        startNewGame();
                        return;
                    case 'l':
                        loadData();
                        return;
                    case 'q':
                        System.exit(0);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public Long seedEnter() {
        StdDraw.clear(BGCOLOR);
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "Enter a seed");
        StdDraw.show();
        String temp = "";
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char curr = Character.toLowerCase(StdDraw.nextKeyTyped());
                if (curr == 's') {
                    break;
                }
                if (Character.isDigit(curr)) {
                    temp += curr;
                }
            }
        }
        return Long.parseLong(temp);
    }

    // starts a fresh game
    public void startNewGame() {
        Game.map = new Map(seed, WIDTH, HEIGHT);
        Game.map.generate();
        Game.world = Game.map.getWorld();
        Game.player = new Player();
        Vision.old = new ArrayList<>();
        renWorldInit();
        Player.alive = true;
        Player.win = false;
        Enemy.spawn();
        Game.player.placeDoor();
    }

    // game Over screen
    public void gameOver() {
        Font font = new Font("Comic Sans", Font.BOLD, 80);
        StdDraw.setFont(font);
        StdDraw.clear(BGCOLOR);
        StdDraw.setPenColor(79, 175, 180);
        StdDraw.text(Game.WIDTH / 2, Game.HEIGHT / 2 + 7, "G A M E");
        StdDraw.text(Game.WIDTH / 2, Game.HEIGHT / 2 - 1, "O V E R");
        font = new Font("Comic Sans", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(Game.WIDTH / 2, Game.HEIGHT / 2 - 8, "Restart (R)");
        StdDraw.text(Game.WIDTH / 2, Game.HEIGHT / 2 - 10, "Quit (Q)");
        StdDraw.show();
        restartOrQuit();
    }

    public void win() {
        Font font = new Font("Comic Sans", Font.BOLD, 80);
        StdDraw.setFont(font);
        StdDraw.clear(BGCOLOR);
        StdDraw.setPenColor(79, 175, 180);
        StdDraw.text(Game.WIDTH / 2, Game.HEIGHT / 2 + 7, "C O N G R A T U L A T I O N S");
        StdDraw.text(Game.WIDTH / 2, Game.HEIGHT / 2 - 1, "Y O U W I N !");
        font = new Font("Comic Sans", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(Game.WIDTH / 2, Game.HEIGHT / 2 - 8, "Restart (R)");
        StdDraw.text(Game.WIDTH / 2, Game.HEIGHT / 2 - 10, "Quit (Q)");
        StdDraw.show();
        restartOrQuit();
    }

    public void restartOrQuit() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                switch (Character.toLowerCase(StdDraw.nextKeyTyped())) {
                    case 'r': restart(); break;
                    case 'q': System.exit(0);
                }
            }
        }
    }

    public void restart() {
        Game game = new Game();
        game.playWithKeyboard();
    }

    public void renWorldInit() {
        Game.renWorld = new TETile[Game.WIDTH][Game.HEIGHT];
        for (int i = 0; i < Game.WIDTH; i++) {
            for (int j = 0; j < Game.HEIGHT; j++) {
                Game.renWorld[i][j] = Tileset.NOTHING;
            }
        }
    }

    // loads old game data
    public void loadData() {
        File check = new File("map.txt");
        if (!check.exists()) {
            System.exit(0);
        }
        Player.alive  = true;
        Player.win = false;
        Vision.old = new ArrayList<>();
        Game.random = Data.load("random.txt");
        Game.map = Data.load("map.txt");
        Game.world = Data.load("world.txt");
        Game.player = Data.load("input.txt");
        Game.enemies = Data.load("enemies.txt");
        Game.renWorld = Data.load("renworld.txt");
    }
}
