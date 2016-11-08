package hr.fer.zemris.optjava.dz4.part2.mutation;

import hr.fer.zemris.optjava.dz4.part2.chromosome.Box;
import hr.fer.zemris.optjava.dz4.part2.chromosome.Chromosome;
import hr.fer.zemris.optjava.dz4.part2.chromosome.Stick;

import java.util.*;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Mutation {
    private static final Random rnd = new Random();
    private final double mutationChance;

    public Mutation(double mutationChance) {
        this.mutationChance = mutationChance;
    }

    public void mutate(Chromosome chromosome){
        List<Box> boxes = chromosome.getBoxes();

        List<Stick> removedSticks = new ArrayList<>();
        Iterator<Box> it = boxes.iterator();
        while(it.hasNext()){
            Box box = it.next();
            if (rnd.nextDouble() >= mutationChance){
                continue;
            }

            removedSticks.addAll(box.getSticks());
            it.remove();
        }

        removedSticks.sort(Comparator.reverseOrder());
        chromosome.addSticks(removedSticks);
    }
}
