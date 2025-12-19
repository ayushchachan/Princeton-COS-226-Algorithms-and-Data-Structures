/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class KdTree {
    private Node root;
    private int size;

    private PointComparator comp;

    /**
     * construct an empty set of points
     */
    public KdTree() {
        root = null;
        size = 0;
        comp = new PointComparator();

    }

    public static void main(String[] args) {

        In reader;
        KdTree t = new KdTree();

        reader = new In("input5.txt");

        while (!reader.isEmpty()) {
            String[] line = reader.readLine().split(" ");
            Point2D p = new Point2D(Double.parseDouble(line[0]), Double.parseDouble(line[1]));
            t.insert(p);
            System.out.println(Arrays.toString(line));

            // read next line
        }
        System.out.println(t);

        reader.close();

    }

    /**
     * @return is the set empty?
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * @return number of points in the set
     */
    public int size() {
        return this.size;
    }

    /**
     * @param p add the point to the set (if it is not already in the set)
     */
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null argument");
        if (root == null) {
            root = new Node(p, new RectHV(0, 0, 1, 1));
            size++;
        }
        else {
            this.insert(p, root, 0);
        }

    }

    private void insert(Point2D p, Node x, int level) {
        int compare = comp.compare(p, x.getPoint(), level);

        if (compare < 0) {
            if (x.hasLeft()) {
                this.insert(p, x.getLeft(), level + 1);
            }
            else {
                RectHV parentRect = x.getRect();
                RectHV newRect;
                if (level % 2 == 0) {
                    newRect = new RectHV(parentRect.xmin(),
                                         parentRect.ymin(),
                                         x.getPoint().x(),
                                         parentRect.ymax());
                }
                else {
                    newRect = new RectHV(parentRect.xmin(),
                                         parentRect.ymin(),
                                         parentRect.xmax(),
                                         x.getPoint().y());
                }

                x.left = new Node(p, newRect);
                size++;
            }
        }
        else if (compare > 0) {
            if (x.hasRight()) {
                this.insert(p, x.getRight(), level + 1);
            }
            else {
                RectHV parentRect = x.getRect();
                RectHV newRect;
                if (level % 2 == 0) {
                    newRect = new RectHV(x.getPoint().x(),
                                         parentRect.ymin(),
                                         parentRect.xmax(),
                                         parentRect.ymax());
                }
                else {
                    newRect = new RectHV(parentRect.xmin(),
                                         x.getPoint().y(),
                                         parentRect.xmax(),
                                         parentRect.ymax());
                }

                x.right = new Node(p, newRect);
                size++;
            }
        }
        else {
            return;
        }
    }

    /**
     * @param p
     * @return does the set contain point p?
     */
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null argument");
        if (root == null) return false;
        return this.contains(p, root, 0);
    }

    private boolean contains(Point2D p, Node x, int level) {
        int compare = comp.compare(p, x.getPoint(), level);

        if (compare < 0) {
            if (x.hasLeft()) {
                return this.contains(p, x.getLeft(), level + 1);
            }
            return false;
        }
        else if (compare > 0) {
            if (x.hasRight()) {
                return this.contains(p, x.getRight(), level + 1);
            }
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        for (Point2D p : new PointIterable(root)) {
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

        ArrayList<Point2D> pointList = new ArrayList<>();
        LinkedList<Node> queue = new LinkedList<>();
        if (root != null) {
            queue.add(root);
        }

        while (!queue.isEmpty()) {
            Node x = queue.removeFirst();
            if (rect.contains(x.getPoint())) {
                pointList.add(x.getPoint());
            }


            if (x.hasLeft() && rect.intersects(x.getLeft().getRect())) {
                queue.add(x.getLeft());
            }
            if (x.hasRight() && rect.intersects(x.getRight().getRect())) {
                queue.add(x.getRight());
            }

        }
        return pointList;
    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     *
     * @param p
     * @return
     */
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Null argument");
        if (root == null) {
            return null;
        }
        return this.nearest(p, root);


    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     *
     * @param p
     * @return
     */
    private Point2D nearest(Point2D p, Node x) {
        Point2D nearest = x.getPoint();
        double leastDistance = nearest.distanceSquaredTo(p);

        if (x.hasLeft() && x.getLeft().getRect().distanceSquaredTo(p) < leastDistance) {
            Point2D nearestLeftSubtree = this.nearest(p, x.getLeft());
            if (p.distanceSquaredTo(nearestLeftSubtree) < leastDistance) {
                nearest = nearestLeftSubtree;
                leastDistance = p.distanceSquaredTo(nearestLeftSubtree);
            }
        }
        if (x.hasRight() && x.getRight().getRect().distanceSquaredTo(p) < leastDistance) {
            Point2D nearestRightSubtree = this.nearest(p, x.getRight());
            if (p.distanceSquaredTo(nearestRightSubtree) < leastDistance) {
                nearest = nearestRightSubtree;
            }
        }

        return nearest;
    }

    public String toString() {
        ArrayList<ArrayList<Node>> levels = new ArrayList<>();
        if (root != null) {
            ArrayList<Node> first = new ArrayList<>();
            first.add(root);
            levels.add(first);
        }

        while (!levels.get(levels.size() - 1).isEmpty()) {
            levels.add(new ArrayList<>());

            for (Node n : levels.get(levels.size() - 2)) {
                if (n.hasLeft()) {
                    levels.get(levels.size() - 1).add(n.getLeft());
                }
                if (n.hasRight()) {
                    levels.get(levels.size() - 1).add(n.getRight());
                }
            }
        }
        return levels.toString();
    }

    private static class PointIterable implements Iterable<Point2D> {
        private final Node root;

        public PointIterable(Node root) {
            this.root = root;
        }

        /**
         * Returns an iterator over elements of type {@code T}.
         *
         * @return an Iterator.
         */
        public Iterator<Point2D> iterator() {
            return new PointIterator(root);
        }
    }

    private static class PointIterator implements Iterator<Point2D> {
        private ArrayList<Point2D> pointList;
        private int i;

        public PointIterator(Node root) {
            i = 0;
            pointList = new ArrayList<>();

            LinkedList<Node> queue = new LinkedList<>();
            if (root != null) {
                queue.add(root);
            }

            while (!queue.isEmpty()) {
                Node x = queue.removeFirst();
                pointList.add(x.getPoint());


                if (x.hasLeft()) {
                    queue.add(x.getLeft());
                }
                if (x.hasRight()) {
                    queue.add(x.getRight());
                }

            }
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        public boolean hasNext() {
            return this.i < pointList.size();
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        public Point2D next() {
            if (!hasNext()) throw new NoSuchElementException("Reached end of iterator");
            // System.out.println("point list = " + pointList.toString());
            Point2D answer = pointList.get(i++);
            // System.out.println(answer);
            return answer;
        }
    }

    private static class Node {
        private Point2D p;
        private Node left;
        private Node right;
        private RectHV rect;

        public Node(Point2D point, RectHV rect) {
            this(point);
            this.rect = rect;
        }

        public Node(Point2D point) {
            this.p = point;
            left = null;
            right = null;
        }

        public Point2D getPoint() {
            return this.p;
        }

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }

        public boolean hasLeft() {
            return this.left != null;
        }

        public boolean hasRight() {
            return this.right != null;
        }

        public RectHV getRect() {
            return rect;
        }

        public String toString() {
            return p.toString();
        }
    }

    private class PointComparator {
        /**
         * @param a     point 1
         * @param b     point 2
         * @param level tree level starting from 0
         * @return -1 if a < b, 1 if a > b else 0
         */
        public int compare(Point2D a, Point2D b, int level) {
            if (level % 2 == 0) {
                double ax = a.x();
                double bx = b.x();
                if (ax < bx) {
                    return -1;
                }
                else if (ax > bx) {
                    return 1;
                }
                else {
                    if (a.y() == b.y()) {
                        return 0;
                    }
                    return -1;
                }

            }
            else {
                double ay = a.y();
                double by = b.y();
                if (ay < by) {
                    return -1;
                }
                else if (ay > by) {
                    return 1;
                }
                else {
                    if (a.x() == b.x()) {
                        return 0;
                    }
                    return -1;
                }
            }
        }
    }
}
