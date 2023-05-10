public class Point {
    int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
        this(0, 0);
    }

    public Point(Point p) {
        this(p.x, p.y);
    }

    public Point add(Point p) {
        return new Point(this.x + p.x, this.y + p.y);
    }
    public Point subtract(Point p) {
        return new Point(this.x - p.x, this.y - p.y); 
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    
}
