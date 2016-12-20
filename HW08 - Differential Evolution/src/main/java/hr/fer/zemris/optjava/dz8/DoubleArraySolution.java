package hr.fer.zemris.optjava.dz8;

import hr.fer.zemris.optjava.dz8.ann.ANN;

import java.util.Random;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class DoubleArraySolution {
    private static Random rnd = new Random();
    private double[] data;
    private double error;
    private boolean hasError = false;

    public DoubleArraySolution(int n){
        data = new double[n];

        for (int i = 0; i < n; ++i){
            data[i] = rnd.nextDouble() * 2 - 1;
        }
    }

    private DoubleArraySolution(DoubleArraySolution other){
        this.data = new double[other.data.length];

        System.arraycopy(other.data, 0, this.data, 0, other.data.length);
    }

    public DoubleArraySolution Clone(){
        return new DoubleArraySolution(this);
    }

    public double[] getData(){
        return data;
    }

    public double getError(ANN ann){
        if (hasError){
            return error;
        }
        error = ann.calcError(data);
        return error;
    }

    public void add(DoubleArraySolution x1, DoubleArraySolution x2, double F){
        hasError = false;

        for (int i = 0; i < this.data.length; ++i){
            double x = F * (x1.data[i] - x2.data[i]);
            this.data[i] += x;
        }
    }

    public void crossover(DoubleArraySolution target, ANN ann, double cr){
        double err1 = this.getError(ann);
        double err2 = target.getError(ann);

        double rate = err2 / (err1 + err2);

        hasError = false;
        int mustTake = rnd.nextInt(data.length);
        for (int i = 0; i < this.data.length; ++i){
            if (rnd.nextDouble() > cr && i != mustTake){
                this.data[i] = target.data[i];
            }
        }
    }

    public void normalize(double lowerBound, double upperBound) {
        hasError = false;

        for (int i = 0; i < this.data.length; ++i){
            if (data[i] < lowerBound){
                data[i] = lowerBound;
            }

            if (data[i] > upperBound){
                data[i] = upperBound;
            }
        }
    }
}
