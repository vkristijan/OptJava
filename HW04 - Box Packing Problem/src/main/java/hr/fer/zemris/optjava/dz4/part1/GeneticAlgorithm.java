package hr.fer.zemris.optjava.dz4.part1;

import hr.fer.zemris.optjava.dz4.part1.chromosome.Chromosome;
import hr.fer.zemris.optjava.dz4.part1.crossover.BLXCrossover;
import hr.fer.zemris.optjava.dz4.part1.crossover.ICrossover;
import hr.fer.zemris.optjava.dz4.part1.fitness.FitnessFunction;
import hr.fer.zemris.optjava.dz4.part1.fitness.IFitnessFunction;
import hr.fer.zemris.optjava.dz4.part1.mutation.IMutation;
import hr.fer.zemris.optjava.dz4.part1.mutation.NormalMutation;
import hr.fer.zemris.optjava.dz4.part1.selection.ISelection;
import hr.fer.zemris.optjava.dz4.part1.selection.RouletteSelection;
import hr.fer.zemris.optjava.dz4.part1.selection.TournamentSelection;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Suggested parameters:
 * 200 0.1 1000 tournament:10 0.02
 *
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class GeneticAlgorithm {
    private static int populationSize;
    private static double maxError;
    private static int maxGenerations;
    private static ISelection selection;
    private static double mutationRate;
    private static Path filePath;
    private static IFitnessFunction fitnessFunction;
    private static ICrossover crossover;
    private static double alpha = 0.45;
    private static IMutation mutation;

    private static final int NUMBER_OF_VARIABLES = 6;
    private static final String DEFAULT_FILE_PATH = "zad-prijenosna.txt";

    public static void main(String[] args) {
        if (args.length < 5 || args.length > 6){
            System.err.println("Invalid number of arguments!");
            System.exit(1);
        }

        populationSize = Integer.parseInt(args[0]);
        maxError = Double.parseDouble(args[1]);
        maxGenerations = Integer.parseInt(args[2]);

        if (args[3].equals("rouletteWheel")){
            selection = new RouletteSelection();
        } else if (args[3].startsWith("tournament:")){
            int n = Integer.parseInt(args[3].substring(11));
            selection = new TournamentSelection(n);
        }

        mutationRate = Double.parseDouble(args[4]);

        if (args.length == 6){
            filePath = Paths.get(args[5]);
        } else {
            filePath = Paths.get(DEFAULT_FILE_PATH);
        }

        List<String> lines = null;

        try {
            lines = Files.readAllLines(filePath);
        } catch (IOException e) {
            System.err.println("Unable to open the given file or file not found!");
            System.exit(1);
        }

        fitnessFunction = new FitnessFunction(lines);
        crossover = new BLXCrossover(alpha);
        mutation = new NormalMutation(mutationRate);

        run();
    }

    private static void run() {
        int generation = 0;
        double currentError = Double.MAX_VALUE;

        List<Chromosome> population = initialPopulation();

        while (currentError > maxError && generation < maxGenerations){
            generation++;
            population.sort(Comparator.naturalOrder());
            currentError = population.get(0).getFitness();
            System.out.print(population.get(0));
            System.out.println("   -> error: " + currentError);

            List<Chromosome> newPopulation = new ArrayList<>();
            newPopulation.add(population.get(0));
            newPopulation.add(population.get(1));

            while (newPopulation.size() < populationSize){
                Chromosome a = selection.select(population);
                Chromosome b = selection.select(population);

                Chromosome child = crossover.crossover(a, b);
                mutation.mutate(child);
                child.calculateFitness();

                newPopulation.add(child);
            }

            population = newPopulation;
        }

        population.sort(Comparator.naturalOrder());
        currentError = population.get(0).getFitness();
        System.out.print(population.get(0));
        System.out.println("   -> error: " + currentError);
    }

    private static List<Chromosome> initialPopulation() {
        List<Chromosome> population = new ArrayList<>();

        for (int i = 0; i < populationSize; ++i){
            population.add(new Chromosome(NUMBER_OF_VARIABLES ,fitnessFunction));
        }

        return population;
    }
}
