package byog.Core;

import byog.TileEngine.TETile;
import org.junit.Test;
import static org.junit.Assert.*;

public class test {

    @Test
    public static void main(String[] args) {
        Game game = new Game();
        Game game2 = new Game();
        Game game3 = new Game();
        TETile[][] worldState = game.playWithInputString("n3415218040718096461ssdsddaddaad");
        TETile[][] world2 = game2.playWithInputString("n3415218040718096461ssdsddaddaa:q");
        TETile[][] world3 = game3.playWithInputString("ld");
        for (int i = 0; i < worldState[0].length; i++) {
            assertArrayEquals(worldState[i], world3[i]);
        }
//        assertArrayEquals("they are not equal", worldState, world2);
    }
}
