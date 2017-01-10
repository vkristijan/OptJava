package hr.fer.zemris.optjava.dz10.nsga.function;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Function2 implements IFunction {
    @Override
    public double valueAt(double[] x) {
        return (1.0 + x[1]) / x[0];
    }
}
