package hr.fer.zemris.optjava.dz9.nsga;

import java.util.Arrays;
import java.util.Set;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Chromosome implements Comparable<Chromosome>{
    private double[] solution;
    private double[] objective;

    private Set<Chromosome> dominating;
    private int dominatedBy;

    private int hash;
    private boolean hasHash;

    private double fitness;

    public Chromosome(int solutionSize, int objectiveSize) {
        this.solution = new double[solutionSize];
        this.objective = new double[objectiveSize];
    }

    public int getSolutionSize(){
        return solution.length;
    }

    public int getObjectiveSize(){
        return objective.length;
    }

    public double getSolution(int index){
        return solution[index];
    }

    public void setSolution(int index, double value){
        solution[index] = value;
        hasHash = false;
    }

    public double[] getObjective() {
        return objective;
    }

    public void setObjective(double[] objective) {
        this.objective = objective;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chromosome that = (Chromosome) o;
        if (this.hashCode() != that.hashCode()){
            return false;
        }

        return Arrays.equals(solution, that.solution);
    }

    @Override
    public int hashCode() {
        if (hasHash == true){
            return hash;
        }

        hasHash = true;
        hash = Arrays.hashCode(solution);
        return hash;
    }

    @Override
    public int compareTo(Chromosome o) {
        return Double.compare(this.fitness, o.fitness);
    }
}
