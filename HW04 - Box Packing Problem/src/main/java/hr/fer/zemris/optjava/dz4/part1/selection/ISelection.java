package hr.fer.zemris.optjava.dz4.part1.selection;

import hr.fer.zemris.optjava.dz4.part1.chromosome.Chromosome;

import java.util.List;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public interface ISelection {
    Chromosome select(List<Chromosome> population);
}
