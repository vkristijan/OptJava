package hr.fer.zemris.optjava.dz3.decoder;

import hr.fer.zemris.optjava.dz3.decoder.BitvectorDecoder;
import hr.fer.zemris.optjava.dz3.solution.BitvectorSolution;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class NaturalBinaryDecoder extends BitvectorDecoder {
    public NaturalBinaryDecoder(double mins, double maxs, int bits, int n) {
        super(mins, maxs, bits, n);
    }

    public NaturalBinaryDecoder(double[] mins, double[] maxs, int[] bits, int n) {
        super(mins, maxs, bits, n);
    }

    @Override
    public double[] decode(BitvectorSolution solution) {
        double[] values = new double[n];

        int bitsIndex = 0;
        int sum = 0;
        int length = bits[0];

        for (int i = 0; i < totalBits; ++i) {
            boolean bit = solution.getBit(i);
            sum *= 2;
            if (bit){
                sum++;
            }

            length--;

            if (length == 0) {
                double maxRange = maxs[bitsIndex] - mins[bitsIndex];
                double maxValue = Math.pow(2, bits[bitsIndex]) - 1.0;
                values[bitsIndex] = mins[bitsIndex] + sum * maxRange / maxValue;

                bitsIndex++;
                if (bitsIndex >= bits.length){
                    break;
                }
                length = bits[bitsIndex];
                sum = 0;
            }
        }

        return values;
    }

}
