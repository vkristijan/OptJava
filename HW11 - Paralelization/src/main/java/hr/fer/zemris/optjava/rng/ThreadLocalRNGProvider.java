package hr.fer.zemris.optjava.rng;

import hr.fer.zemris.optjava.rng.rngimpl.RNGRandomImpl;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class ThreadLocalRNGProvider implements IRNGProvider {
    private ThreadLocal<IRNG> threadLocal = new ThreadLocal<>();

    @Override
    public IRNG getRNG() {
        IRNG irng = threadLocal.get();
        if (irng == null){
            irng = new RNGRandomImpl();
            threadLocal.set(irng);
        }

        return irng;
    }
}
