package hr.fer.zemris.optjava.dz5.part2.chromosome;

import hr.fer.zemris.optjava.dz5.part2.fitness.Fitness;

import java.util.*;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Chromosome {
    private int[] permutation;
    private Fitness fitness;
    private long fitnessValue;

    public Chromosome(int size, Fitness fitness){
        this.fitness = fitness;
        permutation = new int[size];

        for (int i = 0; i < size; ++i){
            permutation[i] = i;
        }

        shuffle(permutation);
        fitnessValue = this.fitness.fitness(this);
    }

    private static void shuffle(int[] arr){
        int index, temp;
        Random random = new Random();
        for (int i = arr.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            temp = arr[index];
            arr[index] = arr[i];
            arr[i] = temp;
        }
    }

    public Chromosome(Chromosome other){
        this.permutation = new int[other.permutation.length];
        System.arraycopy(other.permutation, 0, this.permutation, 0, other.permutation.length);

        this.fitness = other.fitness;
        this.fitnessValue = other.fitnessValue;
    }

    public int getValue(int index){
        return permutation[index];
    }

    public void setValue(int index, int value){
        permutation[index] = value;
        hashCalculated = false;
    }

    public long updateFitness(){
        this.fitnessValue = fitness.fitness(this);
        return fitnessValue;
    }

    public long getFitness(){
        return fitnessValue;
    }

    public int getSize(){
        return permutation.length;
    }

    private int hashValue;
    private boolean hashCalculated = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chromosome that = (Chromosome) o;

        if (fitnessValue != that.fitnessValue) return false;
        return Arrays.equals(permutation, that.permutation);

    }

    @Override
    public int hashCode() {
        if (hashCalculated) return hashValue;

        int result = Arrays.hashCode(permutation);
        result = 31 * result + (int) (fitnessValue ^ (fitnessValue >>> 32));
        hashValue = result;
        hashCalculated = true;
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Fitness: ");
        sb.append(getFitness());
        sb.append("  -  ");

        StringJoiner sj = new StringJoiner( ", ", "[", "]");
        for (int i : permutation){
            sj.add(String.valueOf(i));
        }

        sb.append(sj.toString());

        return sb.toString();
    }
}
