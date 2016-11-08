package hr.fer.zemris.optjava.dz4.part2.crossover;

import hr.fer.zemris.optjava.dz4.part2.chromosome.Box;
import hr.fer.zemris.optjava.dz4.part2.chromosome.Chromosome;
import hr.fer.zemris.optjava.dz4.part2.chromosome.Stick;

import java.util.*;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Crossover {
    private static final Random rnd = new Random();

    public Chromosome crossover(Chromosome a, Chromosome b){
        Chromosome child = null;

        List<Box> boxesA = a.getBoxes();
        List<Box> boxesB = b.getBoxes();

        //insert some boxes from B to A
        int from = rnd.nextInt(boxesB.size());
        int to = rnd.nextInt(boxesB.size());

        if (from > to){
            int tmp = from;
            from = to;
            to = tmp;
        }

        List<Box> childBoxes = new ArrayList<>();
        Set<Stick> childSticks = new HashSet<>();
        for (int i = from; i <= to; ++i){
            childBoxes.add(boxesB.get(i));
            childSticks.addAll(boxesB.get(i).getSticks());
        }

        List<Stick> sticksToAdd = new ArrayList<>();
        for (Box box : boxesB){
            List<Stick> boxSticks = box.getSticks();

            boolean containsAny = false;
            for (Stick stick : boxSticks){
                if (childSticks.contains(stick)){
                    containsAny = true;
                    break;
                }
            }

            if (containsAny){
                for (Stick stick : boxSticks){
                    if (!childSticks.contains(stick)){
                        sticksToAdd.add(stick);
                    }
                }
            } else {
                childBoxes.add(box);
            }
        }

        child = new Chromosome(childBoxes);
        sticksToAdd.sort(Comparator.reverseOrder());
        child.addSticks(sticksToAdd);

        return child;
    }
}
