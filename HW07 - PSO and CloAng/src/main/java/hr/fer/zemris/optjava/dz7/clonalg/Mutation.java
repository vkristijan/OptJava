package hr.fer.zemris.optjava.dz7.clonalg;

import java.util.Random;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Mutation {
    private static final Random rnd = new Random();

    public DoubleArraySolution mutate(DoubleArraySolution solution, double mutationRate){
        DoubleArraySolution other = solution.clone();

        for (int i = 0; i < other.getSize(); ++i){
            double value = other.getValue(i);
            value += (2*rnd.nextDouble() - 1) * mutationRate;
            other.setValue(i, value);
        }

        return other;
    }
}
