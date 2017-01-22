package hr.fer.zemris.optjava.rng.rngimpl;

import hr.fer.zemris.optjava.rng.IRNG;

import java.util.Random;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class RNGRandomImpl implements IRNG {
    private final Random rnd;

    public RNGRandomImpl() {
        this.rnd = new Random();
    }

    @Override
    public double nextDouble() {
        return rnd.nextDouble();
    }

    @Override
    public double nextDouble(double min, double max) {
        return rnd.nextDouble() * (max - min) + min;
    }

    @Override
    public float nextFloat() {
        return rnd.nextFloat();
    }

    @Override
    public float nextFloat(float min, float max) {
        return rnd.nextFloat() * (max - min) + min;
    }

    @Override
    public int nextInt() {
        return rnd.nextInt();
    }

    @Override
    public int nextInt(int min, int max) {
        return rnd.nextInt(max - min) + min;
    }

    @Override
    public boolean nextBoolean() {
        return rnd.nextBoolean();
    }

    @Override
    public double nextGaussian() {
        return rnd.nextGaussian();
    }
}
