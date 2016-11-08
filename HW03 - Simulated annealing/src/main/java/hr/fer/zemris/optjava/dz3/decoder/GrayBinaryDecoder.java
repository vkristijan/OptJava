package hr.fer.zemris.optjava.dz3.decoder;

import hr.fer.zemris.optjava.dz3.solution.BitvectorSolution;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class GrayBinaryDecoder extends BitvectorDecoder {
    public GrayBinaryDecoder(double[] mins, double[] maxs, int[] bits, int n) {
        super(mins, maxs, bits, n);
    }

    public GrayBinaryDecoder(double min, double max, int bits, int n) {
        super(min, max, bits, n);
    }

    @Override
    public double[] decode(BitvectorSolution solution) {
        double[] values = new double[n];

        int bitsIndex = 0;
        int sum = 0;
        int length = bits[0];

        boolean isFirstDigit = true;
        boolean lastDigit = false;
        for (int i = 0; i < totalBits; ++i) {
            boolean currentDigit = solution.getBit(i);

            if (!isFirstDigit){
                if (currentDigit){
                    currentDigit = !lastDigit;
                } else {
                    currentDigit = lastDigit;
                }
            }
            lastDigit = currentDigit;

            sum *= 2;
            if (currentDigit){
                sum++;
            }

            length--;

            if (length == 0) {
                double maxRange = maxs[bitsIndex] - mins[bitsIndex];
                double maxValue = Math.pow(2, bits[bitsIndex]) - 1.00;
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
