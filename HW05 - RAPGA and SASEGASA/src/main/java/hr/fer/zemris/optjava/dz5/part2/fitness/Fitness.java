package hr.fer.zemris.optjava.dz5.part2.fitness;

import hr.fer.zemris.optjava.dz5.part2.chromosome.Chromosome;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Fitness {
    private int n;
    private int[][] dist;
    private int[][] price;

    public Fitness(int n, int[][] dist, int[][] price){
        this.n = n;
        this.dist = dist;
        this.price = price;
    }

    public long fitness(Chromosome chromosome){
        long fitness = 0;

        for (int i = 0; i < n; ++i){
            for (int j = 0; j < n; ++j){
                fitness += price[i][j] * dist[chromosome.getValue(i)][chromosome.getValue(j)];
            }
        }

        return fitness;
    }
}
