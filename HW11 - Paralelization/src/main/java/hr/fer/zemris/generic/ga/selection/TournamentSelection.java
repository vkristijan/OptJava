package hr.fer.zemris.generic.ga.selection;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class TournamentSelection implements ISelection {
    private int tournamentSize;

    public TournamentSelection(int tournamentSize) {
        this.tournamentSize = tournamentSize;
    }

    @Override
    public GASolution<int[]> select(List<GASolution<int[]>> population) {
        IRNG rnd = RNG.getRNG();
        GASolution<int[]> best = null;

        int n = population.size();
        for (int i = 0; i < tournamentSize; ++i){
            GASolution<int[]> choosen = population.get(rnd.nextInt(0, n));
            if (best == null || best.fitness < choosen.fitness){
                best = choosen;
            }
        }

        return best;
    }
}
