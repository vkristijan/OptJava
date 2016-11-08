package hr.fer.zemris.optjava.dz2.functions;

import Jama.Matrix;
import hr.fer.zemris.optjava.dz2.IHFunction;

/**
 * Function f(x1, x2) = (x1 - 1)^2 + 10*(x2 - 2)^2;
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Function2 implements IHFunction {
    private void checkArguments(Matrix arguments){
        if (arguments.getRowDimension() != numberOfVariables() || arguments.getColumnDimension() != 1){
            throw new IllegalArgumentException("Wrong number of arguments!");
        }
    }

    @Override
    public Matrix getHessMatrix(Matrix arguments) {
        checkArguments(arguments);

        double[][] result = new double[2][2];
        result[0][0] = 2;
        result[0][1] = 0;
        result[1][0] = 0;
        result[1][1] = 20;

        return new Matrix(result);
    }

    @Override
    public int numberOfVariables() {
        return 2;
    }

    @Override
    public double value(Matrix arguments) {
        checkArguments(arguments);

        double result = (arguments.get(0, 0) - 1) * (arguments.get(0, 0) - 1);
        result += 10 * (arguments.get(1, 0) - 2) * (arguments.get(1, 0) - 2);

        return result;
    }

    @Override
    public Matrix getGradient(Matrix arguments) {
        checkArguments(arguments);

        double[][] result = new double[numberOfVariables()][1];
        result[0][0] = 2 * arguments.get(0, 0) - 2;
        result[1][0] = 20 * arguments.get(1, 0) - 40;

        return new Matrix(result);
    }
}
