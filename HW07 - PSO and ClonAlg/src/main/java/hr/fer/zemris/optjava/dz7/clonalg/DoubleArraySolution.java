package hr.fer.zemris.optjava.dz7.clonalg;

import hr.fer.zemris.optjava.dz7.ann.FFANN;

import java.util.Random;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class DoubleArraySolution implements Comparable<DoubleArraySolution> {
    private static final Random rnd = new Random();

    private final FFANN ffann;

    private double[] values;
    private int size;
    private double fitness;
    private boolean evaluated;
    private int age;

    private DoubleArraySolution(DoubleArraySolution other){
        this.ffann = other.ffann;
        this.values = new double[other.values.length];
        this.size = other.size;

        System.arraycopy(other.values, 0, this.values, 0, other.values.length);
        this.evaluated = false;
        this.age = 0;
    }

    public DoubleArraySolution(int n, double min, double max, FFANN ffann){
        this.ffann = ffann;
        this.size = n;
        values = new double[n];

        for (int i = 0; i < n; ++i){
            double value = rnd.nextDouble() * (max - min) + min;
            values[i] = value;
        }
        this.evaluated = false;
        this.age = 0;
    }

    public double[] getValues(){
        return values;
    }

    public double getValue(int index){
        return values[index];
    }

    public void setValue(int index, double value){
        values[index] = value;
        this.evaluated = false;
    }

    public DoubleArraySolution clone(){
        return new DoubleArraySolution(this);
    }

    public int getSize(){
        return size;
    }

    public double getFitness(){
        if (evaluated == true){
            return fitness;
        }

        evaluated = true;
        fitness = ffann.calcError(values);
        return fitness;
    }

    public void getOlder(){
        age++;
    }

    public int getAge(){
        return age;
    }

    @Override
    public int compareTo(DoubleArraySolution o) {
        return Double.compare(this.getFitness(), o.getFitness());
    }
}
