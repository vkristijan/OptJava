package hr.fer.zemris.optjava.dz3.solution;

import java.util.BitSet;
import java.util.Random;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class BitvectorSolution extends SingleObjectiveSolution {
    private BitSet bits;

    public BitvectorSolution(int size) {
        this.bits = new BitSet(size);
    }

    public BitvectorSolution newLikeThis() {
        return new BitvectorSolution(bits.size());
    }

    public BitvectorSolution duplicate() {
        int n = bits.size();
        BitvectorSolution other = new BitvectorSolution(n);

        other.bits.or(this.bits);

        return other;
    }

    public void randomize(Random rnd) {
        int n = bits.size();

        for (int i = 0; i < n; ++i) {
            bits.set(i, rnd.nextBoolean());
        }
    }

    public boolean getBit(int index) {
        return bits.get(index);
    }

    public void flipBit(Random rnd){
        int n = bits.size();
        bits.flip(rnd.nextInt(n));
    }
}
