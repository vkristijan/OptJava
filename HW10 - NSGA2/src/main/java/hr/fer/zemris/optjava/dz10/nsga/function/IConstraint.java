package hr.fer.zemris.optjava.dz10.nsga.function;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public interface IConstraint {
    void validate(double[] x);
}
