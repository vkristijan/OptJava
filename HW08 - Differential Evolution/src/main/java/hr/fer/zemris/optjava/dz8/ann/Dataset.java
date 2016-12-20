package hr.fer.zemris.optjava.dz8.ann;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Dataset {
    private double[] data;
    private int inputCount;
    private int dataCount;

    public Dataset(double[] data, int inputCount, int dataCount){
        this.data = data;
        this.inputCount = inputCount;
        this.dataCount = dataCount;

        double min = data[0];
        double max = data[0];

        for (double x : data){
            if (x > max){
                max = x;
            }
            if (x < min){
                min = x;
            }
        }

        for (int i = 0; i < data.length; ++i){
            this.data[i] = 2 * (this.data[i] - min) / (max - min) - 1;
        }
    }

    public int size(){
        return dataCount - inputCount;
    }

    public List<Double> getInput(int index){
        List<Double> input = new ArrayList<>();

        for (int i = 0; i < inputCount; ++i){
            input.add(data[index + i]);
        }
        return input;
    }

    public List<Double> getOutput(int index){
        List<Double> output = new ArrayList<>();

        output.add(data[index + inputCount]);
        return output;
    }
}
