package hr.fer.zemris.optjava.dz7.ann;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class FFANN {
    private int[] layout;
    private TransferFunction[] transferFunctions;
    private Dataset dataset;

    public FFANN(int[] layout, TransferFunction[] transferFunctions, Dataset dataset){
        this.layout = layout;
        this.transferFunctions = transferFunctions;
        this. dataset = dataset;
    }

    public int getWeightsCount(){
        int count = 0;
        for (int i = 1; i < layout.length; ++i){
            count += ((layout[i - 1]+1) * layout[i]);
        }
        return count;
    }

    public int getInputCount(){
        return layout[0];
    }

    public int getOutputCount(){
        return layout[layout.length - 1];
    }

    private void checkArg(double[] arg, int length, String name){
        if (arg.length != length){
            throw new IllegalArgumentException("Wrong number of " + name +
                    " values! Expected " + length + " but got " + arg.length);
        }
    }

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
                for (int k = 0; k < lastOutput.length; ++k){
                    result += lastOutput[k] * weights[weightIndex];
                    weightIndex++;
                }
                lastOutput[j] = transferFunctions[i - 1].valueAt(result);
            }
        }

        System.arraycopy(lastOutput, 0, outputs, 0, lastOutput.length);
    }

    public double calcError(double[] weights){
        int n = dataset.size();

        double error = 0;
        for (int i = 0; i < n; ++i){
            double[] input = dataset.getInput(i).stream().mapToDouble(x -> x).toArray();
            double[] expected = dataset.getOutput(i).stream().mapToDouble(x -> x).toArray();
            double[] output = new double[getOutputCount()];

            calcOutputs(input, weights, output);

            for (int j = 0; j < output.length; ++j){
                error += Math.pow((expected[j] - output[j]), 2);
            }
        }

        error /= n;
        return error;
    }
}
