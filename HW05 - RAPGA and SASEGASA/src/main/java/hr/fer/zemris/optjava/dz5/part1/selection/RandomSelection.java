package hr.fer.zemris.optjava.dz5.part1.selection;

import hr.fer.zemris.optjava.dz5.part1.chromosome.Chromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class RandomSelection implements ISelection{
    private static final Random rnd = new Random();

    @Override
    public Chromosome select(Set<Chromosome> population) {
        List<Chromosome> populationList = new ArrayList<>(population);
        int n = population.size();

        int index = rnd.nextInt(n);
        return populationList.get(index);
    }
}
