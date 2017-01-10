package hr.fer.zemris.optjava.dz10.nsga.moop;

import hr.fer.zemris.optjava.dz10.nsga.function.IConstraint;
import hr.fer.zemris.optjava.dz10.nsga.function.IFunction;

import java.util.List;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class MOOPProblemImpl implements MOOPProblem {
    private List<IFunction> objectiveFunctions;
    private List<IConstraint> constraints;
    private int solutionSize;

    public MOOPProblemImpl(List<IFunction> objectiveFunctions, List<IConstraint> constraints, int solutionSize) {
        this.objectiveFunctions = objectiveFunctions;
        this.constraints = constraints;
        this.solutionSize = solutionSize;
    }

    @Override
    public int getNumberOfObjectives() {
        return objectiveFunctions.size();
    }

    @Override
    public void evaluateSolution(double[] solution, double[] objectives) {
        for (IConstraint constraint : constraints){
            constraint.validate(solution);
        }

        int n = getNumberOfObjectives();
        for (int i = 0; i < n; ++i){
            objectives[i] = objectiveFunctions.get(i).valueAt(solution);
        }
    }

    @Override
    public double[] evaluateSolution(double[] solution) {
        double[] objectives = new double[getNumberOfObjectives()];
        evaluateSolution(solution, objectives);
        return objectives;
    }

    @Override
    public int solutionSize() {
        return solutionSize;
    }


}
