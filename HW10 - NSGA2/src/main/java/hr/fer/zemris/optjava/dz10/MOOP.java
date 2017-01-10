package hr.fer.zemris.optjava.dz10;

import hr.fer.zemris.optjava.dz10.nsga.Chromosome;
import hr.fer.zemris.optjava.dz10.nsga.NSGA2;
import hr.fer.zemris.optjava.dz10.nsga.crossover.Crossover;
import hr.fer.zemris.optjava.dz10.nsga.crossover.ICrossover;
import hr.fer.zemris.optjava.dz10.nsga.function.*;
import hr.fer.zemris.optjava.dz10.nsga.moop.Evaluate;
import hr.fer.zemris.optjava.dz10.nsga.moop.MOOPProblem;
import hr.fer.zemris.optjava.dz10.nsga.moop.MOOPProblemImpl;
import hr.fer.zemris.optjava.dz10.nsga.mutation.IMutation;
import hr.fer.zemris.optjava.dz10.nsga.mutation.Mutation;
import hr.fer.zemris.optjava.dz10.nsga.selection.CrowdedSortSeelction;
import hr.fer.zemris.optjava.dz10.nsga.selection.ISelection;
import hr.fer.zemris.optjava.dz10.nsga.selection.TournamentSelection;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class MOOP {
    private static double epsilon = 0.9;
    private static double sigma = 0.4;
    private static double sigmaShare = 0.1;
    private static double alpha = 2;
    private static int tournamentSize = 2;

    public static void main(String[] args) throws IOException{
        if (args.length != 4){
            System.err.println("Wrong number of arguments! 4 arguments expected!");
        }
        int populationSize = Integer.parseInt(args[1]);
        boolean decisionSpace = Boolean.parseBoolean(args[2]);
        int maxIteration = Integer.parseInt(args[3]);

        MOOPProblem moop = null;
        double[] xmin;
        double[] xmax;

        if (args[0].equals("1")){
            List<IFunction> functions = new ArrayList<>();
            functions.add(new SquareFunction(0));
            functions.add(new SquareFunction(1));
            functions.add(new SquareFunction(2));
            functions.add(new SquareFunction(3));

            List<IConstraint> constraints = new ArrayList<>();
            constraints.add(new IntervalContraint(0, -5, 5));
            constraints.add(new IntervalContraint(1, -5, 5));
            constraints.add(new IntervalContraint(2, -5, 5));
            constraints.add(new IntervalContraint(3, -5, 5));
            moop = new MOOPProblemImpl(functions, constraints, 4);
            xmin = new double[]{-5, -5, -5, -5};
            xmax = new double[]{5, 5, 5, 5};
        } else {
            List<IFunction> functions = new ArrayList<>();
            functions.add(new LinearFunction(0));
            functions.add(new Function2());

            List<IConstraint> constraints = new ArrayList<>();
            constraints.add(new IntervalContraint(0, 0.1, 1));
            constraints.add(new IntervalContraint(1, 0, 5));
            moop = new MOOPProblemImpl(functions, constraints, 2);
            xmin = new double[]{0, 0.1};
            xmax = new double[]{1, 5};
        }

        Evaluate evaluate = new Evaluate(moop, sigmaShare, alpha, xmin, xmax, epsilon);
        ISelection firstSelection = new CrowdedSortSeelction(tournamentSize);
        ISelection secondSelection = new CrowdedSortSeelction(tournamentSize);
        ICrossover crossover = new Crossover();
        IMutation mutation = new Mutation(sigma);

        NSGA2 nsga = new NSGA2(moop, evaluate, populationSize, maxIteration,
                firstSelection, secondSelection, mutation, crossover, xmin, xmax);

        List<Chromosome> population = nsga.run();
        List<List<Chromosome>> fronts = evaluate.evaluate(population);

        PrintWriter writerS = new PrintWriter("izlaz-dec.txt");
        PrintWriter writerO = new PrintWriter("izlaz-obj.txt");
        for (List<Chromosome> front : fronts) {
            System.out.println(front.size());
            for (Chromosome c : front) {
                String str = "";
                for (double d : c.getSolution()) {
                    str += d + " ";
                }
                writerS.write(str + "\n");
                str = "";
                for (double d : c.getObjective()) {
                    str += d + " ";
                }
                writerO.write(str + "\n");
            }
            writerS.write("-------\n");
            writerO.write("-------\n");
        }
        writerS.close();
        writerO.close();
    }
}
