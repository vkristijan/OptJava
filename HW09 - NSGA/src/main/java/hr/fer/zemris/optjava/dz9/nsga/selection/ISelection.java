package hr.fer.zemris.optjava.dz9.nsga.selection;

import hr.fer.zemris.optjava.dz9.nsga.Chromosome;

import java.util.List;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public interface ISelection {
    Chromosome select(List<Chromosome> population);
}
