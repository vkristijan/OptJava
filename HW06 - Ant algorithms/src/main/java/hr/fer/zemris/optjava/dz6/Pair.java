package hr.fer.zemris.optjava.dz6;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Pair<X extends Comparable<X>, Y> implements Comparable<Pair<X, Y>> {
    private X x;
    private Y y;

    public Pair(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    public X getX() {
        return x;
    }

    public void setX(X x) {
        this.x = x;
    }

    public Y getY() {
        return y;
    }

    public void setY(Y y) {
        this.y = y;
    }

    @Override
    public int compareTo(Pair<X, Y> o) {
        return this.x.compareTo(o.x);
    }
}
