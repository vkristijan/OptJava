package hr.fer.zemris.optjava.dz6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class TSPSolver {
    private static Path filepath;
    private static int candidateSize;
    private static int colonySize;
    private static int maxIter;

    private static double alpha = 1.2;
    private static double beta = 2.3;
    private static double a = 100;
    private static double ro = 0.02;
    private static int maxStagnation;

    private static int dimension;
    private static double[][] distances;
    private static double[][] matrix;
    private static double[][] feromon;
    private static List<Integer>[] candidates;
    private static double maxFeromon;
    private static double minFeromon;

    private static Ant bestAnt;

    public static void main(String[] args) {
        if (args.length != 4){
            System.err.println("Invalid number of arguments!");
            System.exit(1);
        }

        filepath = Paths.get(args[0]);
        candidateSize = Integer.parseInt(args[1]);
        colonySize = Integer.parseInt(args[2]);
        maxIter = Integer.parseInt(args[3]);
        maxStagnation = (int)(0.5 * maxIter);

        matrix = readInput();
        initFeromon();

        generateCandidates();

        solve();
    }

    private static void generateCandidates() {
        candidates = new ArrayList[dimension];

        for (int i = 0; i < dimension; ++i) {
            List<Pair<Double, Integer>> neighbours = new ArrayList<>();
            for (int j = 0; j < dimension; ++j) {
                if (j == i) continue;

                neighbours.add(new Pair(distances[i][j], j));
            }
            neighbours.sort(Comparator.naturalOrder());
            neighbours = neighbours.stream()
                    .limit(candidateSize)
                    .collect(Collectors.toList());

            candidates[i] = new ArrayList<>();
            for (Pair<Double, Integer> neighbour : neighbours){
                candidates[i].add(neighbour.getY());
            }
        }
    }

    private static void solve() {
        int stagnationCounter = 0;

        for (int i = 0; i < maxIter; ++i){
            Ant currentBest = new Ant(matrix, feromon, alpha, beta, candidateSize, distances, candidates);
            for (int j = 0; j < colonySize; ++j){
                Ant newAnt = new Ant(matrix, feromon, alpha, beta, candidateSize, distances, candidates);
                if (newAnt.getDistance() < currentBest.getDistance()){
                    currentBest = newAnt;
                }

            }

            updateFeromons(currentBest, i);

            if (bestAnt == null || currentBest.getDistance() < bestAnt.getDistance()){
                bestAnt = currentBest;
                stagnationCounter = 0;
            } else {
                stagnationCounter++;
                if (stagnationCounter > maxStagnation){
                    stagnationCounter = 0;
                    initFeromon();
                }
            }
            System.out.println(bestAnt.getDistance());
        }
    }

    private static void updateFeromons(Ant currentBest, int iteration) {
        for (int i = 0; i < dimension; ++i){
            for (int j = 0; j < dimension; ++j){
                feromon[i][j] *= (1.0 - ro);

                if (feromon[i][j] < minFeromon){
                    feromon[i][j] = minFeromon;
                }
            }
        }

        Ant ant;
        if (iteration > maxIter * 0.85){
            ant = bestAnt;
        } else {
            ant = currentBest;
        }

        double feromonChange = 1 / ant.getDistance();

        List<Integer> order = ant.getOrder();
        int size = order.size();
        for (int i = 0; i < size; ++i){
            int from = order.get(i);
            int nextIndex = i + 1;
            if (nextIndex >= size){
                nextIndex = 0;
            }
            int to = order.get(nextIndex);

            feromon[from][to] += feromonChange;
            feromon[to][from] += feromonChange;

            if (feromon[from][to] > maxFeromon){
                feromon[from][to] = maxFeromon;
                feromon[to][from] = maxFeromon;
            }
        }
    }

    private static void initFeromon() {
        updateMaxFeromon();
        minFeromon = maxFeromon / a;

        for (int i = 0; i < dimension; ++i){
            for (int j = 0; j < dimension; ++j){
                feromon[i][j] = maxFeromon;
            }
        }
    }

    private static void updateMaxFeromon() {
        double bestSolution;

        if (bestAnt != null){
            bestSolution = bestAnt.getDistance();
        } else {
            bestSolution = greedySolution();
        }

        maxFeromon = 1.0 / (ro * bestSolution);
    }

    private static double greedySolution() {
        double distance = 0;

        int current = 0;
        Set<Integer> toVisit = new HashSet<>();

        for (int i = 1; i < dimension; ++i){
            toVisit.add(i);
        }

        while (!toVisit.isEmpty()){
            double minDist = 0;
            int closest = -1;

            for (int next : toVisit){
                if (closest == -1 || distances[current][next] < minDist){
                    minDist = distances[current][next];
                    closest = next;
                }
            }

            toVisit.remove(closest);
            current = closest;
            distance += minDist;
        }

        distance += distances[current][0];
        return distance;
    }

    private static double[][] readInput() {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(filepath);
        } catch (IOException e) {
            System.err.println("Unable to open the given file!");
            System.exit(1);
        }

        for (String line : lines){
            line = line.trim();
            if (line.startsWith("DIMENSION")){
                line = line.substring("DIMENSION: ".length()).trim();
                dimension = Integer.parseInt(line);
                distances = new double[dimension][dimension];
                feromon = new double[dimension][dimension];
            }

            if (line.startsWith("EDGE_WEIGHT_TYPE")){
                if (line.endsWith("EXPLICIT")){
                    return readExplicit(lines);
                } else {
                    return readEuclid(lines);
                }
            }
        }

        return null;
    }

    private static double[][] readEuclid(List<String> lines) {
        double[][] matrix = new double[dimension][dimension];

        int index = 0;
        while (!lines.get(index).contains("SECTION")){
            index++;
        }
        index++;

        Coordinate[] coordinates = new Coordinate[dimension];
        for (int i = 0; i < dimension; ++i){
            String line = lines.get(index + i).trim();
            String[] lineNums = line.split("\\s+");

            if (lineNums.length != 3){
                System.err.println("Invalid input data!");
                System.exit(1);
            }

            double x = Double.parseDouble(lineNums[1]);
            double y = Double.parseDouble(lineNums[2]);
            coordinates[i] = new Coordinate(x, y);
        }

        for (int i = 0; i < dimension; ++i){
            for (int j = 0; j < dimension; ++j){
                distances[i][j] = coordinates[i].distance(coordinates[j]);

                matrix[i][j] = Math.pow((1.0 / distances[i][j]), beta);
            }
        }

        return matrix;
    }

    private static double[][] readExplicit(List<String> lines) {
        double[][] matrix = new double[dimension][dimension];

        int index = 0;
        while (!lines.get(index).contains("SECTION")){
            index++;
        }
        index++;

        /*lines.removeAll(lines.stream().limit(index).collect(Collectors.toList()));
        return readEuclid(lines);*/

        for (int i = 0; i < dimension; ++i){
            String line = lines.get(index + i).trim();
            String[] lineNums = line.split(" +");

            if (lineNums.length != dimension){
                System.err.println("Invalid input data!");
                System.exit(1);
            }

            for (int j = 0; j < dimension; ++j){
                distances[i][j] = Double.parseDouble(lineNums[j]);

                matrix[i][j] = Math.pow((1.0 / distances[i][j]), beta);
            }
        }

        return matrix;
    }
}
