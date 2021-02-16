package fer.nenr.zad5;

import java.util.Random;

public class Neuron {
    private double value;
    private double[] weights;
    private double bias;
    private double delta;
    private final Random random;

    public Neuron(int previousLayerSize) {
        this.random = new Random();
        double number = 2.4 / previousLayerSize;
        weights = new double[previousLayerSize];
        delta = 0;
        value = 0;

        bias = fRand(-number, number);
        for(int i = 0; i < previousLayerSize; i++){
            weights[i] = fRand(-number, number);
        }

    }

    private double fRand(double fMin, double fMax){
        return fMin + random.nextDouble() * (fMax - fMin);
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double[] getWeights() {
        return weights;
    }

    public void setWeights(double[] weights) {
        this.weights = weights;
    }

    public double getBias() {
        return bias;
    }

    public void setBias(double bias) {
        this.bias = bias;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

}
