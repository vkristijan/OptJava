package hr.fer.zemris.optjava.dz4.part1.crossover;

import hr.fer.zemris.optjava.dz4.part1.chromosome.Chromosome;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public interface ICrossover {
    Chromosome crossover(Chromosome a, Chromosome b);
}
