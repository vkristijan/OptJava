package hr.fer.zemris.optjava.dz8;

import hr.fer.zemris.optjava.dz8.ann.ANN;
import hr.fer.zemris.optjava.dz8.ann.Dataset;
import hr.fer.zemris.optjava.dz8.ann.TransferFunction;
import hr.fer.zemris.optjava.dz8.ann.elman.Elman;
import hr.fer.zemris.optjava.dz8.ann.tdnn.TDNN;

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
    private static ANN ann;
    private static int populationSize;
    private static double maxError;
    private static int maxIteration;
    private static Dataset dataset;

    private static double f = 1.5;
    private static int dataCount = 600;
    private static double lowerBound = -5;
    private static double upperBound = 5;
    private static double cr = 0.01;

    public static void main(String[] args) {
        if (args.length != 5){
            System.err.println("Invalid number of arguments!");
            System.exit(1);
        }

        filePath = Paths.get(args[0]);
        populationSize = Integer.parseInt(args[2]);
        maxError = Double.parseDouble(args[3]);
        maxIteration = Integer.parseInt(args[4]);

        List<String> lines = null;
        try {
            lines = Files.readAllLines(filePath);
        } catch (IOException e) {
            System.err.println("Unable to read from the given file!");
            System.exit(2);
        }

        double[] data = new double[lines.size()];
        for (int i = 0; i < lines.size(); ++i){
            data[i] = Double.parseDouble(lines.get(i).trim());
        }

        String arh = args[1].split("-")[1];
        String[] layoutString = arh.split("x");
        int[] layout = new int[layoutString.length];

        for (int i = 0; i < layout.length; ++i){
            layout[i] = Integer.parseInt(layoutString[i]);
        }

        dataset = new Dataset(data, layout[0], dataCount);

        if (args[1].startsWith("TDNN")){
            ann = new TDNN(layout, TransferFunction.TANH, dataset);
        } else if (args[1].startsWith("elman")){
            ann = new Elman(layout, TransferFunction.TANH, dataset);
        } else {
            System.err.println("Unsupported neural network!");
            System.exit(3);
        }

        DE de = new DE(ann, maxError, populationSize, maxIteration, f, lowerBound, upperBound, cr);
        de.run();
    }
}
