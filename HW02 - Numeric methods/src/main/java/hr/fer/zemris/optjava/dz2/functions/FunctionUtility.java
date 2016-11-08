package hr.fer.zemris.optjava.dz2.functions;

import Jama.Matrix;
import hr.fer.zemris.optjava.dz2.IFunction;
import hr.fer.zemris.optjava.dz2.IHFunction;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class FunctionUtility {
    public static Matrix getGradient(Matrix arguments, double epsilon,  IFunction f) {
        checkArguments(arguments, f);

        int n = f.numberOfVariables();
        Matrix gradient = new Matrix(n, 1);

        for (int i = 0; i < n; ++i){
            Matrix step = new Matrix(arguments.getArray());
            step.set(i, 0, arguments.get(i, 0) - epsilon);
            double minValue = f.value(step);

            step.set(i, 0, arguments.get(i, 0) + epsilon);
            double maxValue = f.value(step);

            gradient.set(i, 0, (maxValue - minValue) / (2 * epsilon));
        }

        double norm = gradient.normF();
        return gradient.times(1.0/norm);
    }

    public static void checkArguments(Matrix arguments, IFunction f){
        if (arguments.getRowDimension() != f.numberOfVariables() || arguments.getColumnDimension() != 1){
            throw new IllegalArgumentException("Wrong number of arguments!");
        }
    }
}
