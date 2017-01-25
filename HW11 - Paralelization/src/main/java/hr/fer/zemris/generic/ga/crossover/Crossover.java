package hr.fer.zemris.generic.ga.crossover;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Crossover implements ICrossover {
    private double acceptanceRate;

    public Crossover(double acceptanceRate) {
        this.acceptanceRate = acceptanceRate;
    }

    @Override
    public GASolution<int[]> crossover(GASolution<int[]> a, GASolution<int[]> b) {
        IRNG rnd = RNG.getRNG();
        GASolution<int[]> child = a.duplicate();

        int[] childData = child.getData();
        int[] data = b.getData();

        childData[0] = rnd.nextInt(Math.min(data[0], childData[0]), Math.max(data[0], childData[0]));
        int n = (childData.length - 1) / 5;
        int index = 1;
        for (int i = 0; i < n; ++i){
            if (rnd.nextDouble() > acceptanceRate){
                index += 5;
                continue;
            }

            System.arraycopy(data, index, childData, index, 5);
            index += 5;
        }

        return child;
    }
}
