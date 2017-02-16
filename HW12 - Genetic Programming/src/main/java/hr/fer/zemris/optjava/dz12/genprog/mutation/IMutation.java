package hr.fer.zemris.optjava.dz12.genprog.mutation;

import hr.fer.zemris.optjava.dz12.genprog.Solution;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public interface IMutation {
    Solution mutate(Solution solution);
}
