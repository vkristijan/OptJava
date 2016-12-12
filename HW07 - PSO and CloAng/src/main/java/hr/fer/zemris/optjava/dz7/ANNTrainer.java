package hr.fer.zemris.optjava.dz7;

import hr.fer.zemris.optjava.dz7.ann.Dataset;
import hr.fer.zemris.optjava.dz7.ann.FFANN;
import hr.fer.zemris.optjava.dz7.ann.TransferFunction;
import hr.fer.zemris.optjava.dz7.pso.AbstractPSO;
import hr.fer.zemris.optjava.dz7.pso.GlobalPSO;
import hr.fer.zemris.optjava.dz7.pso.LocalPSO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class ANNTrainer {
    private static Path filePath;
    private static String alg;
    private static int populationSize;
    private static double maxError;
    private static int maxIter;

    public static void main(String[] args) {
        if (args.length != 5){
            System.err.println("Wrong number of arguments! Expected 5 but got " + args.length);
            System.exit(1);
        }

        filePath = Paths.get(args[0]);
        alg = args[1].trim();
        populationSize = Integer.parseInt(args[2]);
        maxError = Double.parseDouble(args[3]);
        maxIter = Integer.parseInt(args[4]);

        List<String> inputLines = null;
        try {
            inputLines = Files.readAllLines(filePath);
        } catch (IOException e) {
            System.err.println("Unable to read from the given file!");
            System.exit(1);
        }

        Dataset dataset = new Dataset(inputLines);
        FFANN ffann = new FFANN(
                new int[] {4,5, 3},
                new TransferFunction[] {
                        TransferFunction.SIGMOID,
                        TransferFunction.SIGMOID
                },
                dataset);

        if (alg.startsWith("pso-a")){
            AbstractPSO pso = new GlobalPSO(ffann, maxIter, populationSize, maxError);
            pso.run();
        } else if (alg.startsWith("pso-b-")){
            String arg = alg.substring("pso-b-".length()).trim();
            int neighbour = Integer.parseInt(arg);

            AbstractPSO pso = new LocalPSO(ffann, maxIter, populationSize, maxError, neighbour);
            pso.run();
        } else if (alg.startsWith("clonalg")){
            //TODO
        } else {
            System.err.println("Unsupported algorithm!");
            System.exit(1);
        }
    }
}
