package hr.fer.zemris.optjava.dz12.genprog.crossover;

import hr.fer.zemris.optjava.dz12.genprog.Solution;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public interface ICrossover {
    Solution crossover(Solution a, Solution b);
}
