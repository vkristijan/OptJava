package hr.fer.zemris.optjava.dz7.pso;

import hr.fer.zemris.optjava.dz7.ann.Dataset;
import hr.fer.zemris.optjava.dz7.ann.FFANN;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class GlobalPSO extends AbstractPSO {
    public GlobalPSO(FFANN ffann, int maxIteration, int populationSize, double maxError) {
        super(ffann, maxIteration, populationSize, maxError);
    }

    @Override
    protected void updateSpeed(double wCoef) {
        int d = ffann.getWeightsCount();

        double w = wStart + wCoef * (wStart - wEnd);
        for (int i = 0; i < populationSize; ++i){
            for (int j = 0; j < d; ++j){
                double x = particle[i][j];

                double tmp = speed[i][j] * w;
                tmp += c1 * rnd.nextDouble() * (pbest[i][j] - x);
                tmp += c2 * rnd.nextDouble() * (best[j] - x);

                if (tmp < vmin) tmp = vmin;
                if (tmp > vmax) tmp = vmax;

                speed[i][j] = tmp;
                particle[i][j] += tmp;
            }

        }
    }
}
