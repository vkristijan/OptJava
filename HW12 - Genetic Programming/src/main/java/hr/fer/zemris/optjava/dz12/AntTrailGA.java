package hr.fer.zemris.optjava.dz12;

import hr.fer.zemris.optjava.dz12.GUI.JAntMap;
import hr.fer.zemris.optjava.dz12.GUI.StartFrame;
import hr.fer.zemris.optjava.dz12.genprog.Evaluator;
import hr.fer.zemris.optjava.dz12.genprog.Solution;
import hr.fer.zemris.optjava.dz12.genprog.crossover.Crossover;
import hr.fer.zemris.optjava.dz12.genprog.crossover.ICrossover;
import hr.fer.zemris.optjava.dz12.genprog.mutation.IMutation;
import hr.fer.zemris.optjava.dz12.genprog.mutation.Mutation;
import hr.fer.zemris.optjava.dz12.genprog.selection.ISelection;
import hr.fer.zemris.optjava.dz12.genprog.selection.TournamentSelection;
import hr.fer.zemris.optjava.dz12.node.terminal.TerminalNode;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class AntTrailGA {
    public static final int MAX_MOVES = 600;

    private static Map map;
    private static int maxInitDepth = 6;
    private static int maxDepth = 10;
    private static int maxNodes = 600;
    private static double p = 0.9;
    private static int populationSize;
    private static int maxIteration;
    private static int stopCondition;

    private static ISelection selection;
    private static IMutation mutation;
    private static ICrossover crossover;
    private static Evaluator evaluator;

    public static void main(String[] args) throws IOException {
        Path filePath = Paths.get(args[0]);
        map = Map.readFromFile(filePath);

        maxIteration = Integer.parseInt(args[1]);
        populationSize = Integer.parseInt(args[2]);
        stopCondition = Integer.parseInt(args[3]);

        selection = new TournamentSelection(7);
        mutation = new Mutation(maxDepth, maxNodes);
        crossover = new Crossover(maxDepth, maxNodes);
        evaluator = new Evaluator();

        List<Solution> population = GeneratePopulation();
        Solution sol = run(population);

        FileWriter fw = new FileWriter(args[4]);
        BufferedWriter bw = new BufferedWriter(fw);
        sol.write(bw);

        Map mapCopy = map.copy();
        List<TerminalNode> nodes = sol.getRoot().getActions(mapCopy);
        JAntMap antMap = new JAntMap(map, nodes);

        System.out.println("Best: " + sol.getFitness());
        evaluator.evaluate(sol, map);
        System.out.println("Best: " + sol.getFitness());
        SwingUtilities.invokeLater(() -> new StartFrame(antMap));
    }

    private static Random rnd = new Random();

    private static Solution run(List<Solution> population) {
        for (int iteration = 0; iteration < maxIteration; ++iteration){
            Solution best = getBest(population);
            if (best.getFitness() >= stopCondition){
                return best;
            }
            System.out.println(best.getFitness());
            List<Solution> newPopulation = new ArrayList<>();
            newPopulation.add(best);

            while (newPopulation.size() < populationSize){
                double x = rnd.nextDouble();
                Solution child;
                Solution parent;
                if (x <= 0.01){
                    parent = selection.select(population);
                    child = parent.copy();
                } else if (x <= 0.15){
                    parent = selection.select(population);

                    child = mutation.mutate(parent.copy());
                } else {
                    parent = selection.select(population);
                    Solution parent2 = selection.select(population);

                    child = crossover.crossover(parent, parent2);
                }
                evaluator.evaluate(child, map);
                if (child.getFitness() == parent.getFitness()){
                    child.setFitness((int)(child.getFitness() * p));
                }
                newPopulation.add(child);
            }
            population = newPopulation;
        }

        System.out.println(getBest(population).getFitness());
        return getBest(population);
    }

    private static List<Solution> GeneratePopulation() {
        List<Solution> population = new ArrayList<>();
        int count = maxDepth - 1;
        for (int j = 2; j <= maxInitDepth; ++j){
            for (int i = 0; i < populationSize / (2 * count); ++i){
                Solution solution = new Solution();
                solution.setRoot(Utility.generateTreeRoot(j, maxNodes, false));
                population.add(solution);
            }
            for (int i = 0; i < populationSize / (2 * count); ++i){
                Solution solution = new Solution();
                solution.setRoot(Utility.generateTreeRoot(j, maxNodes, true));
                population.add(solution);
            }
        }
        return population;
    }

    private static Solution getBest(List<Solution> population){
        population.sort(Comparator.reverseOrder());
        return population.get(0);
    }
}
