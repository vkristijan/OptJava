package hr.fer.zemris.optjava.dz2.functions;

import Jama.Matrix;
import hr.fer.zemris.optjava.dz2.IHFunction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static java.lang.Math.exp;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.pow;


/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class FunctionPrijenosna implements IHFunction {
    private List< List<Double> > functions;
    private double epsilon;

    public FunctionPrijenosna(List<String> functions, double epsilon) {
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
        FunctionUtility.checkArguments(arguments, this);
        int n = numberOfVariables();
        double[][] result = new double[n][n];

        for (int i = 0; i < n; ++i){
            for (int j = 0; j < n; ++j){
                result[i][j] = 0;
            }
        }

        double a = arguments.get(0, 0);
        double b = arguments.get(1, 0);
        double c = arguments.get(2, 0);
        double d = arguments.get(3, 0);
        double e = arguments.get(4, 0);
        double f = arguments.get(5, 0);

        for ( List<Double> function : functions ){
            double x1 = function.get(0);
            double x2 = function.get(1);
            double x3 = function.get(2);
            double x4 = function.get(3);
            double x5 = function.get(4);
            double y = function.get(5);

            result[0][0] += 2*pow(x1,2);
            result[0][1] += 2*pow(x1,4)*x2;
            result[0][2] += 2*x1*exp(d*x3)*(cos(e*x4) + 1);
            result[0][3] += 2*c*x1*x3*exp(d*x3)*(cos(e*x4) + 1);
            result[0][4] += -2*c*x1*x4*exp(d*x3)*sin(e*x4);
            result[0][5] += 2*x1*x4*pow(x5, 2);

            result[1][0] += 2*pow(x1,4)*x2;
            result[1][1] += 2*pow(x1,6)*pow(x2,2);
            result[1][2] += 2*pow(x1,3)*x2*exp(d*x3)*(cos(e*x4) + 1);
            result[1][3] += 2*c*pow(x1,3)*x2*x3*exp(d*x3)*(cos(e*x4) + 1);
            result[1][4] += -2*c*pow(x1,3)*x2*x4*exp(d*x3)*sin(e*x4);
            result[1][5] += 2*pow(x1,3)*x2*x4*pow(x5,2);

            result[2][0] += 2*x1*exp(d*x3)*(cos(e*x4) + 1);
            result[2][1] += 2*pow(x1,3)*x2*exp(d*x3)*(cos(e*x4) + 1);
            result[2][2] += 2*exp(2*d*x3)*pow((cos(e*x4) + 1),2);
            result[2][3] += 2*c*x3*exp(2*d*x3)*pow((cos(e*x4) + 1),2) + 2*x3*exp(d*x3)*(cos(e*x4) + 1)*(b*x2*pow(x1,3) + a*x1 + f*x4*pow(x5,2) - y + c*exp(d*x3)*(cos(e*x4) + 1));
            result[2][4] += - 2*x4*exp(d*x3)*sin(e*x4)*(b*x2*pow(x1,3) + a*x1 + f*x4*pow(x5,2) - y + c*exp(d*x3)*(cos(e*x4) + 1)) - 2*c*x4*exp(2*d*x3)*sin(e*x4)*(cos(e*x4) + 1);
            result[2][5] += 2*x4*pow(x5,2)*exp(d*x3)*(cos(e*x4) + 1);

            result[3][0] += 2*c*x1*x3*exp(d*x3)*(cos(e*x4) + 1);
            result[3][1] += 2*c*pow(x1,3)*x2*x3*exp(d*x3)*(cos(e*x4) + 1);
            result[3][2] += 2*c*x3*exp(2*d*x3)*pow((cos(e*x4) + 1),2) + 2*x3*exp(d*x3)*(cos(e*x4) + 1)*(b*x2*pow(x1,3) + a*x1 + f*x4*pow(x5,2) - y + c*exp(d*x3)*(cos(e*x4) + 1));
            result[3][3] += 2*c*c*pow(x3,2)*exp(2*d*x3)*pow((cos(e*x4) + 1),2) + 2*c*pow(x3,2)*exp(d*x3)*(cos(e*x4) + 1)*(b*x2*pow(x1,3) + a*x1 + f*x4*pow(x5,2) - y + c*exp(d*x3)*(cos(e*x4) + 1));
            result[3][4] += - 2*c*c*x3*x4*exp(2*d*x3)*sin(e*x4)*(cos(e*x4) + 1) - 2*c*x3*x4*exp(d*x3)*sin(e*x4)*(b*x2*pow(x1,3) + a*x1 + f*x4*pow(x5,2) - y + c*exp(d*x3)*(cos(e*x4) + 1));
            result[3][5] += 2*c*x3*x4*pow(x5,2)*exp(d*x3)*(cos(e*x4) + 1);

            result[4][0] += -2*c*x1*x4*exp(d*x3)*sin(e*x4);
            result[4][1] += -2*c*pow(x1,3)*x2*x4*exp(d*x3)*sin(e*x4);
            result[4][2] += - 2*x4*exp(d*x3)*sin(e*x4)*(b*x2*pow(x1,3) + a*x1 + f*x4*pow(x5,2) - y + c*exp(d*x3)*(cos(e*x4) + 1)) - 2*c*x4*exp(2*d*x3)*sin(e*x4)*(cos(e*x4) + 1);
            result[4][3] += - 2*c*c*x3*x4*exp(2*d*x3)*sin(e*x4)*(cos(e*x4) + 1) - 2*c*x3*x4*exp(d*x3)*sin(e*x4)*(b*x2*pow(x1,3) + a*x1 + f*x4*pow(x5,2) - y + c*exp(d*x3)*(cos(e*x4) + 1));
            result[4][4] += 2*c*c*pow(x4,2)*exp(2*d*x3)*pow(sin(e*x4),2) - 2*c*pow(x4,2)*exp(d*x3)*cos(e*x4)*(b*x2*pow(x1,3) + a*x1 + f*x4*pow(x5,2) - y + c*exp(d*x3)*(cos(e*x4) + 1));
            result[4][5] += -2*c*pow(x4,2)*pow(x5,2)*exp(d*x3)*sin(e*x4);

            result[5][0] += 2*x1*x4*pow(x5,2);
            result[5][1] += 2*pow(x1,3)*x2*x4*pow(x5,2);
            result[5][2] += 2*x4*pow(x5,2)*exp(d*x3)*(cos(e*x4) + 1);
            result[5][3] += 2*c*x3*x4*pow(x5,2)*exp(d*x3)*(cos(e*x4) + 1);
            result[5][4] += -2*c*pow(x4,2)*pow(x5,2)*exp(d*x3)*sin(e*x4);
            result[5][5] += 2*pow(x4,2)*pow(x5,4);
        }

        return new Matrix(result);
    }

    @Override
    public int numberOfVariables() {
        return 6;
    }

    @Override
    public double value(Matrix arguments) {
        FunctionUtility.checkArguments(arguments, this);
        double result  = 0;

        double a = arguments.get(0, 0);
        double b = arguments.get(1, 0);
        double c = arguments.get(2, 0);
        double d = arguments.get(3, 0);
        double e = arguments.get(4, 0);
        double f = arguments.get(5, 0);

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

        return result / 100;
    }

    @Override
    public Matrix getGradient(Matrix arguments) {
        return FunctionUtility.getGradient(arguments, epsilon, this);
    }
}
