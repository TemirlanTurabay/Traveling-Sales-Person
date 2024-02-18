import java.awt.*;

public class Tour {
    // the Node class
    public static class Node {
        Point point;
        Node next;

        public Node(Point point) {
            this.point = point;
        }
    }

    Node head;

    // creates an empty tour````````````````````
    public Tour() {
        head = null;
    }

    // creates the 4-point tour a->b->c->d->a (for debugging)
    public Tour(Point a, Point b, Point c, Point d) {
        this.head = new Node(a);
        this.head.next = new Node(b);
        this.head.next.next = new Node(c);
        this.head.next.next.next = new Node(d);
        this.head.next.next.next.next = this.head;
    }

    // returns the number of points in this tour
    public int size() {
        Node current = head;
        int counter = 0;
        do {
            current = current.next;
            counter++;
        } while (current != head);
        return counter;
    }

    // returns the length of this tour
    public double length() {
        Node current = head;
        double sum = 0;
        do {
            sum += current.point.distanceTo(current.next.point);
            current = current.next;
        } while (current != head);
        return sum;
    }

    // returns a string representation of this tour
    public String toString() {
        if (head.next == null) return "";
        Node current = head;
        StringBuilder ans = new StringBuilder();
        do {
            ans.append(current.point).append("\n");
            current = current.next;
        } while (current != head);
        return ans.toString();
    }

    // draws this tour to standard drawing
    public void draw() {
        if (head.next == null) return;
        Node current = head;
        StdDraw.setPenColor(Color.BLACK);
        do {
            current.point.drawTo(current.next.point);
            current = current.next;
        } while (current != head);
    }

    // inserts p using the nearest neighbor heuristic
    public void insertNearest(Point p) {
        Node newp = new Node(p);
        if (head == null) {
            head = newp;
            newp.next = head;
            return;
        }
        Node current = head;
        double min = Double.MAX_VALUE;
        double dist;
        Node insert_after = current;
        do {
            dist = current.point.distanceTo(p);
            if (dist < min) {
                min = dist;
                insert_after = current;
            }
            current = current.next;
        } while (current != head);
        Node temp = insert_after.next;
        insert_after.next = new Node(p);
        insert_after.next.next = temp;
    }

    // inserts p using the smallest increase heuristic
    public void insertSmallest(Point p) {
        Node newp = new Node(p);
        if (head == null) {
            head = newp;
            newp.next = head;
            return;
        }
        Node current = head;
        double min = Double.MAX_VALUE;
        Node insert_after = current;
        do {
            if (current.point.distanceTo(p) + p.distanceTo(current.next.point) - current.point.distanceTo(current.next.point) < min) {
                insert_after = current;
            }
            current = current.next;
        } while (current != head);
        Node temp = insert_after.next;
        insert_after.next = new Node(p);
        insert_after.next.next = temp;
    }

    // tests this class by calling all constructors and instance methods
    public static void main(String[] args) {
        // define 4 points, corners of a square
        Point a = new Point(1.0, 1.0);
        Point b = new Point(1.0, 4.0);
        Point c = new Point(4.0, 4.0);
        Point d = new Point(4.0, 1.0);
        // create the tour a -> b -> c -> d -> a
        Tour squareTour = new Tour(a, b, c, d);
        // print the size to standard output
        int size = squareTour.size();
        StdOut.println("# of points = " + size);
        // print the tour length to standard output
        double length = squareTour.length();
        StdOut.println("Tour length = " + length);
        // print the tour to standard output
        StdOut.println(squareTour);
        StdDraw.setXscale(0, 6);
        StdDraw.setYscale(0, 6);
        Point e = new Point(5.0, 6.0);
        squareTour.insertNearest(e);
        squareTour.draw();
    }
}
