package hr.fer.zemris.trisat;

import hr.fer.zemris.trisat.algorithms.Algorithm1;
import hr.fer.zemris.trisat.algorithms.Algorithm2;
import hr.fer.zemris.trisat.algorithms.Algorithm3;
import hr.fer.zemris.trisat.algorithms.TriSATAlgorithm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class TriSATSolver {

    public static void main(String[] args) {
        if (args.length != 2){
            throw new IllegalArgumentException("Wrong number of arguments. Expected 2 arguments!");
        }

        Path path = Paths.get(args[1]);
        List<String> lines;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to open the given file for reading.", e);
        }

        Clause[] clauses = null;
        boolean isFirstLine = true;
        int numberOfVariables = 0;
        int numberOfClauses;
        int index = 0;

        for (String line : lines){
            line = line.trim();
            if (line.startsWith("c")) continue;
            if (line.startsWith("%")) break;

            if (isFirstLine){
                isFirstLine = false;

                line = line.substring(6).trim(); //ignore the beginning "p cnf "
                String lineData[] = line.split(" +");

                if (lineData.length != 2){
                    throw new IllegalArgumentException("There are expected to be exactly 2 numbers in the first line");
                }

                numberOfVariables = Integer.parseInt(lineData[0]);
                numberOfClauses = Integer.parseInt(lineData[1]);

                clauses = new Clause[numberOfClauses];
                continue;
            }

            Clause clause = new Clause(line);
            clauses[index++] = clause;
        }

        SATFormula formula = new SATFormula(numberOfVariables, clauses);

        TriSATAlgorithm algorithm;
        switch (args[0]){
            case "1":
                algorithm = new Algorithm1(formula);
                break;
            case "2":
                algorithm = new Algorithm2(formula);
                break;
            case "3":
                algorithm = new Algorithm3(formula);
                break;
            default:
                throw new IllegalArgumentException("The given algorithm is not supported!");
        }

        algorithm.solve();
    }
}
