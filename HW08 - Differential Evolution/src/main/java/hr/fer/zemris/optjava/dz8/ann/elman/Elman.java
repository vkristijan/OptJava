package hr.fer.zemris.optjava.dz8.ann.elman;

import hr.fer.zemris.optjava.dz8.ann.ANN;
import hr.fer.zemris.optjava.dz8.ann.Dataset;
import hr.fer.zemris.optjava.dz8.ann.TransferFunction;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Elman extends ANN {
    private double[] context;

    public Elman(int[] layout, TransferFunction transferFunction, Dataset dataset){
        super(layout, transferFunction, dataset);

        if (layout.length < 3){
            throw new IllegalArgumentException("There must be at least 3 layers in this neural network.");
        }

        this.context = new double[layout[1]];
    }

    @Override
    public int getWeightsCount(){
        //initial values
        int count = layout[1];
        //weights for the context layer
        count += layout[1] * layout[1];
        //standard weights
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

        int index = weights.length - context.length - 1;
        System.arraycopy(weights, index, context, 0, context.length);

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

                //use context layer!
                if(i == 1){
                    for (int k = 0; k < context.length; ++k){
                        result += context[k] * weights[weightIndex];
                        weightIndex++;
                    }
                }

                lastOutput[j] = transferFunction.valueAt(result);
            }

            if (i == 1){
                System.arraycopy(lastOutput, 0, context, 0, context.length);
            }
        }

        System.arraycopy(lastOutput, 0, outputs, 0, lastOutput.length);
    }

}
