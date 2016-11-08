package hr.fer.zemris.optjava.dz3.algorithm;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class GeometricTempSchedule implements ITempSchedule {
    private final double alpha;
    private final double tInitial;
    private final int innerLimit;
    private final int outerLimit;

    private double tCurrent;


    public GeometricTempSchedule(double alpha, int innerLimit, int outerLimit, double tInitial) {
        this.alpha = alpha;
        this.innerLimit = innerLimit;
        this.outerLimit = outerLimit;
        this.tInitial = tInitial;

        this.tCurrent = tInitial / alpha;
    }

    @Override
    public double getNextTemperature() {
        tCurrent *= alpha;

        return tCurrent;
    }

    @Override
    public int getInnerLoopCounter() {
        return innerLimit;
    }

    @Override
    public int getOuterLoopCounter() {
        return outerLimit;
    }
}
