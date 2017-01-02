package hr.fer.zemris.optjava.dz9.nsga.crossover;

import hr.fer.zemris.optjava.dz9.nsga.Chromosome;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public interface ICrossover {
    Chromosome crossover(Chromosome p1, Chromosome p2);
}
