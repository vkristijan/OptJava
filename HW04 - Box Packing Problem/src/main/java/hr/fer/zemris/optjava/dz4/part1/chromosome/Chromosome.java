package hr.fer.zemris.optjava.dz4.part1.chromosome;

import hr.fer.zemris.optjava.dz4.part1.fitness.IFitnessFunction;

import java.util.Random;
import java.util.StringJoiner;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Chromosome implements Comparable<Chromosome> {
    private static final double INITIAL_MIN = -10;
    private static final double INITIAL_MAX = 10;

    private IFitnessFunction fitnessFunction;

    private double[] values;
    private double fitness;

    public Chromosome(double[] values, IFitnessFunction fitnessFunction) {
        this.values = values;
        this.fitnessFunction = fitnessFunction;

        this.fitness = fitnessFunction.fitness(this);
    }

    public Chromosome(int n, IFitnessFunction fitnessFunction) {
        this.fitnessFunction = fitnessFunction;
        values = new double[n];

        Random rnd = new Random();
        for (int i = 0; i < n; ++i){
            values[i] = (rnd.nextDouble() * (INITIAL_MAX - INITIAL_MIN)) + INITIAL_MIN;
        }
        this.fitness = fitnessFunction.fitness(this);
    }

    public Chromosome clone(){
        Chromosome newChromosome = new Chromosome(values.length, fitnessFunction);
        System.arraycopy(values, 0, newChromosome.values, 0, values.length);
        newChromosome.fitness = this.fitness;

        return newChromosome;
    }

    public int numberOFValues(){
        return values.length;
    }

    public double getValue(int index){
        return values[index];
    }

    public void setValue(int index, double value){
        values[index] = value;
    }

    public double calculateFitness(){
        fitness = fitnessFunction.fitness(this);
        return fitness;
    }

    public double getFitness(){
        return fitness;
    }

    @Override
    public int compareTo(Chromosome o) {
        return Double.valueOf(fitness).compareTo(o.fitness);
    }

    @Override
    public String toString() {
        StringJoiner str = new StringJoiner(", ", "[", "]");

        for (double x : values){
            str.add(String.valueOf(x));
        }

        return str.toString();
    }
}
