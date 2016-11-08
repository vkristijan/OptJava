package hr.fer.zemris.trisat.algorithms;

import hr.fer.zemris.trisat.*;

import java.util.*;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Algorithm3 extends  TriSATAlgorithm {
    private final int numberOfBest = 2;

    private final int maxIterations = 100000;
    private SATFormulaStats stats;

    public Algorithm3(SATFormula formula) {
        super(formula);

        stats = new SATFormulaStats(formula);
    }

    @Override
    public void solve() {
        Random rnd = new Random();
        BitVector assignment = new BitVector(rnd, formula.getNumberOfVariables());

        int numberOfIterations = 0;

        while (numberOfIterations < maxIterations) {
            stats.setAssignment(assignment, true);
            numberOfIterations++;

            if (stats.getNumberOfSatisfied() == formula.getNumberOfClauses()){
                System.out.println(assignment.toString());
                return;
            }

            Map<Double, BitVector> possibleSolutions = new TreeMap<>(Comparator.reverseOrder());

            BitVectorNGenerator gen = new BitVectorNGenerator(assignment);
            for (MutableBitVector n : gen) {
                possibleSolutions.put(fitness(n), n.copy());
            }

            List<BitVector> acceptedSolutions = new ArrayList<>();
            for (BitVector vector : possibleSolutions.values()){
                acceptedSolutions.add(vector);
                if (acceptedSolutions.size() >= numberOfBest){
                    break;
                }
            }
            assignment = acceptedSolutions.get(rnd.nextInt(acceptedSolutions.size()));
        }

        System.out.println("Pretraga gotova - rjesenje nije pronadjeno");
    }

    private double fitness(BitVector assignment){
        double fitness = stats.setAssignment(assignment, false).getNumberOfSatisfied();
        fitness += stats.getPercentageBonus();
        return fitness;
    }
}
