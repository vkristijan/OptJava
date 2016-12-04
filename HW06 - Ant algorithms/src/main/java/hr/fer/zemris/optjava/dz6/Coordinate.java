package hr.fer.zemris.optjava.dz6;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Coordinate {
    private double x;
    private double y;

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    double distance(Coordinate other){
        double dist = 0;

        dist += (other.x - this.x) * (other.x - this.x);
        dist += (other.y - this.y) * (other.y - this.y);

        return Math.sqrt(dist);
    }
}
