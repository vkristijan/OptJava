package hr.fer.zemris.optjava.dz10.nsga;

import hr.fer.zemris.optjava.dz10.nsga.crossover.ICrossover;
import hr.fer.zemris.optjava.dz10.nsga.moop.Evaluate;
import hr.fer.zemris.optjava.dz10.nsga.moop.MOOPProblem;
import hr.fer.zemris.optjava.dz10.nsga.mutation.IMutation;
import hr.fer.zemris.optjava.dz10.nsga.selection.ISelection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class NSGA2 {
    private MOOPProblem moop;
    private Evaluate evaluate;

    private int populationSize;
    private int maxIteration;

    private ISelection firstSelection;
    private ISelection secondSelection;
    private IMutation mutation;
    private ICrossover crossover;
    private double[] xmin;
    private double[] xmax;

    public NSGA2(MOOPProblem moop, Evaluate evaluate, int populationSize, int maxIteration, ISelection firstSelection, ISelection secondSelection, IMutation mutation, ICrossover crossover, double[] xmin, double[] xmax) {
        this.moop = moop;
        this.evaluate = evaluate;
        this.populationSize = populationSize;
        this.maxIteration = maxIteration;
        this.firstSelection = firstSelection;
        this.secondSelection = secondSelection;
        this.mutation = mutation;
        this.crossover = crossover;
        this.xmin = xmin;
        this.xmax = xmax;
    }

    public List<Chromosome> run(){
        List<Chromosome> population = generatePopulation();

        int iteration = 0;
        while (iteration < maxIteration){
            iteration++;

            List<Chromosome> newPopulation = new ArrayList<>();
            while (newPopulation.size() < populationSize){
                Chromosome firstParrent = firstSelection.select(population);
                Chromosome secondParrent = secondSelection.select(population);
                Chromosome child = crossover.crossover(firstParrent, secondParrent);
                mutation.mutate(child);

                newPopulation.add(child);
            }

            newPopulation.addAll(population);

            List<List<Chromosome>> fronts = evaluate.evaluate(newPopulation);
            population = new ArrayList<>();

            for (List<Chromosome> front : fronts){
                if (population.size() == populationSize) break;
                if (front.size() <= populationSize - population.size()){
                    population.addAll(front);
                    break;
                }

                front.sort(Comparator.comparingDouble(Chromosome::getDistance).reversed());
                int n = populationSize - population.size();
                for (int i = 0; i < n; ++i){
                    population.add(front.get(i));
                }
            }
        }


        return population;
    }

    private List<Chromosome> generatePopulation() {
        Random rnd = new Random();
        List<Chromosome> population = new ArrayList<>();

        int n = moop.solutionSize();
        for (int i = 0; i < populationSize; ++i){
            Chromosome chromosome = new Chromosome(moop.solutionSize(), moop.getNumberOfObjectives());
            for (int j = 0; j < n; ++j){
                double x = rnd.nextDouble() * (xmax[j] - xmin[j]) + xmin[j];
                chromosome.setSolution(j, x);
            }

            population.add(chromosome);
        }

        evaluate.evaluate(population);
        return population;
    }
}
