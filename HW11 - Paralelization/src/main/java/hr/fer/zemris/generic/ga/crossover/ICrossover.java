package hr.fer.zemris.generic.ga.crossover;

import hr.fer.zemris.generic.ga.GASolution;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public interface ICrossover {
    GASolution<int[]> crossover(GASolution<int[]> a, GASolution<int[]> b);
}
