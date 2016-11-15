package hr.fer.zemris.optjava.dz5.part2;

import hr.fer.zemris.optjava.dz5.part2.chromosome.Chromosome;
import hr.fer.zemris.optjava.dz5.part2.compFactor.CompFactor;
import hr.fer.zemris.optjava.dz5.part2.crossover.Crossover;
import hr.fer.zemris.optjava.dz5.part2.fitness.Fitness;
import hr.fer.zemris.optjava.dz5.part2.mutation.Mutation;
import hr.fer.zemris.optjava.dz5.part2.selection.ISelection;
import hr.fer.zemris.optjava.dz5.part2.selection.TournamentSelection;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class GeneticAlgorithm {
    private static Random rnd = new Random();

    private static Path filePath;
    /**
     * The size of the population. A value of 100 will do fine.
     */
    private static int populationSize;
    /**
     * The number of smaller populations created from the starting population. A number of 50 will do fine.
     */
    private static int separationCount;
    private static int n;
    private static int tournamentSize = 3;

    private static double successRatio = 0.4;
    private static double maxSelectionPressure = 2000;
    private static double pressure = 0.0005;

    private static Fitness fitnessFunction;
    private static ISelection firstSelection;
    private static ISelection secondSelection;
    private static Crossover crossover;
    private static Mutation mutation;


    public static void main(String[] args) {
        if (args.length != 3){
            System.err.println("Expected 3 arguments!");
            System.exit(1);
        }

        filePath = Paths.get(args[0]);
        populationSize = Integer.parseInt(args[1]);
        separationCount = Integer.parseInt(args[2]);

        readFile();
        firstSelection = new TournamentSelection(tournamentSize);
        secondSelection = new TournamentSelection(tournamentSize);
        crossover = new Crossover();
        mutation = new Mutation();

        run();
    }

    private static void readFile() {
        int[][] dist;
        int[][] price;

        try {
            Scanner sc = new Scanner(Files.newInputStream(filePath));

            n = sc.nextInt();
            dist = new int[n][n];
            price = new int[n][n];

            for (int i = 0; i < n; ++i){
                for (int j = 0; j < n; ++j){
                    dist[i][j] = sc.nextInt();
                }
            }

            for (int i = 0; i < n; ++i){
                for (int j = 0; j < n; ++j){
                    price[i][j] = sc.nextInt();
                }
            }

            fitnessFunction = new Fitness(n, dist, price);
        } catch (IOException e) {
            System.err.println("Unable to read from the given file!");
            System.exit(1);
        }
    }

    private static void run() {
        List<Chromosome> population = initialPopulation();

        while (separationCount > 0){
            List<Chromosome> newPopulation = new ArrayList<>();

            for (int i = 0; i < separationCount; ++i){
                int begin = i * populationSize / separationCount;
                int end = (i + 1) * populationSize / separationCount;

                if (end > populationSize || i == separationCount - 1){
                    end = populationSize;
                }

                List<Chromosome> currentPopulation = new ArrayList<>();
                for (int j = begin; j < end; ++j){
                    currentPopulation.add(population.get(j));
                }

                currentPopulation = run(currentPopulation);
                newPopulation.addAll(currentPopulation);
            }

            System.out.println("-------");
            population = newPopulation;
            separationCount--;
        }

        Chromosome best = bestOfPopulation(population);
        System.out.println(best);
    }

    private static int maxIter = 10000;
    private static List<Chromosome> run(List<Chromosome> population) {
        int poolSize;
        double selectionPressure = 0;

        CompFactor compFactor = new CompFactor(pressure);
        while (selectionPressure < maxSelectionPressure && compFactor.getCompFactor() < 1){
            compFactor.nextCompFactor();
            Set<Chromosome> newPopulation = new HashSet<>();
            newPopulation.add(bestOfPopulation(population));
            Set<Chromosome> pool = new HashSet<>();

            int expectedSize = (int)(population.size() * successRatio);
            int maxPool = population.size() - expectedSize - 1;
            poolSize = 0;
            while (selectionPressure < maxSelectionPressure && newPopulation.size() < expectedSize){
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
                if (f < f2 - compFactor.getCompFactor() * (f2 - f1)){
                    newPopulation.add(child);
                } else {
                    if (pool.size() < maxPool){
                        pool.add(child);
                    }
                    poolSize++;
                }
                selectionPressure = (double)poolSize / population.size();
            }

            newPopulation.addAll(pool);
            int iter = 0;
            while (newPopulation.size() < population.size() && iter < maxIter){
                iter++;
                Chromosome firstParrent = firstSelection.select(population);
                Chromosome secondParrent = secondSelection.select(population);

                Chromosome child = crossover.crossover(firstParrent, secondParrent);
                mutation.mutate(child);
                newPopulation.add(child);
            }

            if (newPopulation.size() < population.size()){
                expectedSize = population.size();
                population = new ArrayList<>(newPopulation);

                while (population.size() < expectedSize){
                    population.add(population.get(rnd.nextInt(population.size())));
                }
                break;
            }

            population = new ArrayList<>(newPopulation);
        }

        Chromosome best = bestOfPopulation(population);
        System.out.println(best);
        return population;
    }

    private static Chromosome bestOfPopulation(List<Chromosome> population) {
        long bestFitness = Long.MAX_VALUE;
        Chromosome best = null;

        for (Chromosome chromosome : population){
            if (chromosome.getFitness() < bestFitness){
                bestFitness = chromosome.getFitness();
                best = chromosome;
            }
        }

        return best;
    }

    private static List<Chromosome> initialPopulation() {
        List<Chromosome> population = new ArrayList<>();

        while (population.size() < populationSize){
            population.add(new Chromosome(n, fitnessFunction));
        }

        return population;
    }


}
