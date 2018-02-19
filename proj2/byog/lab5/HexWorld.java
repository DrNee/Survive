package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int HEIGHT = 60, WIDTH = 60;

    public static void main(String[] args) {
        TERenderer test = new TERenderer();
        test.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];

        // Have to set the world to nothing first, needs full map
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        addHexagon(world, 20, 20, 2, Tileset.MOUNTAIN);
        test.renderFrame(world);
    }

    /**
     * This will be used to generate a hexagon with the given parameter size
     * @param x y Signifies starting placement (x, y) is the top left of the hexagon
     */
    public static void addHexagon(TETile[][] world, int x, int y, int size, TETile t) {
        int columns = size * size + (size - 2);
        int rows = size*2;

        int count = size;
        int hexagons = size;

        for (int i = x; i < size + x; i++) {
            for (int j = y; j < size + y; j++) {
                world[i][j] = Tileset.MOUNTAIN;
            }
        }
    }
}
