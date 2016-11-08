package hr.fer.zemris.optjava.dz3;

import hr.fer.zemris.optjava.dz3.algorithm.*;
import hr.fer.zemris.optjava.dz3.decoder.IDecoder;
import hr.fer.zemris.optjava.dz3.decoder.NaturalBinaryDecoder;
import hr.fer.zemris.optjava.dz3.decoder.PassThroughDecoder;
import hr.fer.zemris.optjava.dz3.function.Function;
import hr.fer.zemris.optjava.dz3.function.IFunction;
import hr.fer.zemris.optjava.dz3.neighbor.BitVectorNeighborhood;
import hr.fer.zemris.optjava.dz3.neighbor.DoubleArrayUnifNeighborhood;
import hr.fer.zemris.optjava.dz3.neighbor.INeighborhood;
import hr.fer.zemris.optjava.dz3.solution.BitvectorSolution;
import hr.fer.zemris.optjava.dz3.solution.DoubleArraySolution;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class RegresijaSustava {
    private final static int VARIABLE_NUMBER = 6;
    private final static double INITIAL_DELTA = 0.1;
    private final static double INIT_MIN = -10;
    private final static double INIT_MAX = 10;

    private final static double MIN_BITS = 5;
    private final static double MAX_BITS = 30;

    private final static double ALPHA = 0.9925;
    private final static double INITAL_TEMP = 100;
    private final static int INNER_LIMIT = 5000;
    private final static int OUTER_LIMIT = 2500;

    public static void main(String[] args) {
        List<String> lines = null;

        try {
            lines = Files.readAllLines(Paths.get(args[0]));
        } catch (IOException e) {
            System.err.println("Unable to open the given file or file not found!");
            System.exit(1);
        }

        IFunction function = new Function(lines);
        ITempSchedule tempSchedule = new GeometricTempSchedule(ALPHA, INNER_LIMIT, OUTER_LIMIT, INITAL_TEMP);

        double[] deltas = generateArray(INITIAL_DELTA, VARIABLE_NUMBER);
        double[] mins = generateArray(INIT_MIN, VARIABLE_NUMBER);
        double[] maxs = generateArray(INIT_MAX, VARIABLE_NUMBER);

        if (args[1].startsWith("decimal")){
            IDecoder<DoubleArraySolution> decoder = new PassThroughDecoder();
            INeighborhood<DoubleArraySolution> neighborhood = new DoubleArrayUnifNeighborhood(deltas);
            DoubleArraySolution start = new DoubleArraySolution(VARIABLE_NUMBER);
            start.randomize(new Random(), mins, maxs);

            IOptAlgorithm algorithm = new SimulatedAnnealing(decoder, neighborhood, start, function, true, tempSchedule);

            algorithm.run();
            return;
        }

        if (!args[1].startsWith("binary:")){
            System.err.println("Unsuported operation!");
            System.exit(1);
        }

        args[1] = args[1].substring(7);
        int bitCount = Integer.parseInt(args[1]);
        if (bitCount < MIN_BITS || bitCount > MAX_BITS){
            System.err.println("Invalid number of bits!");
            System.exit(1);
        }

        IDecoder<BitvectorSolution> decoder = new NaturalBinaryDecoder(INIT_MIN, INIT_MAX, bitCount, VARIABLE_NUMBER);
        INeighborhood<BitvectorSolution> neighborhood = new BitVectorNeighborhood();
        BitvectorSolution start = new BitvectorSolution(VARIABLE_NUMBER * bitCount);
        start.randomize(new Random());

        IOptAlgorithm algorithm = new SimulatedAnnealing(decoder, neighborhood, start, function, true, tempSchedule);

        algorithm.run();
    }

    private static double[] generateArray(double value, int n){
        double[] array = new double[n];

        for (int i = 0; i < n; ++i){
            array[i] = value;
        }

        return array;
    }
}
