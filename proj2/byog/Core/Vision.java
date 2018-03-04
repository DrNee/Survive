package byog.Core;
import byog.TileEngine.Tileset;

import java.io.Serializable;
import java.util.ArrayList;

public class Vision implements Serializable {
    protected static ArrayList<Tuple> old = new ArrayList<>();

    private static boolean isBlocked(Tuple point) {
        return Game.world[point.x][point.y].equals(Tileset.WALL);
    }

    private static ArrayList<Tuple> generateVisiblePoints(double Radius, double dTheta, double dr) {
        ArrayList<Tuple> visiblePoints = new ArrayList<>();
        for (double theta = 0; theta <= 2 * Math.PI; theta += dTheta) {
            for (double r = 0; r <= Radius; r += dr) {
                Tuple point = new Tuple((int) Math.round(Game.player.getPos().x + r * Math.cos(theta)),
                        (int) Math.round(Game.player.getPos().y + r * Math.sin(theta)));
                    if (isBlocked(point)) {
                        if (checkInside(visiblePoints, point)) {
                            visiblePoints.add(point);
                        }
                        break;
                    } else {
                        if (checkInside(visiblePoints, point)) {
                            visiblePoints.add(point);
                        }
                    }
            }
        }
        return visiblePoints;
    }

    private static boolean checkInside(ArrayList<Tuple> vis, Tuple tup) {
        for (Tuple t: vis) {
            if (t.x == tup.x && t.y == tup.y && t != tup) {
                return false;
            }
        }
        return true;
    }

    protected static void renderWorld(int rad) {
        ArrayList<Tuple> points = generateVisiblePoints(rad, Math.toRadians(7.3), .3);
        for (int i = 0; i < points.size(); i++) {
            if (checkInside(old, points.get(i))) {
                old.add(points.get(i));
            }
        }
        for (Tuple pt: old) {
            if (Game.world[pt.x][pt.y].equals(Tileset.WALL)) {
                Game.renWorld[pt.x][pt.y] = Tileset.WALL_OLD;
            } else if (Game.world[pt.x][pt.y].equals(Tileset.CLOUD)){
                Game.renWorld[pt.x][pt.y] = Tileset.CLOUD_OLD;
            } else {
                Game.renWorld[pt.x][pt.y] = Tileset.FLOOR_OLD;
            }

        }
        for (Tuple pt: points) {
            Game.renWorld[pt.x][pt.y] = Game.world[pt.x][pt.y];
        }
    }

    /*

    start at player position: let's call it x, y
    initialize a radius r for field of view (do we want it to depend on the dim(curr_room), etc?)

    for loop that goes to 2pi that starts at 0 and += a very small dtheta
            for 0 to radius
            if r*sin(theta), r*cos(theta) correspondes to a blocked position
                add point to running point list
                break
            else
                add r*sin(theta), r*cos(theta) to a running list of points

    render only the points in the running list

    */
}
