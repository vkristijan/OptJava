package hr.fer.zemris.optjava.dz3.algorithm;

import hr.fer.zemris.optjava.dz3.decoder.IDecoder;
import hr.fer.zemris.optjava.dz3.function.IFunction;
import hr.fer.zemris.optjava.dz3.neighbor.INeighborhood;

import java.util.Random;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class SimulatedAnnealing<T> implements IOptAlgorithm {
    private IDecoder<T> decoder;
    private INeighborhood<T> neighborhood;
    private T startWith;
    private IFunction function;
    private boolean minimize;
    private ITempSchedule tempSchedule;
    private static final Random rand = new Random();

    public SimulatedAnnealing(IDecoder<T> decoder, INeighborhood<T> neighborhood, T startWith,
                              IFunction function, boolean minimize, ITempSchedule tempSchedule) {

        this.decoder = decoder;
        this.neighborhood = neighborhood;
        this.startWith = startWith;
        this.function = function;
        this.minimize = minimize;
        this.tempSchedule = tempSchedule;
    }

    @Override
    public void run() {
        T currentSolution = startWith;
        double[] params = decoder.decode(startWith);
        double value = function.valueAt(params);

        int maxInner = tempSchedule.getInnerLoopCounter();
        int maxOuter = tempSchedule.getOuterLoopCounter();

        for (int i = 0; i < maxOuter; ++i){
            double temperature = tempSchedule.getNextTemperature();
            for (int j = 0; j < maxInner; ++j){
                T newSolution = neighborhood.randomNeighbor(currentSolution);

                params = decoder.decode(newSolution);
                double newValue = function.valueAt(params);

                double delta = newValue - value;
                if (!minimize){
                    delta *= -1;
                }

                if (delta < 0){
                    currentSolution = newSolution;
                    value = newValue;
                } else {
                    double p = Math.exp(-delta/temperature);
                    if (p > rand.nextDouble()){
                        currentSolution = newSolution;
                        value = newValue;
                    }
                }
            }
            System.out.print("Temperature #" + temperature + ": [ ");
            params = decoder.decode(currentSolution);
            for (double x : params){
                System.out.print(x + " ");
            }
            System.out.println("]  -> Error: " + value);
        }

    }
}
