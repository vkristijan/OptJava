package hr.fer.zemris.optjava.dz3.neighbor;

import hr.fer.zemris.optjava.dz3.solution.BitvectorSolution;

import java.util.Random;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class BitVectorNeighborhood implements INeighborhood<BitvectorSolution> {
    private static final Random rnd = new Random();

    @Override
    public BitvectorSolution randomNeighbor(BitvectorSolution current) {
        BitvectorSolution newSolution = current.duplicate();
        newSolution.flipBit(rnd);
        newSolution.flipBit(rnd);

        return newSolution;
    }

}
