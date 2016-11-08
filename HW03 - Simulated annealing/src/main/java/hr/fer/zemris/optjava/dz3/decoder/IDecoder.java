package hr.fer.zemris.optjava.dz3.decoder;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public interface IDecoder<T> {
    double[] decode(T solution);
    void decode(T solution, double[] result);
}
