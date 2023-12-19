package day11;

public class Point {
    long x;
    long y;

    public Point(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public long distanceBetween(Point otherPoint) {
        return Math.abs(this.x - otherPoint.x) + Math.abs(this.y - otherPoint.y);
    }
}
