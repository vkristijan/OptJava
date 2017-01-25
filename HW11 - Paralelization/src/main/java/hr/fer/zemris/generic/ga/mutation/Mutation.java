package hr.fer.zemris.generic.ga.mutation;

import hr.fer.zemris.art.GrayScaleImage;
import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Mutation implements IMutation {
    private double backgroundChange;
    private double rectangleChange;
    private int changeRate;
    private int width;
    private int height;

    public Mutation(double backgroundChange, double rectangleChange, int changeRate, int width, int height) {
        this.backgroundChange = backgroundChange;
        this.rectangleChange = rectangleChange;
        this.changeRate = changeRate;
        this.width = width;
        this.height = height;
    }

    @Override
    public void mutate(GASolution<int[]> solution) {
        IRNG rnd = RNG.getRNG();
        int[] data = solution.getData();

        if (rnd.nextDouble() < backgroundChange){
            data[0] = rnd.nextInt(0, GrayScaleImage.SHADES_OF_GREY);
        }

        int n = (data.length - 1) / 5;
        int index = 1;
        for (int i = 0; i < n; ++i){
            if (rnd.nextDouble() > rectangleChange){
                index += 5;
                continue;
            }

            data[index] = change(data[index], 0, width - 1, rnd);
            data[index + 1] = change(data[index + 1], 0, height - 1, rnd);
            data[index + 2] = change(data[index + 2], 1, width - data[index], rnd);
            data[index + 3] = change(data[index + 3], 1, height - data[index + 1], rnd);
            data[index + 4] = change(data[index + 4], 0, GrayScaleImage.SHADES_OF_GREY, rnd);

            index += 5;
        }
    }

    private int change(int data, int min, int max, IRNG rnd) {
        data += (int)((double)changeRate * rnd.nextGaussian());

        if (data < min){
            data = min;
        }
        if (data >= max){
            data = max;
        }

        return data;
    }
}
