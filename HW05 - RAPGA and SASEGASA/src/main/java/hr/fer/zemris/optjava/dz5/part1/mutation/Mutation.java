package hr.fer.zemris.optjava.dz5.part1.mutation;

import hr.fer.zemris.optjava.dz5.part1.chromosome.Chromosome;

import java.util.Random;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Mutation {
    private static final Random rnd = new Random();

    private final double mutationRate;

    public Mutation(double mutationRate){
        this.mutationRate = mutationRate;
    }

    public void mutate(Chromosome chromosome){
        int n = chromosome.size();

        for (int i = 0; i < n; ++i){
            if (rnd.nextDouble() < mutationRate){
                chromosome.flipBit(i);
            }
        }
    }
}
