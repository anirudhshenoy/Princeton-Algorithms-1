import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class BruteCollinearPoints {
    private LineSegment[] ls;


    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        if (Arrays.asList(points).subList(0, points.length).contains(null)) {
            throw new IllegalArgumentException();
        }
        Point[] pointsCopy = new Point[points.length];
        System.arraycopy(points, 0, pointsCopy, 0, points.length);
        Arrays.sort(pointsCopy);
        if (checkDuplicates(pointsCopy)) {
            throw new IllegalArgumentException();
        }
        List<LineSegment> lines = new ArrayList<>();

        for (int p = 0; p < pointsCopy.length - 3; p++) {
            for (int q = p + 1; q < pointsCopy.length - 2; q++) {
                for (int r = q + 1; r < pointsCopy.length - 1; r++) {
                    for (int s = r + 1; s < pointsCopy.length; s++) {
                        double slope1 = pointsCopy[p].slopeTo(pointsCopy[q]);
                        double slope2 = pointsCopy[p].slopeTo(pointsCopy[r]);
                        double slope3 = pointsCopy[p].slopeTo(pointsCopy[s]);
                        if (slope1 == slope2 && slope2 == slope3) {
                            lines.add(new LineSegment(pointsCopy[p], pointsCopy[s]));
                        }
                    }
                }
            }
        }
        ls = lines.toArray(new LineSegment[lines.size()]);
    }

    public int numberOfSegments() {
        return ls.length;
    }

    public LineSegment[] segments() {
        LineSegment[] lsTemp = new LineSegment[ls.length];
        System.arraycopy(ls, 0, lsTemp, 0, ls.length);
        return lsTemp;
    }

    private boolean checkDuplicates(Point[] pointsCopy) {
        for (int i = 0; i < pointsCopy.length - 1; i++) {
            if (pointsCopy[i].compareTo(pointsCopy[i + 1]) == 0) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {

        // read the n pointsCopy from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the pointsCopy
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
