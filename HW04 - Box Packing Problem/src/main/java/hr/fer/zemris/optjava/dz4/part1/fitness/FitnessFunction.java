package hr.fer.zemris.optjava.dz4.part1.fitness;

import hr.fer.zemris.optjava.dz4.part1.chromosome.Chromosome;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class FitnessFunction implements IFitnessFunction {
    private List<List<Double>> functions;

    public FitnessFunction(List<String> functions) {
        this.functions = new ArrayList<>();

        Iterator<String> function = functions.iterator();

        while (function.hasNext()){
            String x = function.next().trim();
            if (x.startsWith("#")){
                continue;
            }

            x = x.substring(1, x.length() - 1);
            String[] lineNumbers = x.split(",\\s*");

            List<Double> numberList = new ArrayList<>();
            for (String s : lineNumbers){
                numberList.add(Double.parseDouble(s));
            }

            this.functions.add(numberList);
        }
    }

    @Override
    public double fitness(Chromosome x) {
        double result  = 0;

        double a = x.getValue(0);
        double b = x.getValue(1);
        double c = x.getValue(2);
        double d = x.getValue(3);
        double e = x.getValue(4);
        double f = x.getValue(5);

        for ( List<Double> function : functions ){
            double functionResult = 0;

            double x1 = function.get(0);
            double x2 = function.get(1);
            double x3 = function.get(2);
            double x4 = function.get(3);
            double x5 = function.get(4);
            double y = function.get(5);

            functionResult += a * x1;
            functionResult += b * x1 * x1 * x1 * x2;
            functionResult += c * Math.exp(d * x3) * (1 + Math.cos(e * x4));
            functionResult += f * x4 * x5 * x5;

            functionResult -= y;

            result += functionResult * functionResult;
        }

        return result;
    }
}
