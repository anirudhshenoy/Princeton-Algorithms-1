import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private LineSegment[] ls;
    private int n = 0;

    public BruteCollinearPoints(Point[] points) {
        List<LineSegment> lines = new ArrayList<>();
        Arrays.sort(points);
        for (int p = 0; p < points.length - 3; p++) {
            for (int q = p + 1; q < points.length - 2; q++) {
                for (int r = q + 1; r < points.length - 1; r++) {
                    for (int s = r + 1; s < points.length; s++) {
                        double slope1 = points[p].slopeTo(points[q]);
                        double slope2 = points[p].slopeTo(points[r]);
                        double slope3 = points[p].slopeTo(points[s]);
                        if (slope1 == slope2 && slope2 == slope3) {
                            lines.add(new LineSegment(points[p], points[s]));
                            n++;
                        }
                    }
                }
            }
        }
        ls = lines.toArray(new LineSegment[lines.size()]);
    }

    public int numberOfSegments() {
        return n;
    }

    public LineSegment[] segments() {
        return ls;
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
