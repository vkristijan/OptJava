package hr.fer.zemris.trisat;

import java.util.Objects;

public class SATFormulaStats {
    private SATFormula formula;
    private BitVector assignment;
    private double[] post;

    private final double percentageConstantUp = 0.01;
    private final double percentageConstantDown = 0.1;
    private final double percentageUnitAmount = 50;

    public SATFormulaStats(SATFormula formula) {
        this.formula = formula;
        this.post = new double[formula.getNumberOfClauses()];
    }

    // analizira se predano rješenje i pamte svi relevantni pokazatelji
    public SATFormulaStats setAssignment(BitVector assignment, boolean updatePercentages) {
        this.assignment = assignment;

        int numberOfClauses = formula.getNumberOfClauses();

        if (!updatePercentages){
            return this;
        }

        for (int i = 0; i < numberOfClauses; ++i){
            if (formula.getClause(i).isSatisfied(assignment)){
                post[i] += (1-post[i])*percentageConstantUp;
            } else {
                post[i] += (0-post[i])*percentageConstantDown;
            }
        }

        return this;
    }

    // vraća temeljem onoga što je setAssignment zapamtio: broj klauzula koje su zadovoljene

    public int getNumberOfSatisfied() {
        Objects.requireNonNull(assignment, "No assignment given.");

        int numberOfSatisfied = 0;
        int numberOfClauses = formula.getNumberOfClauses();

        for (int i = 0; i < numberOfClauses; ++i){
            if (formula.getClause(i).isSatisfied(assignment)){
                numberOfSatisfied++;
            }
        }

        return numberOfSatisfied;
    }

    // vraća temeljem onoga što je setAssignment zapamtio
    public boolean isSatisfied() {
        return formula.isSatisfied(assignment);
    }

    // vraća temeljem onoga što je setAssignment zapamtio: suma korekcija klauzula
    public double getPercentageBonus() {
        double bonus = 0;
        int numberOfClauses = formula.getNumberOfClauses();

        for (int i = 0; i < numberOfClauses; ++i){
            if (formula.getClause(i).isSatisfied(assignment)){
                bonus += percentageUnitAmount * (1-post[i]);
            } else {
                bonus -= percentageUnitAmount * (1-post[i]);
            }
        }

        return bonus;
    }

    // vraća temeljem onoga što je setAssignment zapamtio: procjena postotka za klauzulu
    public double getPercentage(int index) {
        if (index < 0 || index > post.length){
            throw new IndexOutOfBoundsException("There is no clause with the given index.");
        }

        return post[index];
    }
}