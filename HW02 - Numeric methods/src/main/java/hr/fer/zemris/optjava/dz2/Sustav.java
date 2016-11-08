package hr.fer.zemris.optjava.dz2;

import Jama.Matrix;
import hr.fer.zemris.optjava.dz2.functions.Function1;
import hr.fer.zemris.optjava.dz2.functions.FunctionsSustav;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Sustav {
    private static final double epsilon = 0.0001;

    public static void main(String[] args) {
        if (args.length != 3){
            System.err.println("Invalid number of arguments! Expected 3 but got " + args.length);
            System.exit(1);
        }

        String filePath = args[2];
        List<String> lines = null;

        try {
            lines = Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            System.err.println("Unable to open the given file or file not found!");
            System.exit(1);
        }

        IHFunction function = new FunctionsSustav(lines, epsilon);

        int maxIterations = Integer.parseInt(args[1]);
        Matrix x = Matrix.random(function.numberOfVariables(), 1);

        switch (args[0]){
            case "grad":
                x = NumOptAlgorithms.gradientDescent(function, maxIterations, x, false);
                break;
            case "newton":
                x = NumOptAlgorithms.newtonMethod(function, maxIterations, x, false);
                break;
            default:
                System.err.println(
                        "Unsupported operation, choose 'grad' for gradient descent and 'newton' for newton's method."
                );
                System.exit(1);
        }

        NumOptAlgorithms.printX(x);
        System.out.println(function.value(x));
    }
}
