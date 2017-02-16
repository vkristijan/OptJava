package hr.fer.zemris.optjava.dz12.genprog.selection;

import hr.fer.zemris.optjava.dz12.genprog.Solution;

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
    public Solution select(List<Solution> population) {
        Solution best = null;

        int n = population.size();
        for (int i = 0; i < tournamentSize; ++i){
            Solution chosen = population.get(rnd.nextInt(n));
            if (best == null || best.getFitness() < chosen.getFitness()){
                best = chosen;
            }
        }

        return best;
    }
}
