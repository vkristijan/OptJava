package hr.fer.zemris.optjava.dz4.part1.selection;

import hr.fer.zemris.optjava.dz4.part1.chromosome.Chromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class TournamentSelection implements ISelection{
    private static final Random rnd = new Random();
    private int n;

    public TournamentSelection(int n) {
        if (n < 2){
            throw new IllegalArgumentException("There must be at least 2 selections");
        }

        this.n = n;
    }

    @Override
    public Chromosome select(List<Chromosome> population) {
        int size = population.size();
        int index = rnd.nextInt(size);
        Chromosome bestChromosome = population.get(index);
        double bestFitness = bestChromosome.getFitness();

        for (int i = 0; i < n; ++i){
            index = rnd.nextInt(size);
            Chromosome tmpChromosome = population.get(index);

            if (tmpChromosome.getFitness() < bestFitness){
                bestChromosome = tmpChromosome;
                bestFitness = bestChromosome.getFitness();
            }
        }

        return bestChromosome;
    }
}
