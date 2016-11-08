package hr.fer.zemris.optjava.dz2;

import Jama.Matrix;

import java.util.Iterator;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public interface IFunction {
    int numberOfVariables();
    double value(Matrix arguments);
    Matrix getGradient(Matrix arguments);
}
