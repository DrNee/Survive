package byog.Core;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;
import java.util.HashSet;

public class Vision implements Serializable {
    private Tuple pos;

    public Vision(Player p) {
        this.pos = p.getPos();
    }

    private boolean isBlocked(Tuple point) {
        return point.x < Game.WIDTH && point.y < Game.HEIGHT
                && Game.world[point.x][point.y].equals(Tileset.WALL);
    }

    private HashSet<Tuple> generateVisiblePoints(double Radius, double dTheta, double dr) {
        HashSet<Tuple> visiblePoints = new HashSet<>();
        for (double theta = 0; theta <= 2 * Math.PI; theta += dTheta) {
            for (double r = 0; r <= Radius; r += dr) {
                Tuple point = new Tuple((int) Math.round(pos.x + r * Math.cos(theta)),
                        (int) Math.round(pos.y + r * Math.sin(theta)));
                if (isBlocked(point)) {
                    visiblePoints.add(point);
                    break;
                }
                else {
                    visiblePoints.add(point);
                }
            }
        }
        return visiblePoints;
    }

    protected TETile[][] renderWorld() {
        HashSet<Tuple> points = generateVisiblePoints(7, Math.toRadians(7.3), .6);
        for (int i = 0; i < Game.WIDTH; i++) {
            for (int j = 0; j < Game.HEIGHT; j++) {
                Game.renWorld[i][j] = Tileset.NOTHING;
            }
        }
        for (Tuple pt: points) {
            Game.renWorld[pt.x][pt.y] = Game.world[pt.x][pt.y];
        }
        return Game.renWorld;
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
