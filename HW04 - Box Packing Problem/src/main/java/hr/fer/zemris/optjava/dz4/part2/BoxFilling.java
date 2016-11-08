package hr.fer.zemris.optjava.dz4.part2;

import hr.fer.zemris.optjava.dz4.part2.chromosome.Box;
import hr.fer.zemris.optjava.dz4.part2.chromosome.Chromosome;
import hr.fer.zemris.optjava.dz4.part2.chromosome.Stick;
import hr.fer.zemris.optjava.dz4.part2.crossover.Crossover;
import hr.fer.zemris.optjava.dz4.part2.fitness.FitnessFunction;
import hr.fer.zemris.optjava.dz4.part2.mutation.Mutation;
import hr.fer.zemris.optjava.dz4.part2.selection.TournamentSelection;
import hr.fer.zemris.optjava.dz4.part2.selection.TournamentWorstSelection;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class BoxFilling {
    private static Path filePath;
    private static int populationSize;
    private static int n;
    private static int m;
    private static boolean p;
    private static int maxIteration;
    private static int acceptableSolution;
    /**
     * Determines the probability that a particular box will mutate.
     * The value should be less than 1.
     */
    private static double mutatonChance;
    /**
     * Used to calculate the fitness function. The value should be greater than 1.
     * Using 2 will do fine.
     */
    private static double capacityConstant;

    private static final int boxHeight = 20;


    public static void main(String[] args) {
        if (args.length != 9){
            throw new IllegalArgumentException("Expected 9 arguments");
        }

        filePath = Paths.get(args[0]);
        populationSize = Integer.parseInt(args[1]);
        n = Integer.parseInt(args[2]);
        m = Integer.parseInt(args[3]);
        p = Boolean.parseBoolean(args[4]);
        maxIteration = Integer.parseInt(args[5]);
        acceptableSolution = Integer.parseInt(args[6]);
        mutatonChance = Double.parseDouble(args[7]);
        capacityConstant = Double.parseDouble(args[8]);

        List<Stick> sticks = readFile(filePath);
        Crossover crossover = new Crossover();
        FitnessFunction fitness = new FitnessFunction(capacityConstant);
        Mutation mutation = new Mutation(mutatonChance);
        TournamentSelection selection = new TournamentSelection(n, fitness);
        TournamentWorstSelection worstSelection = new TournamentWorstSelection(m, fitness);

        run(sticks, crossover, fitness, mutation, selection, worstSelection);
    }

    private static void run(List<Stick> sticks, Crossover crossover, FitnessFunction fitness, Mutation mutation,
                            TournamentSelection selection, TournamentWorstSelection worstSelection) {

        List<Chromosome> population = initialPopulation(sticks);
        Chromosome best = null;
        double bestFitness = 0;

        for (Chromosome chromosome : population){
            double tmpFitness = fitness.fitness(chromosome);
            if (tmpFitness > bestFitness){
                bestFitness = tmpFitness;
                best = chromosome;
            }
        }
        System.out.println(best.getBoxes().size() + " boxes  - " + best + "  -> fitness: " + bestFitness);

        int iteration = 0;
        while (iteration < maxIteration && best.getBoxes().size() > acceptableSolution){
            iteration++;

            Chromosome parrent1 = selection.select(population);
            Chromosome parrent2 = selection.select(population);

            Chromosome child = crossover.crossover(parrent1, parrent2);
            mutation.mutate(child);

            double childFitness = fitness.fitness(child);
            Chromosome old = worstSelection.select(population);
            if (!p){
                population.remove(old);
                population.add(child);
            } else {
                double oldFitness = fitness.fitness(old);
                if (oldFitness < childFitness){
                    population.remove(old);
                    population.add(child);
                }
            }

            if (childFitness > bestFitness){
                bestFitness = childFitness;
                best = child;
                System.out.println(best.getBoxes().size() + " boxes  - " + best + "  -> fitness: " + bestFitness);
            }
        }
    }

    private static List<Chromosome> initialPopulation(List<Stick> sticks) {
        List<Chromosome> population = new ArrayList<>();
        //I would normally create a chromosome this way, but I would get the optimal soultion
        //in the initial generation. Because of that I created a worse population to test the GA.
        //population.add(new Chromosome(boxHeight, sticks));

        Random rnd = new Random();
        while (population.size() < populationSize){
            Collections.shuffle(sticks, rnd);
            List<Box> boxes = new ArrayList<>();
            Box box = new Box(boxHeight);
            for (Stick stick : sticks){
                if (!box.addStick(stick)){
                    boxes.add(box);
                    box = new Box(boxHeight);
                    box.addStick(stick);
                }
            }
            boxes.add(box);
            //population.add(new Chromosome(boxHeight, sticks));
            population.add(new Chromosome(boxes));
        }

        return population;
    }

    private static List<Stick> readFile(Path filePath) {
        List<Stick> sticks = new ArrayList<>();

        String input = null;
        try {
            input = Files.readAllLines(filePath).get(0);
        } catch (IOException e) {
            System.err.println("Unable to open file!");
            System.exit(1);
        }

        input = input.substring(1, input.length() - 1);
        String[] sticksData = input.split(", ");

        for (String stickData : sticksData){
            Stick stick = new Stick(Integer.parseInt(stickData));
            sticks.add(stick);
        }

        return sticks;
    }
}
