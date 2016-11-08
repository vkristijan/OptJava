package hr.fer.zemris.optjava.dz2;

import Jama.Matrix;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class NumOptAlgorithms {
    private static final double epsilon = 0.01;

    static Matrix gradientDescent(IFunction function, int maxIterations, Matrix x, boolean shallPrint){
        for (int i = 0; i < maxIterations; ++i){
            if (function.getGradient(x).normF() < epsilon){
                break;
            }

            if (shallPrint){
                printX(x);
                System.out.println(function.value(x));
            }

            Matrix d = function.getGradient(x).times(-1);

            double lambda = getLambda(function, x, d);
            x = x.plus(d.times(lambda));
        }

        if (shallPrint){
            printX(x);
        }
        return x;
    }

    static Matrix newtonMethod(IHFunction function, int maxIterations, Matrix x, boolean shallPrint){
        for (int i = 0; i < maxIterations; ++i){
            if (function.getGradient(x).normF() < epsilon){
                break;
            }

            if (shallPrint){
                printX(x);
            }

            Matrix d = function.getHessMatrix(x).inverse().times(function.getGradient(x)).times(-1);

            double lambda = getLambda(function, x, d);
            x = x.plus(d.times(lambda));
        }

        if (shallPrint){
            printX(x);
        }
        return x;
    }

    public static void printX(Matrix x){
        System.out.print("[");
        double[] row = x.getRowPackedCopy();
        for (double number : row){
            System.out.printf("%.4f ", number);
        }
        System.out.println("]");
    }

    private static double getLambda(IFunction function, Matrix x, Matrix d){
        double lambdaMin = 0;
        double lambdaMax = 1;

        while (calculateDTheta(function, x, d, lambdaMax) < 0){
            lambdaMax *= 2;
        }

        double lambda;
        double dTheta;
        do {
            lambda = (lambdaMin + lambdaMax) / 2;
            dTheta = calculateDTheta(function, x, d, lambda);

            if (dTheta > 0) {
                lambdaMax = lambda;
            } else {
                lambdaMin = lambda;
            }
        } while (Math.abs(dTheta) > epsilon);

        return lambda;
    }

    private static double calculateDTheta(IFunction function, Matrix x, Matrix d, double lambda){
        return function.getGradient(x.plus(d.times(lambda))).transpose().times(d).get(0,0);
    }
}
