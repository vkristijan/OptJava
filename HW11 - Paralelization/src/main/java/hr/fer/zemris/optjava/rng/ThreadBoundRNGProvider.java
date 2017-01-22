package hr.fer.zemris.optjava.rng;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class ThreadBoundRNGProvider implements IRNGProvider {
    @Override
    public IRNG getRNG() {
        Thread current = Thread.currentThread();
        IRNGProvider threadProvider = (IRNGProvider) current;

        return threadProvider.getRNG();
    }
}
