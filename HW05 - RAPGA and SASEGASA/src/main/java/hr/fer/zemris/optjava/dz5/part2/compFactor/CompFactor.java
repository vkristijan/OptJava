package hr.fer.zemris.optjava.dz5.part2.compFactor;

/**
 * @author Kristijan Vulinovic
 * @version 1.0.0
 */
public class CompFactor {
    private double pressure;
    private double compFactor;

    public CompFactor(double pressure){
        this.pressure = pressure;
        this.compFactor = 0.2;
    }

    public void nextCompFactor(){
        compFactor += pressure;
        if (compFactor > 1){
            compFactor = 1;
        }
    }

    public double getCompFactor(){
        return compFactor;
    }
}
