package hr.fer.zemris.optjava.dz8;

import hr.fer.zemris.optjava.dz8.ann.Dataset;
import hr.fer.zemris.optjava.dz8.ann.TransferFunction;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import hr.fer.zemris.optjava.dz8.ann.elman.Elman;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
@RunWith(MockitoJUnitRunner.class)
public class ElmanTest {
    @Mock
    private Dataset dataset;

    @Test
    public void test1() throws IOException {
        when(dataset.size()).thenReturn(1);
        List<Double> input = new ArrayList<>();
        input.add(1.0);
        when(dataset.getInput(0)).thenReturn(input);

        List<Double> output = new ArrayList<>();
        output.add(0.1850593);
        when(dataset.getOutput(0)).thenReturn(output);

        Elman elman = new Elman(
                new int[] {1, 4, 2, 1},
                TransferFunction.TANH,
                dataset
        );

        int number = 37 + 4;
        assertEquals(number, elman.getWeightsCount());

        double[] weights = new double[number];
        for (int i = 0; i < number; ++i){
            weights[i] = 0.1;
        }
        weights[37] = 3;
        weights[38] = 3;
        weights[39] = 3;
        weights[40] = 3;

        assertEquals(0, elman.calcError(weights), 0.001);
    }

    @Test
    public void test2() throws IOException {
        when(dataset.size()).thenReturn(2);
        List<Double> input = new ArrayList<>();
        input.add(1.0);
        when(dataset.getInput(0)).thenReturn(input);

        List<Double> output = new ArrayList<>();
        output.add(0.1850593);
        when(dataset.getOutput(0)).thenReturn(output);

        List<Double> input1 = new ArrayList<>();
        input1.add(1.0);
        when(dataset.getInput(1)).thenReturn(input1);

        List<Double> output1 = new ArrayList<>();
        output1.add(0.15157);
        when(dataset.getOutput(1)).thenReturn(output1);

        Elman elman = new Elman(
                new int[] {1, 4, 2, 1},
                TransferFunction.TANH,
                dataset
        );

        int number = 37 + 4;
        assertEquals(number, elman.getWeightsCount());

        double[] weights = new double[number];
        for (int i = 0; i < number; ++i){
            weights[i] = 0.1;
        }
        weights[37] = 3;
        weights[38] = 3;
        weights[39] = 3;
        weights[40] = 3;

        assertEquals(0, elman.calcError(weights), 0.001);
    }
}
