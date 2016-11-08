package hr.fer.zemris.optjava.dz2;

import Jama.Matrix;
import hr.fer.zemris.optjava.dz2.functions.Function1;
import hr.fer.zemris.optjava.dz2.functions.Function2;

import java.util.DoubleSummaryStatistics;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class Jednostavno {
    public static void main(String[] args) {
        if (args.length != 2 && args.length != 4){
            throw new IllegalArgumentException("Expected 2 or 4 arguments, but got " + args.length);
        }

        Matrix x;

        if (args.length == 4){
            double x1 = Double.parseDouble(args[2]);
            double x2 = Double.parseDouble(args[3]);

            x = new Matrix(new double[][] {{x1},{x2}});
        } else {
            x = Matrix.random(1, 2);
        }

        int maxIteration = Integer.parseInt(args[1]);

        IHFunction function;
        switch (args[0]){
            case "1a":
                function = new Function1();
                x = NumOptAlgorithms.gradientDescent(function, maxIteration, x, true);
                break;
            case "1b":
                function = new Function1();
                x = NumOptAlgorithms.newtonMethod(function, maxIteration, x, true);
                break;
            case "2a":
                function = new Function2();
                x = NumOptAlgorithms.gradientDescent(function, maxIteration, x, true);
                break;
            case "2b":
                function = new Function2();
                x = NumOptAlgorithms.newtonMethod(function, maxIteration, x, true);
                break;
        }
    }
}
