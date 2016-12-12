package hr.fer.zemris.optjava.dz7.pso;

import hr.fer.zemris.optjava.dz7.ann.FFANN;

import java.util.DoubleSummaryStatistics;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class LocalPSO extends AbstractPSO {
    private int neighbour;

    public LocalPSO(FFANN ffann, int maxIteration, int populationSize, double maxError, int neighbour) {
        super(ffann, maxIteration, populationSize, maxError);

        this.neighbour = neighbour;
    }

    @Override
    protected void updateSpeed(double wCoef) {
        int d = ffann.getWeightsCount();

        double w = wStart + wCoef * (wStart - wEnd);
        for (int i = 0; i < populationSize; ++i){
            double[] lbest = getLocalBest(i, neighbour);

            for (int j = 0; j < d; ++j){
                double x = particle[i][j];

                double tmp = speed[i][j] * w;
                tmp += c2 * rnd.nextDouble() * (lbest[j] - x);
                tmp += c1 * rnd.nextDouble() * (pbest[i][j] - x);

                if (tmp < vmin) tmp = vmin;
                if (tmp > vmax) tmp = vmax;

                speed[i][j] = tmp;
                particle[i][j] += tmp;
            }

        }

    }

    private double[] getLocalBest(int index, int neighbour) {
        int d = ffann.getWeightsCount();

        double[] lbest = new double[d];
        double lbestError = Double.POSITIVE_INFINITY;

        for (int i = -neighbour; i <= neighbour; ++i){
            int nIndex = index + i;
            nIndex += populationSize;
            nIndex %= populationSize;

            if (pError[nIndex] < lbestError){
                lbestError = pError[nIndex];
                System.arraycopy(pbest[nIndex], 0, lbest, 0, d);
            }
        }

        return lbest;
    }
}
