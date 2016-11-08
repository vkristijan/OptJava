package hr.fer.zemris.optjava.dz4.part2.selection;


import hr.fer.zemris.optjava.dz4.part2.fitness.FitnessFunction;
import hr.fer.zemris.optjava.dz4.part2.chromosome.Chromosome;

import java.util.List;
import java.util.Random;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class TournamentWorstSelection {
    private static final Random rnd = new Random();
    private FitnessFunction fitnessFunction;
    private int n;

    public TournamentWorstSelection(int n, FitnessFunction fitnessFunction) {
        if (n < 2){
            throw new IllegalArgumentException("There must be at least 2 selections");
        }

        this.n = n;
        this.fitnessFunction = fitnessFunction;
    }

    public Chromosome select(List<Chromosome> population) {
        int size = population.size();
        int index = rnd.nextInt(size);
        Chromosome worstChromosome = population.get(index);
        double bestFitness = fitnessFunction.fitness(worstChromosome);

        for (int i = 0; i < n; ++i){
            index = rnd.nextInt(size);
            Chromosome tmpChromosome = population.get(index);

            double tmpFitness = fitnessFunction.fitness(tmpChromosome);
            if (tmpFitness < bestFitness){
                worstChromosome = tmpChromosome;
                bestFitness = tmpFitness;
            }
        }

        return worstChromosome;
    }
}
