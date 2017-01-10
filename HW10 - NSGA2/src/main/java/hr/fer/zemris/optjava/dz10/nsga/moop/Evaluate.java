package hr.fer.zemris.optjava.dz10.nsga.moop;

import hr.fer.zemris.optjava.dz10.nsga.Chromosome;
import hr.fer.zemris.optjava.dz10.nsga.SharingFunction;

import java.util.*;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Evaluate {
    private MOOPProblem moop;
    private double sigmaShare;
    private double alpha;
    private double[] xmin;
    private double[] xmax;
    private double epsilon;

    public Evaluate(MOOPProblem moop, double sigmaShare, double alpha, double[] xmin, double[] xmax, double epsilon) {
        this.moop = moop;
        this.sigmaShare = sigmaShare;
        this.alpha = alpha;
        this.xmin = xmin;
        this.xmax = xmax;
        this.epsilon = epsilon;
    }

    public List<List<Chromosome>> evaluate(List<Chromosome> population){
        for (Chromosome chromosome : population){
            moop.evaluateSolution(chromosome.getSolution(), chromosome.getObjective());
            chromosome.setDominatedBy(0);
            chromosome.setDominating(new HashSet<>());
        }

        int n = population.size();
        for (int i = 0; i < n; ++i){
            for (int j = 0; j < n; ++j){
                if (i == j) continue;

                Chromosome c1 = population.get(i);
                Chromosome c2 = population.get(j);

                if (isDominating(c1, c2)){
                    c2.incrementDominatedBy();
                    c1.addDominating(c2);
                }
            }
        }

        List<List<Chromosome>> fronts = getFronts(population);

        SharingFunction sf = new SharingFunction(alpha, sigmaShare);
        double tmpFitness = population.size() + epsilon;

        int frontCounter = -1;
        for (List<Chromosome> front : fronts){
            tmpFitness -= epsilon;
            frontCounter++;

            for (Chromosome chromosome : front){
                chromosome.setFront(frontCounter);
                chromosome.setDistance(0);

                double nc = 0;
                for (Chromosome other : front){
                    nc += sf.value(chromosome, other, xmin, xmax);
                }
                chromosome.setFitness(tmpFitness / nc);
            }

            for (Chromosome chromosome : front){
                if (chromosome.getFitness() < tmpFitness){
                    tmpFitness = chromosome.getFitness();
                }
            }

            for (int i = 0; i < front.get(0).getSolution().length; ++i){
                int index = i;
                front.sort(Comparator.comparingDouble(c -> c.getSolution()[index]));

                int m = front.size();

                front.get(0).setDistance(Double.POSITIVE_INFINITY);
                front.get(m - 1).setDistance(Double.POSITIVE_INFINITY);

                for (int j = 1; j < m - 1; ++j){
                    double d = front.get(j).getDistance();

                    double s1 = front.get(j - 1).getSolution(i);
                    double s2 = front.get(j + 1).getSolution(i);
                    d += ((s2 - s1) / (xmax[i] - xmin[i]));

                    front.get(j).setDistance(d);
                }

            }
        }

        return fronts;
    }

    private List<List<Chromosome>> getFronts(List<Chromosome> population) {
        List<List<Chromosome>> fronts = new ArrayList<>();
        List<Chromosome> populationCopy = new ArrayList<>(population);

        while (!populationCopy.isEmpty()){
            List<Chromosome> front = new ArrayList<>();

            for (Chromosome chromosome : populationCopy){
                if (chromosome.getDominatedBy() == 0){
                    front.add(chromosome);
                }
            }

            for (Chromosome chromosome : front){
                for (Chromosome dominated : chromosome.getDominating()){
                    dominated.decrementDominatedBy();
                }
            }

            populationCopy.removeAll(front);
            fronts.add(front);
        }


        return fronts;
    }

    /**
     * Checks if {@link Chromosome} c1 dominated {@link Chromosome} c2.
     * @param c1 the first chromosome
     * @param c2 second chromosome
     * @return true if c1 dominates c2
     */
    private boolean isDominating(Chromosome c1, Chromosome c2){
        double[] objective1 = c1.getObjective();
        double[] objective2 = c2.getObjective();
        int n = objective1.length;

        boolean dominating = false;
        for (int i = 0; i < n; ++i){
            if (objective1[i] > objective2[i]){
                return false;
            }

            if (objective1[i] < objective2[i]){
                dominating = true;
            }
        }

        return dominating;
    }
}
