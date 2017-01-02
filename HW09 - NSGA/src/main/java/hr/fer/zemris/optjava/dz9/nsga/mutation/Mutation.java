package hr.fer.zemris.optjava.dz9.nsga.mutation;

import hr.fer.zemris.optjava.dz9.nsga.Chromosome;

import java.util.Random;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Mutation implements IMutation {
    private static final Random rnd = new Random();
    private final double sigma;

    public Mutation(double sigma){
        this.sigma = sigma;
    }

    @Override
    public void mutate(Chromosome chromosome) {
        for (int i = 0; i < chromosome.getSolutionSize(); ++i){
            double solution = chromosome.getSolution(i);
            solution += rnd.nextGaussian() * sigma;

            chromosome.setSolution(i, solution);
        }
    }
}
