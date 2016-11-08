package hr.fer.zemris.optjava.dz4.part1.crossover;

import hr.fer.zemris.optjava.dz4.part1.chromosome.Chromosome;

import java.util.Random;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class BLXCrossover implements ICrossover {
    private static final Random rnd = new Random();
    private final double alpha;

    public BLXCrossover(double alpha) {
        this.alpha = alpha;
    }

    @Override
    public Chromosome crossover(Chromosome a, Chromosome b) {
        Chromosome newChromosome = a.clone();
        int n = newChromosome.numberOFValues();

        for (int i = 0; i < n; ++i){
            double cmin = Math.min(a.getValue(i), b.getValue(i));
            double cmax = Math.max(a.getValue(i), b.getValue(i));

            double intervalLength = (cmax - cmin) * alpha;
            double newValue = rnd.nextDouble() * (cmax + 2 * intervalLength - cmin) + cmin - intervalLength;
            newChromosome.setValue(i, newValue);
        }

        newChromosome.calculateFitness();
        return newChromosome;
    }
}
