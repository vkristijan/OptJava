package hr.fer.zemris.optjava.dz8.ann;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public abstract class ANN {
    protected int[] layout;
    protected TransferFunction transferFunction;
    protected Dataset dataset;

    public ANN(int[] layout, TransferFunction transferFunction, Dataset dataset){
        this.layout = layout;
        this.transferFunction = transferFunction;
        this. dataset = dataset;
    }

    public int getInputCount(){
        return layout[0];
    }

    public int getOutputCount(){
        return layout[layout.length - 1];
    }

    protected void checkArg(double[] arg, int length, String name){
        if (arg.length != length){
            throw new IllegalArgumentException("Wrong number of " + name +
                    " values! Expected " + length + " but got " + arg.length);
        }
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

    public abstract void calcOutputs(double[] inputs, double[] weights, double[] outputs);
    public abstract int getWeightsCount();
}
