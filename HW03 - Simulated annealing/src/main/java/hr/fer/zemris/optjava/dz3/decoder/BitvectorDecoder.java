package hr.fer.zemris.optjava.dz3.decoder;

import hr.fer.zemris.optjava.dz3.solution.BitvectorSolution;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public abstract class BitvectorDecoder implements IDecoder<BitvectorSolution> {
    protected double[] mins;
    protected double[] maxs;

    protected int[] bits;
    protected int n;
    protected int totalBits;

    public BitvectorDecoder(double[] mins, double[] maxs, int[] bits, int n) {
        this.mins = mins;
        this.maxs = maxs;
        this.bits = bits;
        this.n = n;

        totalBits = 0;
        for (int i = 0; i < n; ++i){
            totalBits += bits[i];
        }
    }

    public BitvectorDecoder(double min, double max, int bits, int n){
        this.mins = new double[n];
        this.maxs = new double[n];
        this.bits = new int[n];
        this.n = n;

        for (int i = 0; i < n; ++i){
            mins[i] = min;
            maxs[i] = max;
            this.bits[i] = bits;
        }

        totalBits = n * bits;
    }

    public int getTotalBits(){
        return totalBits;
    }

    public int getDimensions(){
        return n;
    }

    public abstract double[] decode(BitvectorSolution solution);

    public void decode(BitvectorSolution solution, double[] answer){
        answer = decode(solution);
    };
}
