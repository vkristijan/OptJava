package hr.fer.zemris.optjava.dz2;

import Jama.Matrix;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public interface IHFunction extends IFunction {
    Matrix getHessMatrix(Matrix arguments);
}
