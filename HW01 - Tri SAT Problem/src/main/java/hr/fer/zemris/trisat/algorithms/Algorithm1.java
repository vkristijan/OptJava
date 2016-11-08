package hr.fer.zemris.trisat.algorithms;

import hr.fer.zemris.trisat.MutableBitVector;
import hr.fer.zemris.trisat.SATFormula;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Algorithm1 extends TriSATAlgorithm {

    public Algorithm1(SATFormula formula) {
        super(formula);
    }

    @Override
    public void solve() {
        int numberOfVariables = formula.getNumberOfVariables();
        MutableBitVector solution = new MutableBitVector(numberOfVariables);

        int n = (int) Math.pow(2, numberOfVariables);
        for (int i = 0; i < n; ++i){
            if (i > 0){
                increment(solution);
            }

            if (formula.isSatisfied(solution)){
                System.out.println(solution);
            }
        }
    }

    private void increment(MutableBitVector vector){
        int index = 0;

        while (vector.get(index)){
            vector.set(index, false);
            index++;
        }

        vector.set(index, true);
    }
}
