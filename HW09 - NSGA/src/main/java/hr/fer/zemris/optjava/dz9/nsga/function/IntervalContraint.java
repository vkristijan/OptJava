package hr.fer.zemris.optjava.dz9.nsga.function;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class IntervalContraint implements IConstraint {
    private int index;

    private double lowerBound;
    private double upperBound;

    public IntervalContraint(int index, double lowerBound, double upperBound) {
        this.index = index;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    @Override
    public void validate(double[] x) {
        if (x[index] < lowerBound){
            x[index] = lowerBound;
        }

        if (x[index] > upperBound){
            x[index] = upperBound;
        }
    }
}
