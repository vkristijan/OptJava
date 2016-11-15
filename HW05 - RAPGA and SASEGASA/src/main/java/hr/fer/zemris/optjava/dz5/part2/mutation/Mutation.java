package hr.fer.zemris.optjava.dz5.part2.mutation;

import hr.fer.zemris.optjava.dz5.part2.chromosome.Chromosome;

import java.util.Random;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Mutation {
    private static final Random rnd = new Random();

    public void mutate(Chromosome chromosome){
        int size = chromosome.getSize();

        int index1 = rnd.nextInt(size);
        int index2 = rnd.nextInt(size);

        int value1 = chromosome.getValue(index1);
        int value2 = chromosome.getValue(index2);

        chromosome.setValue(index1, value2);
        chromosome.setValue(index2, value1);

        chromosome.updateFitness();
    }
}
