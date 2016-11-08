package hr.fer.zemris.optjava.dz3.neighbor;

import hr.fer.zemris.optjava.dz3.solution.DoubleArraySolution;

import java.util.Random;
import java.util.function.Supplier;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public abstract class DoubleArrayNeighborhood implements INeighborhood<DoubleArraySolution> {
    protected static Random rnd = new Random();

    private double[] deltas;

    public DoubleArrayNeighborhood(double[] deltas) {
        this.deltas = deltas;
    }

    protected DoubleArraySolution randomNeighbor(DoubleArraySolution current, Supplier<Double> suplier){
        int n = deltas.length;

        DoubleArraySolution neighbor = current.duplicate();
        for (int i = 0; i < n; ++i){
            double delta = (suplier.get() * 2 - 1) * deltas[i];
            neighbor.changeValue(i, delta);
        }

        return neighbor;
    }

    @Override
    public abstract DoubleArraySolution randomNeighbor(DoubleArraySolution current);
}
