package hr.fer.zemris.optjava.dz3.algorithm;

import hr.fer.zemris.optjava.dz3.decoder.IDecoder;
import hr.fer.zemris.optjava.dz3.function.IFunction;
import hr.fer.zemris.optjava.dz3.neighbor.INeighborhood;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class GreedyAlgorithm<T> implements IOptAlgorithm<T> {
    private static final int NUMBER_OF_ITERATIONS = 1000000;

    private IDecoder<T> decoder;
    private INeighborhood<T> neighborhood;
    private T startWith;
    private IFunction function;
    private boolean minimize;

    public GreedyAlgorithm(IDecoder<T> decoder, INeighborhood<T> neighborhood, T startWith,
                           IFunction function, boolean minimize) {

        this.decoder = decoder;
        this.neighborhood = neighborhood;
        this.startWith = startWith;
        this.function = function;
        this.minimize = minimize;
    }

    @Override
    public void run() {
        T currentSolution = startWith;
        double[] params = decoder.decode(startWith);
        double value = function.valueAt(params);

        for (int i = 0; i < NUMBER_OF_ITERATIONS; ++i){
            T newSolution = neighborhood.randomNeighbor(currentSolution);

            params = decoder.decode(newSolution);
            double newValue = function.valueAt(params);

            //System.out.println(newValue + " " + value);

            if (newValue < value && minimize){
                currentSolution = newSolution;
                value = newValue;
            }

            if (i % 10000 == 0){
                System.out.print("Iteration #" + i + ": [ ");
                params = decoder.decode(currentSolution);
                for (double x : params){
                    System.out.print(x + " ");
                }
                System.out.println("]  -> Value: " + value);
            }
        }
    }
}
