package hr.fer.zemris.optjava.dz6;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Ant {
    private static Random rnd = new Random();

    private double distance;
    private List<Integer> order;

    public Ant(double[][] matrix, double[][] feromon, double alpha, double beta,
               int candidateSize, double[][] distances, List<Integer>[] candidates) {
        int size = matrix.length;
        order = new ArrayList<>();

        int current = rnd.nextInt(size);
        int first = current;
        order.add(current);
        Set<Integer> toVisit = new HashSet<>();

        for (int i = 0; i < size; ++i){
            if (i == current) continue;
            toVisit.add(i);
        }

        while (!toVisit.isEmpty()){
            List<Integer> neighbours = new ArrayList<>(candidates[current]);

            neighbours = neighbours.stream()
                                   .limit(candidateSize)
                                   .filter(toVisit::contains)
                                   .collect(Collectors.toList());

            int neighbourIndex = 0;
            if (neighbours.size() > 0){
                double sum = 0;
                for (int index : neighbours){
                    sum += matrix[current][index] * Math.pow(feromon[current][index], alpha);
                }

                double rand = rnd.nextDouble();
                double tmpSum = 0;

                neighbourIndex = 0;
                while (tmpSum < rand){
                    int index = neighbours.get(neighbourIndex);
                    tmpSum += (matrix[current][index] * Math.pow(feromon[current][index], alpha)) / sum;

                    if (tmpSum > rand) {
                        neighbourIndex = index;
                        break;
                    }
                    neighbourIndex++;
                }

            } else {
                int index = rnd.nextInt(toVisit.size());
                for (int neighbour : toVisit){
                    if (index == 0){
                        neighbourIndex = neighbour;
                        break;
                    }
                    index--;
                }
            }

            toVisit.remove(neighbourIndex);
            distance += distances[current][neighbourIndex];
            current = neighbourIndex;
            order.add(neighbourIndex);
        }

        distance += distances[current][first];
    }

    public double getDistance(){
        return distance;
    }

    public List<Integer> getOrder() {
        return order;
    }
}
