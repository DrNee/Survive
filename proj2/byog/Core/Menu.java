package byog.Core;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Font;
import java.awt.Color;
import java.io.Serializable;

public class Menu implements Serializable{
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
                switch (StdDraw.nextKeyTyped()) {
                    case 'n':
                        seed = seedEnter();
                        Game.map = new Map(seed, WIDTH, HEIGHT);
                        Game.map.generate();
                        Game.world = Game.map.getWorld();
                        Game.player = new Input();
                        return;
                    case 'l':
                        loadData();
                        return;
                    case 'q':
                        System.exit(0);
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
                char curr = StdDraw.nextKeyTyped();
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

    public void loadData() {
        Game.ter = Data.load("proj2/byog/SaveFiles/ter");
        Game.map = Data.load("proj2/byog/SaveFiles/map");
        Game.world = Data.load("proj2/byog/SaveFiles/world");
        Game.player = Data.load("proj2/byog/SaveFiles/input");
    }
}
