package hr.fer.zemris.trisat;

import java.util.ArrayList;
import java.util.List;

public class Clause {
    private int[] indexes;

    public Clause(String line){
        List<Integer> indexes = new ArrayList<>();

        String[] numbers = line.split(" +");
        for (String number : numbers){
            try {
                int value = Integer.parseInt(number);
                if (value == 0) break;
                indexes.add(value);

            } catch (NumberFormatException e){
                throw new IllegalArgumentException("The given input is not valid!", e);
            }
        }
        this.indexes = indexes.stream().mapToInt(Integer::intValue).toArray();
    }

    public Clause(int[] indexes) {
        this.indexes = indexes;
    }

    // vraća broj literala koji čine klauzulu
    public int getSize() {
        return indexes.length;
    }

    // vraća indeks varijable koja je index-ti član ove klauzule
    public int getLiteral(int index) {
        if (index < 0 || index > getSize()) {
            throw new IndexOutOfBoundsException("There is no literal at the given clause index.");
        }
        return Math.abs(indexes[index]) - 1;
    }

    // vraća true ako predana dodjela zadovoljava ovu klauzulu
    public boolean isSatisfied(BitVector assignment) {
        for (int i = 0; i < getSize(); ++i) {
            boolean value = assignment.get(getLiteral(i));
            if ((value && indexes[i] > 0) || (!value && indexes[i] < 0)){
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < getSize(); ++i){
            sb.append(getLiteral(i));
            sb.append(" ");
        }

        return sb.toString();
    }
}
