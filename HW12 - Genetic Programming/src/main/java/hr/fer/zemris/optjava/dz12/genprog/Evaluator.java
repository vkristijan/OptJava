package hr.fer.zemris.optjava.dz12.genprog;

import hr.fer.zemris.optjava.dz12.Map;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Evaluator {
    public int evaluate(Solution sol, Map map){
        Map mapCopy = map.copy();
        sol.getRoot().action(mapCopy, 0);
        int fitness = mapCopy.getFoodEaten();

        sol.setFitness(fitness);
        return fitness;
    }
}
