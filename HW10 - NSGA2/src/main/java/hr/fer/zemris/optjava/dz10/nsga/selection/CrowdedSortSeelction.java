package hr.fer.zemris.optjava.dz10.nsga.selection;

import hr.fer.zemris.optjava.dz10.nsga.Chromosome;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class CrowdedSortSeelction implements ISelection {
    private static Random rnd = new Random();
    private int tournamentSize;

    public CrowdedSortSeelction(int tournamentSize) {
        this.tournamentSize = tournamentSize;
    }

    @Override
    public Chromosome select(List<Chromosome> population) {
        int n = population.size();

        Chromosome selected = population.get(rnd.nextInt(n));

        for (int i = 1; i < tournamentSize; ++i){
            Chromosome newSelected =  population.get(rnd.nextInt(n));

            if (newSelected.getFront() < selected.getFront()){
                selected = newSelected;
            } else if (newSelected.getDistance() > selected.getDistance()){
                selected = newSelected;
            }
        }

        return selected;
    }
}
