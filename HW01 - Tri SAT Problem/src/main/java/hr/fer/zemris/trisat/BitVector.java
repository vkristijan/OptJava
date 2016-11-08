package hr.fer.zemris.trisat;

import java.util.Random;

public class BitVector {
    protected boolean[] bits;

    public BitVector(Random rand, int numberOfBits) {
        this.bits = new boolean[numberOfBits];

        for (int i = 0; i < numberOfBits; ++i) {
            bits[i] = rand.nextBoolean();
        }
    }

    public BitVector(boolean... bits) {
        this.bits = new boolean[bits.length];

        System.arraycopy(bits, 0, this.bits, 0, bits.length);
    }

    public BitVector(int n) {
        this.bits = new boolean[n];
    }

    // vraća vrijednost index-te varijable
    public boolean get(int index) {
        return bits[index];
    }

    // vraća broj varijabli koje predstavlja
    public int getSize() {
        return bits.length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (boolean bit : bits) {
            sb.append(bit ? "1" : "0");
        }

        return sb.toString();
    }

    // vraća promjenjivu kopiju trenutnog rješenja
    public MutableBitVector copy() {
        boolean[] bitsCopy = new boolean[getSize()];

        System.arraycopy(bits, 0, bitsCopy, 0, getSize());

        return new MutableBitVector(bitsCopy);
    }
}
