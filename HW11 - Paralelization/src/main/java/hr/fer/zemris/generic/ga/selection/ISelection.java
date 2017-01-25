package hr.fer.zemris.generic.ga.selection;

import hr.fer.zemris.generic.ga.GASolution;

import java.util.List;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public interface ISelection {
    GASolution<int[]> select(List<GASolution<int[]>> population);
}
