package hr.fer.zemris.optjava.dz8;

import hr.fer.zemris.optjava.dz8.ann.ANN;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class DE {
    private static Random rnd = new Random();

    private ANN ann;
    private double maxError;
    private int populationSize;
    private int maxIter;
    private double F;
    private double cr;

    private double lowerBound;
    private double upperBound;

    private DoubleArraySolution best;

    public DE(ANN ann, double maxError, int populationSize, int maxIter, double f,
              double lowerBound, double upperBound, double cr) {
        this.ann = ann;
        this.maxError = maxError;
        this.populationSize = populationSize;
        this.maxIter = maxIter;
        F = f;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.cr = cr;
    }

    public void run(){
        List<DoubleArraySolution> population = createPopulation();

        int iteration = 0;
        while (iteration < maxIter && best.getError(ann) > maxError){
            List<DoubleArraySolution> newPopulation = new ArrayList<>();

            for (DoubleArraySolution target : population){
                DoubleArraySolution base = best; //population.get(rnd.nextInt(populationSize));
                DoubleArraySolution x1 = population.get(rnd.nextInt(populationSize));
                DoubleArraySolution x2 = population.get(rnd.nextInt(populationSize));

                DoubleArraySolution mutant = base.Clone();
                mutant.add(x1, x2, F);
                mutant.crossover(target, ann, cr);
                mutant.normalize(lowerBound, upperBound);

                if (target.getError(ann) > mutant.getError(ann)){
                    newPopulation.add(mutant);
                    if (mutant.getError(ann) < best.getError(ann)){
                        best = mutant;
                    }
                } else {
                    newPopulation.add(target);
                }
            }

            population = newPopulation;
            iteration++;

            System.out.println(iteration + ": " + best.getError(ann));
        }
    }

    private List<DoubleArraySolution> createPopulation() {
        List<DoubleArraySolution> population = new ArrayList<>();

        int n = ann.getWeightsCount();
        for (int i = 0; i < populationSize; ++i){
            DoubleArraySolution newSol = new DoubleArraySolution(n);
            population.add(newSol);

            if (best == null || best.getError(ann) > newSol.getError(ann)){
                best = newSol;
            }
        }

        return population;
    }
}
