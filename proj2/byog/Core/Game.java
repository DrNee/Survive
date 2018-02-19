package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.event.KeyEvent;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 85;
    public static final int HEIGHT = 50;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        while (true) {
            if (StdDraw.isKeyPressed(KeyEvent.VK_COLON) && StdDraw.isKeyPressed(KeyEvent.VK_Q)) {
                System.exit(0);
            }
        }
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        String seed = readInput(input);
        ter.initialize(WIDTH, HEIGHT);
        Map test = new Map(Integer.parseInt(seed), WIDTH, HEIGHT);
        initial(test);
        TETile[][] finalWorldFrame = test.getWorld();
        ter.renderFrame(finalWorldFrame);
        return finalWorldFrame;
    }

    public void initial(Map map) {
        map.fillEmpty();
        map.addRooms(15);
    }

    public String readInput(String input) {
        int l = input.length();
        String seed = "";
        if (!input.substring(l - 1, l).equalsIgnoreCase("s")
                || !input.substring(0, 1).equalsIgnoreCase("n")) {
            System.exit(0);
        }
        else {
            seed = input.substring(1, l - 1);
        }
        return seed;
    }
}
