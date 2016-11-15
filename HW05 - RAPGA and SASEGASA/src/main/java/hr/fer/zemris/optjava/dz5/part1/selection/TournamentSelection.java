package hr.fer.zemris.optjava.dz5.part1.selection;

import hr.fer.zemris.optjava.dz5.part1.chromosome.Chromosome;
import hr.fer.zemris.optjava.dz5.part1.fitness.FitnessFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class TournamentSelection implements ISelection {
    private static final Random rnd = new Random();

    private final int tournamentSize;
    private final FitnessFunction fitnessFunction;

    public TournamentSelection(int tournamentSize, FitnessFunction fitnessFunction) {
        if (tournamentSize < 1){
            throw new IllegalArgumentException("Tournament size invalid!");
        }

        this.tournamentSize = tournamentSize;
        this.fitnessFunction = fitnessFunction;
    }

    @Override
    public Chromosome select(Set<Chromosome> population) {
        List<Chromosome> populationList = new ArrayList<>(population);
        List<Chromosome> selected = new ArrayList<>();

        int n = population.size();
        for (int i = 0; i < tournamentSize; ++i){
            int index = rnd.nextInt(n);
            selected.add(populationList.get(index));
        }
        selected.sort(
                (a, b) ->
                        Double.compare(
                            fitnessFunction.fitness(a),
                            fitnessFunction.fitness(b)
                        )
        );

        return selected.get(0);
    }
}
