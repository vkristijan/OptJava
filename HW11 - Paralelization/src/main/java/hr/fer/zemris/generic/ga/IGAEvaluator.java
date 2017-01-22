package hr.fer.zemris.generic.ga;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public interface IGAEvaluator<T> {
    public void evaluate(GASolution<T> p);
}
