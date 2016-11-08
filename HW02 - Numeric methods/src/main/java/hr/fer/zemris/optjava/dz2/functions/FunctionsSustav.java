package hr.fer.zemris.optjava.dz2.functions;

import Jama.Matrix;
import hr.fer.zemris.optjava.dz2.IHFunction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class FunctionsSustav implements IHFunction{
    private List< List<Double> > functions;
    private double epsilon;

    public FunctionsSustav(List<String> functions, double epsilon) {
        this.epsilon = epsilon;
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
    public Matrix getHessMatrix(Matrix arguments) {
        int n = numberOfVariables();
        Matrix hess = new Matrix(n, n);

        for (int i = 0; i < n; ++i){
            for (int j = 0; j < n; ++j){
                double value = 0;
                for (int k = 0; k < n; ++k){
                    value += functions.get(k).get(i) * functions.get(k).get(j) * 2;
                }
                hess.set(i, j, value);
            }
        }

        return hess;
    }

    @Override
    public int numberOfVariables() {
        return functions.get(0).size() - 1;
    }

    @Override
    public double value(Matrix arguments) {
        FunctionUtility.checkArguments(arguments, this);
        double result  = 0;

        int n = numberOfVariables();
        for ( List<Double> function : functions ){
            double functionResult = 0;
            for (int i = 0; i < n; ++i){
                functionResult += function.get(i) * arguments.get(i, 0);
            }
            functionResult -= function.get(n);

            result += functionResult * functionResult;
        }

        return result;
    }

    @Override
    public Matrix getGradient(Matrix arguments) {
        return FunctionUtility.getGradient(arguments, epsilon, this);
    }
}
