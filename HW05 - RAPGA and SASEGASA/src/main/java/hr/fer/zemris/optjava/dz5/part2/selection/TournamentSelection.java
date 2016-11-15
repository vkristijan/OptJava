package hr.fer.zemris.optjava.dz5.part2.selection;

import hr.fer.zemris.optjava.dz5.part2.chromosome.Chromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class TournamentSelection implements ISelection {
    private static final Random rnd = new Random();

    private final int tournamentSize;

    public TournamentSelection(int tournamentSize) {
        if (tournamentSize < 1){
            throw new IllegalArgumentException("Tournament size invalid!");
        }

        this.tournamentSize = tournamentSize;
    }

    @Override
    public Chromosome select(List<Chromosome> population) {
        List<Chromosome> selected = new ArrayList<>();

        int n = population.size();
        for (int i = 0; i < tournamentSize; ++i){
            int index = rnd.nextInt(n);
            selected.add(population.get(index));
        }
        selected.sort((a, b) -> Long.compare(b.getFitness(), a.getFitness()));

        return selected.get(0);
    }
}
