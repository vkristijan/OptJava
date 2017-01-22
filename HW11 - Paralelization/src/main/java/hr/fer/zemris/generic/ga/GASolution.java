package hr.fer.zemris.generic.ga;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public abstract class GASolution<T> implements Comparable<GASolution<T>> {
    protected T data;
    public double fitness;

    public GASolution() {
    }

    public T getData() {
        return data;
    }

    public abstract GASolution<T> duplicate();

    @Override
    public int compareTo(GASolution<T> o) {
        return -Double.compare(this.fitness, o.fitness);
    }
}
