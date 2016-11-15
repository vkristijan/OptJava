package hr.fer.zemris.optjava.dz5.part1.fitness;

import hr.fer.zemris.optjava.dz5.part1.chromosome.Chromosome;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class FitnessFunction {
    private static final double CONSTANT_START = 0.8;
    private static final double CONSTANT_END = 0.9;

    public double fitness(Chromosome chromosome){
        int n = chromosome.size();
        double k = chromosome.getActive();

        if (k <= CONSTANT_START * n){
            return k/n;
        }
        if (k <= CONSTANT_END * n){
            return CONSTANT_START;
        }

        return (2 * k / n) - 1;
    }
}
