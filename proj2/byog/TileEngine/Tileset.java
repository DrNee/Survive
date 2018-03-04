package byog.TileEngine;

import java.awt.Color;
import java.io.Serializable;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 *
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset implements Serializable {
    public static final TETile PLAYER = new TETile('人', new Color(56, 132, 255), new Color(147, 255, 226), "player");
    public static final TETile WALL = new TETile('■', new Color(79, 79, 79), new Color(30, 30, 30),
            "wall");
    public static final TETile FLOOR = new TETile('▒', new Color(119, 61, 0), new Color(145, 74, 0),
            "floor");
    public static final TETile NOTHING = new TETile(' ', Color.black, Color.black, "nothing");
    public static final TETile ENEMY = new TETile('☣', Color.RED, Color.white, "enemy");
    public static final TETile CLOUD = new TETile('☁', new Color(149, 198, 0), new Color(80, 80, 80), "cloud");
    public static final TETile KEY = new TETile('⚿', new Color(255,215,0), new Color(50, 50, 50), "key");
    public static final TETile LOCKED_DOOR = new TETile('㋬', new Color(216, 240, 255), Color.black,
            "locked door");
    public static final TETile UNLOCKED_DOOR = new TETile('▢', new Color(216, 240, 255), Color.black,
            "unlocked door");


    public static final TETile WALL_OLD = new TETile('■', new Color(40, 40, 40), new Color(12, 12, 12),
            "old_wall");
    public static final TETile FLOOR_OLD = new TETile('▒', new Color(70, 30, 0), new Color(124, 63, 0),
            "old_floor");
    public static final TETile CLOUD_OLD = new TETile('☁', new Color(88, 138, 0), new Color(30, 30, 30), "old_star");
    public static final TETile KEY_OLD = new TETile('⚿', new Color(200,165,0), new Color(10, 10, 10), "old_key");
    public static final TETile LOCKED_DOOR_OLD = new TETile('㋬', new Color(150, 180, 190), Color.black,
            "locked door");
    public static final TETile UNLOCKED_DOOR_OLD = new TETile('▢', new Color(150, 180, 190), Color.black,
            "unlocked door");


    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass");
    public static final TETile WATER = new TETile('≈', Color.blue, Color.black, "water");
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower");
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain");
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "tree");
}


