package hr.fer.zemris.optjava.dz5.part1.crossover;

import hr.fer.zemris.optjava.dz5.part1.chromosome.Chromosome;

import java.util.Random;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Crossover {
    private static final Random rnd = new Random();

    public Chromosome crossover(Chromosome a, Chromosome b){
        int n = a.size();

        int firstPoint = rnd.nextInt(n);
        int secondPoint = rnd.nextInt(n);

        if (secondPoint < firstPoint){
            int tmp = firstPoint;
            firstPoint = secondPoint;
            secondPoint = tmp;
        }

        Chromosome child = new Chromosome(a);

        for (int i = firstPoint; i <= secondPoint; ++i){
            child.setBit(i, b.getBit(i));
        }

        return child;
    }
}
