package hr.fer.zemris.generic.ga;

import hr.fer.zemris.art.GrayScaleImage;
import hr.fer.zemris.generic.ga.crossover.Crossover;
import hr.fer.zemris.generic.ga.crossover.ICrossover;
import hr.fer.zemris.generic.ga.mutation.IMutation;
import hr.fer.zemris.generic.ga.mutation.Mutation;
import hr.fer.zemris.generic.ga.mutation.SingleMutation;
import hr.fer.zemris.generic.ga.selection.ISelection;
import hr.fer.zemris.generic.ga.selection.TournamentSelection;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;
import hr.fer.zemris.thread.ThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Ga {
    private static int TOURNAMENT_SIZE = 5;
    private static double BACKGROUND_CHANGE = 0.2;
    private static double RECTANGLE_CHANGE = 0.01;
    private static int CHANGE_RATE = 5;
    private static double ACCEPTANCE_RATE = 0.3;

    private int maxIteration;
    private double minFitness;
    private int populationSize;
    private int numberOfRectangles;
    private int width;
    private int height;

    private ISelection firstSelection;
    private ISelection secondSelection;
    private ICrossover crossover;
    private IMutation mutation;
    private IGAEvaluator<int[]> evaluator;

    private BlockingQueue<GASolution<int[]>> tasks;
    private BlockingQueue<GASolution<int[]>> results;

    public Ga(int maxIteration, double minFitness, int populationSize, int numberOfRectangles, int width, int height,
              IGAEvaluator<int[]> evaluator) {
        this.maxIteration = maxIteration;
        this.minFitness = minFitness;
        this.populationSize = populationSize;
        this.numberOfRectangles = numberOfRectangles;
        this.width = width;
        this.height = height;
        this.evaluator = evaluator;

        firstSelection = new TournamentSelection(TOURNAMENT_SIZE);
        secondSelection = new TournamentSelection(TOURNAMENT_SIZE);
        crossover = new Crossover(ACCEPTANCE_RATE);
        mutation = new SingleMutation(BACKGROUND_CHANGE, CHANGE_RATE, width, height);

        tasks = new LinkedBlockingQueue<>();
        results = new LinkedBlockingQueue<>();
    }

    public GASolution<int[]> run() {
        int n = Runtime.getRuntime().availableProcessors();
        ThreadPool pool = new ThreadPool(n, tasks, results, evaluator);

        List<GASolution<int[]>> population = initPopulation();
        evaluate(population);
        GASolution<int[]> best = bestOdPopulation(population);

        int iteration = 0;
        while (iteration < maxIteration && best.fitness < minFitness) {
            iteration++;
            List<GASolution<int[]>> newPopulation = new ArrayList<>();
            newPopulation.add(best);

            for (int i = 1; i < populationSize; ++i){
                GASolution<int[]> a = firstSelection.select(population);
                GASolution<int[]> b = secondSelection.select(population);

                GASolution<int[]> child = crossover.crossover(a, b);
                mutation.mutate(child);
                newPopulation.add(child);
            }

            evaluate(newPopulation);
            population = newPopulation;
            best = bestOdPopulation(population);

            if (iteration % 10 == 0){
                System.out.println("Iteration: " + iteration + "  - " + best.fitness + " : " + average(population));
            }
        }

        pool.killAll();
        return best;
    }

    private int average(List<GASolution<int[]>> population) {
        int result = 0;
        for (GASolution<int[]> solution : population){
            result += solution.fitness;
        }

        return result / population.size();
    }

    private GASolution<int[]> bestOdPopulation(List<GASolution<int[]>> population) {
        GASolution<int[]> best = population.get(0);

        for (GASolution<int[]> solution : population){
            if (solution.fitness > best.fitness){
                best = solution;
            }
        }

        return best;
    }

    private void evaluate(List<GASolution<int[]>> population) {
        try {
            for (GASolution<int[]> solution : population) {
                tasks.put(solution);
            }

            for (int i = 0; i < populationSize; ++i){
                results.take();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private List<GASolution<int[]>> initPopulation() {
        IRNG rnd = RNG.getRNG();
        List<GASolution<int[]>> population = new ArrayList<>();

        int dataSize = 1 + 5 * numberOfRectangles;
        for (int i = 0; i < populationSize; ++i) {
            GASolution<int[]> solution = new GAIntArrSolution();
            int[] data = new int[dataSize];

            data[0] = rnd.nextInt(0, GrayScaleImage.SHADES_OF_GREY);
            int index = 1;
            for (int j = 0; j < numberOfRectangles; ++j) {
                data[index] = rnd.nextInt(0, width);
                data[index + 1] = rnd.nextInt(0, height);
                data[index + 2] = rnd.nextInt(0, 10);
                data[index + 3] = rnd.nextInt(0, 10);
                data[index + 4] = rnd.nextInt(0, GrayScaleImage.SHADES_OF_GREY);
                index += 5;
            }

            solution.setData(data);
            population.add(solution);
        }

        return population;
    }
}
