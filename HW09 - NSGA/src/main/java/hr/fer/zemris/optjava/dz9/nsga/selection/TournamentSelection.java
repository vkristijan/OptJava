package hr.fer.zemris.optjava.dz9.nsga.selection;

import hr.fer.zemris.optjava.dz9.nsga.Chromosome;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class TournamentSelection implements ISelection {
    private static Random rnd = new Random();
    private int tournamentSize;

    public TournamentSelection(int tournamentSize) {
        this.tournamentSize = tournamentSize;
    }

    @Override
    public Chromosome select(List<Chromosome> population) {
        List<Chromosome> selected = new ArrayList<>();

        int n = population.size();
        for (int i = 0; i < tournamentSize; ++i){
            selected.add(population.get(rnd.nextInt(n)));
        }

        selected.sort(Comparator.naturalOrder());
        return selected.get(0);
    }
}
