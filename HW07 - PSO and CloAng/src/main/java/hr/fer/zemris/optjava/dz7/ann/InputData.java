package hr.fer.zemris.optjava.dz7.ann;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class InputData {
    private List<Double> input;
    private List<Double> output;

    public InputData(String data) {
        String[] dataParts = data.split(":");
        if (dataParts.length != 2){
            throw new IllegalArgumentException("The given data format is invalid!");
        }

        String inputString = dataParts[0].trim();
        input = new ArrayList<>();
        inputString = inputString.substring(1, inputString.length() - 1);

        String[] inputData = inputString.split(",");
        for (String inputNumber : inputData){
            input.add(Double.parseDouble(inputNumber.trim()));
        }


        String outputString = dataParts[1].trim();
        output = new ArrayList<>();
        outputString = outputString.substring(1, outputString.length() - 1);

        String[] outputData = outputString.split(",");
        for (String outputNumber : outputData){
            output.add(Double.parseDouble(outputNumber.trim()));
        }
    }

    public List<Double> getInput() {
        return input;
    }

    public List<Double> getOutput() {
        return output;
    }
}
