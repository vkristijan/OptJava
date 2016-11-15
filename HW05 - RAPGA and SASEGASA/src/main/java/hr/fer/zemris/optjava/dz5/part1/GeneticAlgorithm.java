package hr.fer.zemris.optjava.dz5.part1;

import hr.fer.zemris.optjava.dz5.part1.chromosome.Chromosome;
import hr.fer.zemris.optjava.dz5.part1.compFactor.CompFactor;
import hr.fer.zemris.optjava.dz5.part1.crossover.Crossover;
import hr.fer.zemris.optjava.dz5.part1.fitness.FitnessFunction;
import hr.fer.zemris.optjava.dz5.part1.mutation.Mutation;
import hr.fer.zemris.optjava.dz5.part1.selection.ISelection;
import hr.fer.zemris.optjava.dz5.part1.selection.RandomSelection;
import hr.fer.zemris.optjava.dz5.part1.selection.TournamentSelection;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class GeneticAlgorithm {
    private static final int MIN_POPULATION = 50;
    private static final int MAX_POPULATION = 200;
    private static final int INIT_POPULATION = 100;
    private static final int MAX_EFFORT = 10_000;
    private static final int MAX_GENERATIONS = 10000;
    private static final double PRESSURE = 5.0 / MAX_GENERATIONS;

    private static final double MUTATION_CHANCE = 0.01;
    private static final int TOURNAMENT_SIZE = 10;

    private static FitnessFunction fitnessFunction;
    private static ISelection firstSelection;
    private static ISelection secondSelection;
    private static Crossover crossover;
    private static Mutation mutation;
    private static CompFactor compFactor;

    private static int variableCount;

    public static void main(String[] args) {
        if (args.length != 1){
            System.err.println("There is exactly 1 argument expected!");
            System.exit(1);
        }

        variableCount = Integer.parseInt(args[0]);
        fitnessFunction = new FitnessFunction();
        firstSelection = new TournamentSelection(TOURNAMENT_SIZE, fitnessFunction);
        secondSelection = new RandomSelection();
        crossover = new Crossover();
        mutation = new Mutation(MUTATION_CHANCE);
        compFactor = new CompFactor(PRESSURE);

        run();
    }

    private static void run(){
        Set<Chromosome> population = initialPopulation();

        for (int i = 0; i < MAX_GENERATIONS; ++i){
            Chromosome best = getBest(population);
            if (i % 10 == 0) {
                System.out.println(fitnessFunction.fitness(best) + "  -  " + population.size() + " : " + best);
            }
            if (fitnessFunction.fitness(best) == 1) break;

            compFactor.nextCompFactor();
            Set<Chromosome> newPopulation = new HashSet<>();
            newPopulation.add(best);
            Set<Chromosome> badPopulation = new HashSet<>();

            int counter = 0;
            while (newPopulation.size() < MAX_POPULATION && counter < MAX_EFFORT){
                counter++;

                Chromosome firstParrent = firstSelection.select(population);
                Chromosome secondParrent = secondSelection.select(population);

                Chromosome child = crossover.crossover(firstParrent, secondParrent);
                mutation.mutate(child);

                double f1 = fitnessFunction.fitness(firstParrent);
                double f2 = fitnessFunction.fitness(secondParrent);
                if (f1 > f2){
                    double tmp = f1;
                    f1 = f2;
                    f2 = tmp;
                }

                double f = fitnessFunction.fitness(child);
                if (f > f1 + compFactor.getCompFactor() * (f2 - f1)){
                    newPopulation.add(child);
                } else {
                    if (badPopulation.size() < MIN_POPULATION){
                        badPopulation.add(child);
                    }
                }
            }

            population = newPopulation;
            if (population.size() < MIN_POPULATION){
                population.addAll(badPopulation);
            }
        }

        Chromosome best = getBest(population);
        System.out.println(fitnessFunction.fitness(best) + "  -  " + best);
    }

    private static Chromosome getBest(Set<Chromosome> population) {
        double bestFitness = -1;
        Chromosome best = null;

        for (Chromosome chromosome : population){
            double fitness = fitnessFunction.fitness(chromosome);
            if (fitness > bestFitness){
                bestFitness = fitness;
                best = chromosome;
            }
        }

        return best;
    }

    private static Set<Chromosome> initialPopulation() {
        Set<Chromosome> population = new HashSet<>();

        while(population.size() < INIT_POPULATION){
            population.add(new Chromosome(variableCount));
        }

        return population;
    }
}
