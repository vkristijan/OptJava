package hr.fer.zemris.trisat.algorithms;

import hr.fer.zemris.trisat.SATFormula;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public abstract class TriSATAlgorithm {
    protected SATFormula formula;

    public TriSATAlgorithm(SATFormula formula) {
        this.formula = formula;
    }

    public abstract void solve();
}
