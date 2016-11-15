package hr.fer.zemris.optjava.dz5.part1.selection;

import hr.fer.zemris.optjava.dz5.part1.chromosome.Chromosome;

import java.util.Set;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public interface ISelection {
    Chromosome select(Set<Chromosome> population);
}
