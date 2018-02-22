package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

public class Menu {
    private int WIDTH, HEIGHT;
    protected long seed;

    public void run() {
        mainMenu();
        menuInput();
    }

    public Menu (int w, int h) {
        WIDTH = w;
        HEIGHT = h;
    }

    public void mainMenu() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        Font font = new Font("Courier New", Font.BOLD, 80);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(255, 255, 255);
        StdDraw.text(WIDTH/2, HEIGHT*3/4, "CS61B: THE GAME!");
        font = new Font("Times New Roman", Font.PLAIN, 30);
        StdDraw.setFont(font);
        StdDraw.text(WIDTH/2, HEIGHT/2, "New Game (N)");
        StdDraw.text(WIDTH/2, HEIGHT/2 - 2, "Load Game (L)");
        StdDraw.text(WIDTH/2, HEIGHT/2 - 4, "Quit (Q)");
        StdDraw.enableDoubleBuffering();
        StdDraw.show();
    }

    public void menuInput() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                switch (StdDraw.nextKeyTyped()) {
                    case 'n':
                        seed = seedEnter();
                        break;
                    case 'l':
                        ;
                        break;
                    case 'q':
                        System.exit(0);
                }
            }
        }
    }

    public Long seedEnter() {
        StdDraw.clear(Color.black);
        StdDraw.text(WIDTH/2, HEIGHT/2, "Enter a seed");
        StdDraw.show();
        String temp = "";
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char curr = StdDraw.nextKeyTyped();
                if (curr == 's') {
                    break;
                }
                try {
                    if (Character.isDigit(curr)) {
                        temp += curr;
                    }
                } catch (Exception e) {
                    System.out.println("not a number");
                }
            }
        }
        return Long.parseLong(temp);
    }
}
