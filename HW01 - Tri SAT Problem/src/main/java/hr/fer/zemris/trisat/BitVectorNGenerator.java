package hr.fer.zemris.trisat;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BitVectorNGenerator implements Iterable<MutableBitVector> {
    private BitVector assignment;

    public BitVectorNGenerator(BitVector assignment) {
        this.assignment = assignment;
    }

    // Vraća iterator koji na svaki next() računa sljedećeg susjeda
    @Override public Iterator<MutableBitVector> iterator() {
        return new MutableBitVectorIterator();
    }

    // Vraća kompletno susjedstvo kao jedno polje
    public MutableBitVector[] createNeighborhood() {
        int size = assignment.getSize();
        MutableBitVector[] neighborhood = new MutableBitVector[size];

        int index = 0;
        for (MutableBitVector bitVector : this){
            neighborhood[index] = bitVector.copy();
        }

        return neighborhood;
    }

    private class MutableBitVectorIterator implements Iterator<MutableBitVector> {
        private MutableBitVector lastNeighbour;
        private int index = 0;

        public MutableBitVectorIterator(){
            this.lastNeighbour = assignment.copy();
        }

        @Override
        public boolean hasNext() {
            return index < lastNeighbour.getSize();
        }

        @Override
        public MutableBitVector next() {
            if (!hasNext()){
                throw new NoSuchElementException("There are no more elements in the neighbourhood.");
            }

            if (index > 0){
                swapBit(index - 1);
            }
            swapBit(index);
            index++;

            return lastNeighbour;
        }

        private void swapBit(int index){
            boolean currentValue = lastNeighbour.get(index);
            lastNeighbour.set(index, !currentValue);
        }
    }
}