package fer.nenr.zad5;

public class ActivationFunction {
    public double evaluate(double value){
        return 1 / (1 + Math.exp(-value));
    }

    public double evaluateDerivative(double value){
        return (value - Math.pow(value, 2));
    }
}
