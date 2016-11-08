package hr.fer.zemris.optjava.dz3.solution;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class SingleObjectiveSolution implements Comparable<SingleObjectiveSolution> {
    private double fitness;
    private double value;

    @Override
    public int compareTo(SingleObjectiveSolution o) {
        return Double.compare(this.fitness, o.fitness);
    }
}
