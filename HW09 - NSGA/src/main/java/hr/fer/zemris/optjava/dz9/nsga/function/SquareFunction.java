package hr.fer.zemris.optjava.dz9.nsga.function;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class SquareFunction implements IFunction {
    private int index;

    public SquareFunction(int index) {
        this.index = index;
    }

    @Override
    public double valueAt(double[] x) {
        return Math.pow(x[index], 2);
    }
}
