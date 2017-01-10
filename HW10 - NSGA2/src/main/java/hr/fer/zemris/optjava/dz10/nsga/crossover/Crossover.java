package hr.fer.zemris.optjava.dz10.nsga.crossover;

import hr.fer.zemris.optjava.dz10.nsga.Chromosome;

import java.util.Random;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Crossover implements ICrossover {
    private static final Random rnd = new Random();

    @Override
    public Chromosome crossover(Chromosome p1, Chromosome p2) {
        Chromosome child = new Chromosome(p1.getSolutionSize(), p1.getObjectiveSize());

        for (int i = 0; i < p1.getSolutionSize(); ++i){
            double sol1 = p1.getSolution(i);
            double sol2 = p2.getSolution(i);

            if (sol1 > sol2){
                double tmp = sol1;
                sol1 = sol2;
                sol2 = tmp;
            }

            double sol = rnd.nextDouble() * (sol2 - sol1) + sol1;
            child.setSolution(i, sol);
        }

        return child;
    }
}
