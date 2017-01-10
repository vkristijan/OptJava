package hr.fer.zemris.optjava.dz10.nsga.mutation;

import hr.fer.zemris.optjava.dz10.nsga.Chromosome;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public interface IMutation {
    void mutate(Chromosome chromosome);
}
