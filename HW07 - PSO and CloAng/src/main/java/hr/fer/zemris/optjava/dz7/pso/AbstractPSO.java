package hr.fer.zemris.optjava.dz7.pso;

import hr.fer.zemris.optjava.dz7.ann.Dataset;
import hr.fer.zemris.optjava.dz7.ann.FFANN;

import java.util.DoubleSummaryStatistics;
import java.util.Random;


/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public abstract class AbstractPSO {
    protected static final Random rnd = new Random();

    protected FFANN ffann;

    protected static final double c1 = 2.0;
    protected static final double c2 = 2.0;

    protected static final double xmin = -1.0;
    protected static final double xmax = 1.0;
    protected static final double vmin = -0.2;
    protected static final double vmax = 0.2;

    protected static final double wStart = 0.9;
    protected static final double wEnd = 0.4;

    protected final int maxIteration;
    protected final int populationSize;
    private final double maxError;

    protected double error = Double.POSITIVE_INFINITY;
    protected double[] best;

    protected double[] pError;
    protected double[][] pbest;

    protected double[][] particle;
    protected double[][] speed;


    public AbstractPSO(FFANN ffann, int maxIteration, int populationSize, double maxError) {
        this.ffann = ffann;
        this.maxIteration = maxIteration;
        this.populationSize = populationSize;
        this.maxError = maxError;

        int d = ffann.getWeightsCount();

        particle = new double[populationSize][d];
        pbest = new double[populationSize][d];
        pError = new double[populationSize];
        speed = new double[populationSize][d];
        best = new double[d];

        for (int i = 0; i < populationSize; ++i){
            pError[i] = Double.POSITIVE_INFINITY;
            for (int j = 0; j < d; ++j){
                particle[i][j] = rnd.nextDouble() * (xmax - xmin) + xmin;
                speed[i][j] = rnd.nextDouble() * (vmax - vmin) + vmin;
            }
        }
    }

    protected abstract void updateSpeed(double wCoef);

    public void run(){
        int d = ffann.getWeightsCount();

        int iteration = 0;
        while (iteration < maxIteration && error > maxError){
            iteration++;

            for (int i = 0; i < populationSize; ++i){
                double currentError = ffann.calcError(particle[i]);

                if (currentError < pError[i]){
                    pError[i] = currentError;
                    System.arraycopy(particle[i], 0, pbest[i], 0, d);
                }

                if (currentError < error){
                    error = currentError;
                    System.arraycopy(pbest[i], 0, best, 0, d);
                }

                //System.out.print(currentError + " ");
            }
            //System.out.println();

            updateSpeed((double) iteration / maxIteration );

            System.out.println(error);
            /*for (int i = 0; i < d; ++i){
                System.out.print(best[i] + " ");
            }
            System.out.println();*/
        }
    }
}
