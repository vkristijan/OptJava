package hr.fer.zemris.generic.ga.mutation;

import hr.fer.zemris.generic.ga.GASolution;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public interface IMutation {
    void mutate(GASolution<int[]> solution);
}
