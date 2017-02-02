package hr.fer.zemris.optjava.dz11;

import hr.fer.zemris.art.GrayScaleImage;
import hr.fer.zemris.art.IImgProvider;
import hr.fer.zemris.art.ThreadLocalImageProvider;
import hr.fer.zemris.generic.ga.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Pokretac2 {
    private static Path originalFile;
    private static int numberOfRectangles;
    private static int populationSize;
    private static int maxGenerations;
    private static double minFitness;
    private static Path parametersFile;
    private static Path resultFile;

    public static void main(String[] args) throws IOException {
        if (args.length != 7){
            System.err.println("Expected 7 arguments!");
            System.exit(1);
        }

        originalFile = Paths.get(args[0]);
        numberOfRectangles = Integer.parseInt(args[1]);
        maxGenerations = Integer.parseInt(args[3]);
        populationSize = Integer.parseInt(args[2]);
        minFitness = Double.parseDouble(args[4]);
        parametersFile = Paths.get(args[5]);
        resultFile = Paths.get(args[6]);

        GrayScaleImage template = GrayScaleImage.load(originalFile.toFile());
        int width = template.getWidth();
        int height = template.getHeight();
        IImgProvider provider = new ThreadLocalImageProvider(width, height);

        Evaluator evaluator = new Evaluator(template, provider);

        Ga2 ga = new Ga2(maxGenerations, minFitness, populationSize, numberOfRectangles, width, height, evaluator);
        GASolution<int[]> solution = ga.run();

        if (!Files.exists(resultFile)){
            Files.createFile(resultFile);
        }
        GrayScaleImage generatedImage = evaluator.draw(solution, null);
        generatedImage.save(resultFile.toFile());

        if (!Files.exists(parametersFile)){
            Files.createFile(parametersFile);
        }

        FileWriter fw = new FileWriter(parametersFile.toFile());
        for (int data : solution.getData()) {
            fw.write(String.valueOf(data));
            fw.write("\n");
        }
        fw.close();
    }
}
