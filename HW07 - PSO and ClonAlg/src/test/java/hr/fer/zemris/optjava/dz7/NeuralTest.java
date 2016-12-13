package hr.fer.zemris.optjava.dz7;

import hr.fer.zemris.optjava.dz7.ann.Dataset;
import hr.fer.zemris.optjava.dz7.ann.FFANN;
import hr.fer.zemris.optjava.dz7.ann.TransferFunction;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class NeuralTest {
    @Test
    public void test1() throws IOException {
        Path filepath = Paths.get(
                "C:\\Users\\krist\\OneDrive\\FER\\OptJava\\HW07 - PSO and CloAng\\data\\07-iris-formatirano.data"
        );

        FFANN ffann = new FFANN(new int[] {4, 5, 3, 3},
                new TransferFunction[] {TransferFunction.SIGMOID, TransferFunction.SIGMOID, TransferFunction.SIGMOID},
                new Dataset(Files.readAllLines(filepath)));

        int number = 55;
        assertEquals(number, ffann.getWeightsCount());

        double[] weights = new double[number];
        for (int i = 0; i < number; ++i){
            weights[i] = 0.1;
        }

        assertEquals(0.8365366587431725, ffann.calcError(weights), 0.001);
    }

    @Test
    public void test2() throws IOException {
        Path filepath = Paths.get(
                "C:\\Users\\krist\\OneDrive\\FER\\OptJava\\HW07 - PSO and CloAng\\data\\07-iris-formatirano.data"
        );

        FFANN ffann = new FFANN(new int[] {4, 3, 3},
                new TransferFunction[] {TransferFunction.SIGMOID, TransferFunction.SIGMOID},
                new Dataset(Files.readAllLines(filepath)));

        int number = 27;
        assertEquals(number, ffann.getWeightsCount());

        double[] weights = new double[number];
        for (int i = 0; i < number; ++i){
            weights[i] = 0.1;
        }

        assertEquals(0.8566740399081082, ffann.calcError(weights), 0.001);
    }

    @Test
    public void test3() throws IOException {
        Path filepath = Paths.get(
                "C:\\Users\\krist\\OneDrive\\FER\\OptJava\\HW07 - PSO and CloAng\\data\\07-iris-formatirano.data"
        );

        FFANN ffann = new FFANN(new int[] {4, 3, 3},
                new TransferFunction[] {TransferFunction.SIGMOID, TransferFunction.SIGMOID},
                new Dataset(Files.readAllLines(filepath)));

        int number = 27;
        assertEquals(number, ffann.getWeightsCount());

        double[] weights = new double[number];
        for (int i = 0; i < number; ++i){
            weights[i] = -0.2;
        }

        assertEquals(0.7019685477806382, ffann.calcError(weights), 0.001);
    }
}
