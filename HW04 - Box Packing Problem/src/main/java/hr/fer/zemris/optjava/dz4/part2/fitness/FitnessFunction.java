package hr.fer.zemris.optjava.dz4.part2.fitness;

import hr.fer.zemris.optjava.dz4.part2.chromosome.Box;
import hr.fer.zemris.optjava.dz4.part2.chromosome.Chromosome;

import java.util.List;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class FitnessFunction {
    private final double capacityConstant;

    public FitnessFunction(double capacityConstant) {
        this.capacityConstant = capacityConstant;
    }

    public double fitness(Chromosome chromosome){
        double fitness = 0;

        List<Box> boxes = chromosome.getBoxes();
        for (Box box : boxes){
            double boxFill = box.getHeight() - box.getCapacity();
            fitness += Math.pow(boxFill / box.getHeight(), capacityConstant);
        }
        fitness /= boxes.size();

        return fitness;
    }
}
