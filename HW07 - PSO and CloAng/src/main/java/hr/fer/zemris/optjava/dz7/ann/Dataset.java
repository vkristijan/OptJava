package hr.fer.zemris.optjava.dz7.ann;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Dataset {
    private List<InputData> data;

    public Dataset(List<String> inputLines){
        data = new ArrayList<>();

        for (String line : inputLines){
            data.add(new InputData(line));
        }
    }

    public int size(){
        return data.size();
    }

    public int getInputSize(){
        return data.get(0).getInput().size();
    }

    public int getOutputSize(){
        return data.get(0).getOutput().size();
    }

    public InputData getData(int index){
        return data.get(index);
    }

    public List<Double> getInput(int index){
        return getData(index).getInput();
    }

    public List<Double> getOutput(int index){
        return getData(index).getOutput();
    }
}
