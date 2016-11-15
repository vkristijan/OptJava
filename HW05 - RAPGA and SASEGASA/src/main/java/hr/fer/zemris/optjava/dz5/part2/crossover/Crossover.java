package hr.fer.zemris.optjava.dz5.part2.crossover;

import hr.fer.zemris.optjava.dz5.part2.chromosome.Chromosome;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Crossover {
    private static final Random rnd = new Random();

    public Chromosome crossover(Chromosome a, Chromosome b){
        Chromosome child = new Chromosome(a);

        int size = child.getSize();
        int index = rnd.nextInt(size);

        Set<Integer> used = new HashSet<>();
        for (int i = 0; i < index; ++i){
            used.add(a.getValue(i));
        }

        int indexB = 0;
        for (int i = index; i < size; ++i){
            int value;
            if (used.contains(b.getValue(i))){
                while (used.contains(b.getValue(indexB))){
                    indexB++;
                }
                value = b.getValue(indexB);
                indexB++;
            } else {
                value = b.getValue(i);
            }

            used.add(value);
            child.setValue(i, value);
        }

        child.updateFitness();
        return child;
    }
}
