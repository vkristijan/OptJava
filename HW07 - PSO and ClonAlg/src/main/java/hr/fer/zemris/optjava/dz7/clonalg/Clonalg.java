package hr.fer.zemris.optjava.dz7.clonalg;

import hr.fer.zemris.optjava.dz7.ann.FFANN;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Clonalg {
    private final FFANN ffann;
    private final int populationSize;
    private final int maxIteration;
    private final double maxError;

    private final int newBorn = 10;
    private final double beta = 5;
    private final double mutationFactor = 0.1;
    private final double xmin = -1;
    private final double xmax = 1;
    private final int maxAge = 10;

    private double bestError;
    private DoubleArraySolution best;

    private Mutation mutation;

    public Clonalg(FFANN ffann, int populationSize, int maxIteration, double maxError){
        this.ffann = ffann;
        this.populationSize = populationSize;
        this.maxIteration = maxIteration;
        this.maxError = maxError;
        this.bestError = Double.POSITIVE_INFINITY;

        this.mutation = new Mutation();
    }

    public void run(){
        int iteration = 0;
        List<DoubleArraySolution> population = new ArrayList<>();
        for (int i = 0; i < populationSize; ++i){
            population.add(new DoubleArraySolution(ffann.getWeightsCount(), xmin, xmax, ffann));
        }
        population.sort(Comparator.naturalOrder());

        while (iteration < maxIteration && bestError > maxError){
            if (population.get(0).getFitness() < bestError){
                bestError = population.get(0).getFitness();
                best = population.get(0);
            }

            List<DoubleArraySolution> clones = new ArrayList<>();
            int n = population.size();
            for (int i = 0; i < n; ++i){
                DoubleArraySolution tmp = population.get(i);
                int numberOfClones = (int)(beta * n / (i + 1));

                double mutationRate = 1-Math.exp(-mutationFactor / tmp.getFitness());
                for (int j = 0; j < numberOfClones; ++j){
                    clones.add(mutation.mutate(tmp, mutationRate));
                }
            }
            population.addAll(clones);

            population = population.stream()
                    .filter(x -> x.getAge() < maxAge)
                    .sorted()
                    .limit(populationSize)
                    .collect(Collectors.toList());

            population.forEach(DoubleArraySolution::getOlder);

            for (int i = 0; i < newBorn; ++i){
                population.add(new DoubleArraySolution(ffann.getWeightsCount(), xmin, xmax, ffann));
            }

            iteration++;
            System.out.println(iteration + ": " + bestError);
        }

    }
}
