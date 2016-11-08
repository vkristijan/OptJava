package hr.fer.zemris.optjava.dz3.decoder;

import hr.fer.zemris.optjava.dz3.decoder.IDecoder;
import hr.fer.zemris.optjava.dz3.solution.DoubleArraySolution;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class PassThroughDecoder implements IDecoder<DoubleArraySolution> {
    @Override
    public double[] decode(DoubleArraySolution solution) {
        return solution.getValues();
    }

    @Override
    public void decode(DoubleArraySolution solution, double[] result) {
        result = decode(solution);
    }
}
