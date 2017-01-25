package hr.fer.zemris.generic.ga;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class GAIntArrSolution extends GASolution<int[]> {
    @Override
    public GASolution<int[]> duplicate() {
        GASolution<int[]> solution = new GAIntArrSolution();
        solution.data = new int[data.length];
        System.arraycopy(data, 0, solution.data, 0, data.length);

        return solution;
    }
}
