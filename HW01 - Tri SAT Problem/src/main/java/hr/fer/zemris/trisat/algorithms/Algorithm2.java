package hr.fer.zemris.trisat.algorithms;

import hr.fer.zemris.trisat.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Algorithm2 extends TriSATAlgorithm {
    private final int maxIterations = 100000;
    private SATFormulaStats stats;

    public Algorithm2(SATFormula formula) {
        super(formula);

        stats = new SATFormulaStats(formula);
    }

    @Override
    public void solve() {
        Random rnd = new Random();
        BitVector assignment = new BitVector(rnd, formula.getNumberOfVariables());

        int numberOfIterations = 0;

        while (numberOfIterations < maxIterations) {
            numberOfIterations++;
            //System.err.println(numberOfIterations);
            int fitness = fitness(assignment);
            if (fitness == formula.getNumberOfClauses()){
                System.out.println(assignment.toString());
                return;
            }
            List<BitVector> possibleSolutions = new ArrayList<>();

            BitVectorNGenerator gen = new BitVectorNGenerator(assignment);
            for (MutableBitVector n : gen) {
                if (fitness(n) >= fitness) {
                    possibleSolutions.add(n.copy());
                }
            }

            if (possibleSolutions.size() == 0){
                System.out.println("Pretraga neuspjesna - nadjen lokalni minimum");
                return;
            }

            assignment = possibleSolutions.get(rnd.nextInt(possibleSolutions.size()));
        }

        System.out.println("Pretraga gotova - rjesenje nije pronadjeno");
    }

    private int fitness(BitVector assignment){
        return stats.setAssignment(assignment, false).getNumberOfSatisfied();
    }
}
