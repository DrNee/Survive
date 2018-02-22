package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
//import edu.princeton.cs.introcs.StdDraw;

import java.awt.Font;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 85;
    public static final int HEIGHT = 50;
    private static final Font GENERIC = new Font("Times New Roman", Font.PLAIN, 30);

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        // initialize starting screen
        ter.initialize(WIDTH, HEIGHT);
        Menu menu = new Menu(WIDTH, HEIGHT);
        menu.run();

        // generate the map from the given seed
        Map test = new Map(menu.seed, WIDTH, HEIGHT);
        test.generate();
        TETile[][] finalWorldFrame = test.getWorld();
        ter.renderFrame(finalWorldFrame);

        // add the player
        Player player = new Player(finalWorldFrame, test.getRooms());

        // continually move the world, add win condition later
        while (true) {
            ter.renderFrame(finalWorldFrame);
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
        // Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        String seed = readInput(input);
        ter.initialize(WIDTH, HEIGHT);
        Map test = new Map(Long.parseLong(seed), WIDTH, HEIGHT);
        test.generate();
        TETile[][] finalWorldFrame = test.getWorld();
        //ter.renderFrame(finalWorldFrame);
        return finalWorldFrame;
    }

    // temporary for phase 1
    private String readInput(String input) {
        int l = input.length();
        String seed = "";
        if (!input.substring(l - 1, l).equalsIgnoreCase("s")
                || !input.substring(0, 1).equalsIgnoreCase("n")) {
            System.exit(0);
        } else {
            seed = input.substring(1, l - 1);
        }
        return seed;
    }

//    private void input() {
//        switch (StdDraw.nextKeyTyped()) {
//            case 'w': ;
//            case 'a': ;
//            case 's': ;
//            case 'd': ;
//        }
//    }
}
