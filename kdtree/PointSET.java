/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

public class PointSET {

    private TreeSet<Point2D> pointSet;

    /**
     * construct an empty set of points
     */
    public PointSET() {
        pointSet = new TreeSet<>();

    }

    public static void main(String[] args) {

    }

    /**
     * @return is the set empty?
     */
    public boolean isEmpty() {
        return this.pointSet.isEmpty();
    }

    /**
     * @return number of points in the set
     */
    public int size() {
        return pointSet.size();
    }

    /**
     * @param p add the point to the set (if it is not already in the set)
     */
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null argument");
        pointSet.add(p);

    }

    /**
     * @param p
     * @return does the set contain point p?
     */
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null argument");
        return pointSet.contains(p);
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        for (Point2D p : pointSet) {
            p.draw();
        }

    }

    /**
     * all points that are inside the rectangle (or on the boundary)
     *
     * @param rect
     * @return
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Null argument");
        List<Point2D> points = new LinkedList<>();
        for (Point2D p : pointSet) {
            if (rect.contains(p)) {
                points.add(p);
            }
        }
        return points;

    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     *
     * @param p
     * @return
     */
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null argument");
        double leastDistance = Integer.MAX_VALUE;

        Point2D nearest = null;

        for (Point2D q : pointSet) {

            double distPQ = p.distanceSquaredTo(q);
            if (distPQ < leastDistance) {
                leastDistance = distPQ;
                nearest = q;
            }

        }
        return nearest;
    }

}
