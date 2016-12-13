package hr.fer.zemris.optjava.dz7.ann;

import java.util.function.Function;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public enum TransferFunction {
    SIGMOID(x -> 1 / (1 + Math.pow(Math.E, -x))),
    STEP(x -> x > 0 ? x : 0),
    LINEAR(x -> x);

    private Function<Double, Double> function;

    TransferFunction(Function<Double, Double> function){
        this.function = function;
    }

    public double valueAt(double x){
        return function.apply(x);
    }
}
