package hr.fer.zemris.optjava.dz4.part1.selection;

import hr.fer.zemris.optjava.dz4.part1.chromosome.Chromosome;

import java.util.List;
import java.util.Random;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class RouletteSelection implements ISelection {
    private static final Random rnd = new Random();

    @Override
    public Chromosome select(List<Chromosome> population) {
        double fitnessSum = 0;
        double worstFitness = 0;
        for (Chromosome x : population){
            fitnessSum += x.getFitness();
            if (x.getFitness() > worstFitness){
                worstFitness = x.getFitness();
            }
        }
        fitnessSum = population.size() * worstFitness - fitnessSum;

        double roulette = rnd.nextDouble() * fitnessSum;
        double currentSum = 0;

        for (Chromosome x : population){
            currentSum += (worstFitness - x.getFitness());

            if (currentSum >= roulette){
                return x;
            }
        }

        return population.get(population.size() - 1);
    }
}
