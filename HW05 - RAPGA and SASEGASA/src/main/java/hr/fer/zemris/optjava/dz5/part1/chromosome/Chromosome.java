package hr.fer.zemris.optjava.dz5.part1.chromosome;

import java.util.BitSet;
import java.util.Random;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Chromosome {
    private static final Random rnd = new Random();
    private BitSet bitSet;
    private int size;

    private int hashCode;
    private boolean calculatedHash = false;

    public Chromosome(int size){
        if (size <= 0){
            throw new IllegalArgumentException("The given chromosome size is not allowed!");
        }

        this.size = size;
        bitSet = new BitSet(size);
        for (int i = 0; i < size; ++i){
            bitSet.set(i, rnd.nextBoolean());
        }
    }

    public Chromosome(Chromosome other){
        if (other == null){
            throw new IllegalArgumentException("The other chromosome is not allowed to be null!");
        }

        this.size = other.size;
        this.bitSet = new BitSet(size);

        for (int i = 0; i < size; ++i){
            this.bitSet.set(i, other.getBit(i));
        }
    }

    public void flipBit(int index){
        checkIndex(index);
        bitSet.flip(index);
        calculatedHash = false;
    }

    public boolean getBit(int index){
        checkIndex(index);
        return bitSet.get(index);
    }

    public void setBit(int index, boolean value){
        checkIndex(index);
        bitSet.set(index, value);
        calculatedHash = false;
    }

    public int size(){
        return this.size;
    }

    public int getActive(){
        int n = 0;

        for (int i = 0; i < size; ++i){
            if (bitSet.get(i)){
                n++;
            }
        }

        return n;
    }

    private void checkIndex(int index){
        if (index < 0 || index >= size){
            throw new IllegalArgumentException("Illegal index.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chromosome that = (Chromosome) o;

        if (this.hashCode() != that.hashCode()){
            return false;
        }
        return bitSet.equals(that.bitSet);

    }

    @Override
    public int hashCode() {
        if (calculatedHash){
            return hashCode;
        }

        hashCode = bitSet.hashCode();
        return hashCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < size; ++i){
            sb.append(bitSet.get(i) ? "1" : "0");
        }

        return sb.toString();
    }
}
