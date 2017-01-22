package test;

import hr.fer.zemris.optjava.rng.EVOThread;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Test2 {
    public static void main(String[] args) {
        Runnable job = () -> {
            IRNG rng = RNG.getRNG();
            for (int i = 0; i < 20; i++) {
                System.out.println(rng.nextInt(-5, 5));
            }
        };
        EVOThread thread = new EVOThread(job);
        thread.start();
    }
}
