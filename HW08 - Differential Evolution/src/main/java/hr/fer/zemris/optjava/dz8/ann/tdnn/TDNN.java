package hr.fer.zemris.optjava.dz8.ann.tdnn;

import hr.fer.zemris.optjava.dz8.ann.ANN;
import hr.fer.zemris.optjava.dz8.ann.Dataset;
import hr.fer.zemris.optjava.dz8.ann.TransferFunction;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class TDNN extends ANN {
    public TDNN(int[] layout, TransferFunction transferFunction, Dataset dataset){
        super(layout, transferFunction, dataset);
    }

    @Override
    public int getWeightsCount(){
        int count = 0;
        for (int i = 1; i < layout.length; ++i){
            count += ((layout[i - 1]+1) * layout[i]);
        }
        return count;
    }

    @Override
    public void calcOutputs(double[] inputs, double[] weights, double[] outputs){
        checkArg(inputs, getInputCount(), "input");
        checkArg(outputs, getOutputCount(), "output");
        checkArg(weights, getWeightsCount(), "weight");

        double[] lastOutput = new double[inputs.length];
        System.arraycopy(inputs, 0, lastOutput, 0, inputs.length);

        int weightIndex = 0;
        //for every layer
        for (int i = 1; i < layout.length; ++i){
            double[] input = new double[lastOutput.length + 1];
            input[0] = 1; //bias
            System.arraycopy(lastOutput, 0, input, 1, lastOutput.length);

            lastOutput = new double[layout[i]];
            //for every neuron in layer
            for (int j = 0; j < layout[i]; ++j){
                double result = 0;

                //for every input to te neuron
                for (int k = 0; k < input.length; ++k){
                    result += input[k] * weights[weightIndex];
                    weightIndex++;
                }
                lastOutput[j] = transferFunction.valueAt(result);
            }
        }

        System.arraycopy(lastOutput, 0, outputs, 0, lastOutput.length);
    }
}
