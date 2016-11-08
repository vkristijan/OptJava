package hr.fer.zemris.optjava.dz3.neighbor;

import hr.fer.zemris.optjava.dz3.solution.DoubleArraySolution;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class DoubleArrayUnifNeighborhood extends DoubleArrayNeighborhood {
    public DoubleArrayUnifNeighborhood(double[] deltas) {
        super(deltas);
    }

    @Override
    public DoubleArraySolution randomNeighbor(DoubleArraySolution current) {
        return randomNeighbor(current, () -> rnd.nextDouble());
    }
}
