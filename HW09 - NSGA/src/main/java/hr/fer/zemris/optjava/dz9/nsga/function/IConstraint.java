package hr.fer.zemris.optjava.dz9.nsga.function;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public interface IConstraint {
    void validate(double[] x);
}
