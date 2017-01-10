package hr.fer.zemris.optjava.dz10.nsga.function;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class LinearFunction implements IFunction {
    private int index;

    public LinearFunction(int index) {
        this.index = index;
    }


    @Override
    public double valueAt(double[] x) {
        return x[index];
    }
}
