package hr.fer.zemris.optjava.dz9.nsga.moop;

import hr.fer.zemris.optjava.dz9.nsga.Chromosome;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Evaluate {

    private boolean isDominating(Chromosome c1, Chromosome c2){
        double[] objective1 = c1.getObjective();
        double[] objective2 = c2.getObjective();
        int n = objective1.length;

        for (int i = 0; i < n; ++i){
            if (objective1[i] > objective2[i]){
                return false;
            }
        }

        return true;
    }
}
