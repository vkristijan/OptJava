package hr.fer.zemris.optjava.dz3.solution;

import java.util.Random;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class DoubleArraySolution extends SingleObjectiveSolution {
    private double[] values;

    public DoubleArraySolution(int size) {
        this.values = new double[size];
    }

    public DoubleArraySolution newLikeThis(){
        return new DoubleArraySolution(values.length);
    }

    public DoubleArraySolution duplicate(){
        int n = values.length;

        DoubleArraySolution other = new DoubleArraySolution(n);
        for (int i = 0; i < n; ++i){
            other.values[i] = this.values[i];
        }

        return other;
    }

    public void randomize(Random rnd, double[] mins, double[] maxs){
        int n = values.length;

        if (mins.length != n || maxs.length != n){
            throw new IllegalArgumentException("The given range definition is invalid.");
        }

        for (int i = 0; i < n; ++i){
            values[i] = rnd.nextDouble() * (maxs[i] - mins[i]) + mins[i];
        }
    }

    public double[] getValues(){
        return this.values;
    }

    public void changeValue(int i, double delta) {
        values[i] += delta;
    }
}
