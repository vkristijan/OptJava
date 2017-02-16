package hr.fer.zemris.optjava.dz12.genprog.selection;

import hr.fer.zemris.optjava.dz12.genprog.Solution;

import java.util.List;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public interface ISelection {
    Solution select(List<Solution> population);
}
