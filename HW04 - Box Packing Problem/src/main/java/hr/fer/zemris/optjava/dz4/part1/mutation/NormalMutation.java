package hr.fer.zemris.optjava.dz4.part1.mutation;

import hr.fer.zemris.optjava.dz4.part1.chromosome.Chromosome;

import java.util.Random;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class NormalMutation implements IMutation {
    private static final Random rnd = new Random();
    private final double mutationRate;

    public NormalMutation(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    @Override
    public void mutate(Chromosome x) {
        for (int i = 0; i < x.numberOFValues(); ++i){
            double oldValue = x.getValue(i);
            double change = rnd.nextGaussian() * mutationRate;

            x.setValue(i, oldValue + change);
        }
        x.calculateFitness();
    }
}
