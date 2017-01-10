package hr.fer.zemris.optjava.dz10.nsga.moop;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public interface MOOPProblem {
    int getNumberOfObjectives();
    void evaluateSolution(double[] solution, double[] objectives);

    double[] evaluateSolution(double[] solution);

    int solutionSize();
}
