import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private LineSegment[] ls;

    public FastCollinearPoints(Point[] points) {
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
        for (int i = 0; i < pointsCopy.length; i++) {
            Arrays.sort(pointsCopy);                                                //Arrays.sort is stable for Objects[]
            Arrays.sort(pointsCopy, pointsCopy[i].slopeOrder());
            for (int second = 1, third = 2, fourth = 3;
                 fourth < pointsCopy.length;
                 second++, third++, fourth++) {
                double slope1 = pointsCopy[0].slopeTo(pointsCopy[second]);
                double slope2 = pointsCopy[0].slopeTo(pointsCopy[third]);
                double slope3 = pointsCopy[0].slopeTo(pointsCopy[fourth]);
                if (slope1 == slope2 && slope2 == slope3) {                     //Not a good idea to compare doubles like this
                    while (fourth + 1 < pointsCopy.length && slope3 == pointsCopy[fourth].slopeTo(pointsCopy[fourth + 1])) {
                        fourth++;
                    }
                    if (pointsCopy[0].compareTo(pointsCopy[second]) < 0) {
                        lines.add(new LineSegment(pointsCopy[0], pointsCopy[fourth]));
                    }
                    second = fourth;
                    third = second + 1;
                    fourth += 2;
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

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        
        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }


}
