package hr.fer.zemris.trisat;

public class MutableBitVector extends BitVector {
    public MutableBitVector(boolean... bits) {
        this.bits = bits;
    }

    public MutableBitVector(int n) {
        this.bits = new boolean[n];
    }

    // zapisuje predanu vrijednost u zadanu varijablu
    public void set(int index, boolean value) {
        bits[index] = value;
    }
}