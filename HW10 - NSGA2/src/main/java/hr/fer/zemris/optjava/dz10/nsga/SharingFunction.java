package hr.fer.zemris.optjava.dz10.nsga;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class SharingFunction {
    private double alpha;
    private double sigma;

    public SharingFunction(double alpha, double sigma) {
        this.alpha = alpha;
        this.sigma = sigma;
    }

    public double value(Chromosome c1, Chromosome c2, double[] xmin, double[] xmax){
        double dist = distance(c1.getSolution(), c2.getSolution(), xmin, xmax);

        if (dist < sigma){
            return 1.0 - Math.pow(dist / sigma, alpha);
        } else {
            return 0;
        }
    }

    private double distance(double[] sol1, double[] sol2, double[] xmin, double[] xmax){
        int n = sol1.length;
        double dist = 0;

        for (int i = 0; i < n; ++i){
            if (xmin == xmax) continue;

            dist += Math.pow((sol1[i] - sol2[i]) / (xmax[i] - xmin[i]), 2);
        }

        return dist;
    }
}
