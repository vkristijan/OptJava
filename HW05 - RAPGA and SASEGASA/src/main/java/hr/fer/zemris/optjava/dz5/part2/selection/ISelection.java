package hr.fer.zemris.optjava.dz5.part2.selection;

import hr.fer.zemris.optjava.dz5.part2.chromosome.Chromosome;

import java.util.List;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public interface ISelection {
    Chromosome select(List<Chromosome> population);
}
