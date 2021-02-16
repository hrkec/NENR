package fer.nenr.zad7;

import java.util.Random;

public class NeuronskaMreza {
    private int[] layers;
    private double[] neurons;
    private double[] parameters;
    private Random random;

    public NeuronskaMreza(int[] layers){
        this.random = new Random();
        this.layers = layers;
        int numOfNeurons = 0;
        for(int layerSize : layers){
            numOfNeurons += layerSize;
        }
        this.neurons = new double[numOfNeurons];
        this.parameters = new double[numOfParameters()];
        for(int i = 0; i < this.parameters.length; i++){
            parameters[i] = fRand(-1, 1);
        }
    }

    public static int numOfParameters(int[] layers){
        int result = layers[1] * layers[0] * 2;
        for(int i = 2; i < layers.length; i++){
            result += layers[i] * (layers[i - 1] + 1);
        }
        return result;
    }

    public int numOfParameters(){
        int result = this.layers[1] * this.layers[0] * 2;
        for(int i = 2; i < this.layers.length; i++){
            result += this.layers[i] * (this.layers[i - 1] + 1);
        }
        return result;
    }

    public int numOfNeurons(){
        return this.neurons.length;
    }

    public double[] calcOutput(Double[] input){
        int numOfInput = this.layers[0];
        int i = 0;
        for(; i < numOfInput; i++){
            this.neurons[i] = input[i];
        }

        int l = 0;
        for(; i < numOfInput + this.layers[1]; i++){
            double sum = 0;
            for(int k = 0; k < 2; k++){
                sum += Math.abs((this.neurons[k] - parameters[l++]) / (parameters[l++]));
            }
            this.neurons[i] = 1. / (1 + sum);
        }

        int sizeTotal = 0;
        for(int k = 2; k < this.layers.length; k++){
            int layerSize = this.layers[k];
            int previousLayerSize = this.layers[k - 1];
            sizeTotal += layerSize;
            for(; i < numOfInput + this.layers[1] + sizeTotal; i++){
                double sum = 0;
                for(int j = 0; j < previousLayerSize; j++){
                    sum += neurons[j + numOfInput + this.layers[1] + sizeTotal - layerSize - previousLayerSize] * parameters[l++];
                }
                neurons[i] = sigmoid(sum + parameters[l++]);
            }
        }

        double[] output = new double[this.layers[this.layers.length - 1]];
        for(int k = 0; k < output.length; k++){
            output[k] = this.neurons[this.neurons.length - output.length + k];
        }

        return output;
    }

    public double[] calcOutput(Double[] input, double[] parameters){
        int numOfInput = this.layers[0];
        int i = 0;
        for(; i < numOfInput; i++){
            this.neurons[i] = input[i];
        }

        // Neuroni tipa 1
        int l = 0;
        for(; i < numOfInput + this.layers[1]; i++){
            double sum = 0;
            for(int k = 0; k < 2; k++){
                sum += Math.abs((this.neurons[k] - parameters[l++]) / (parameters[l++]));
            }
            this.neurons[i] = 1. / (1 + sum);
        }

        // Ostali slojevi
        int sizeTotal = 0;
        for(int k = 2; k < this.layers.length; k++){
            int layerSize = this.layers[k];
            int previousLayerSize = this.layers[k - 1];
            sizeTotal += layerSize;
            for(; i < numOfInput + this.layers[1] + sizeTotal; i++){
                double sum = 0;
                for(int j = 0; j < previousLayerSize; j++){
                    sum += neurons[j + numOfInput + this.layers[1] + sizeTotal - layerSize - previousLayerSize] * parameters[l++];
                }
                neurons[i] = sigmoid(sum + parameters[l++]);
            }
        }

        // Izlaz
        double[] output = new double[this.layers[this.layers.length - 1]];
        for(int k = 0; k < output.length; k++){
            output[k] = this.neurons[this.neurons.length - output.length + k];
        }

        return output;
    }

    public double calcError(Dataset dataset, double[] parameters){
        double mse = 0, error;
        for(int i = 0; i < dataset.size(); i++){
            Double[] input = dataset.inputAtIndex(i);
            Integer[] output = dataset.outputAtIndex(i);
            double[] outputCalc = calcOutput(input, parameters);
            for(int j = 0; j < output.length; j++){
                error = output[j] - outputCalc[j];
                mse += (error * error);
            }
        }
        mse /= dataset.size();
        return mse;
    }

    public double calcError(Dataset dataset){
        double mse = 0, error;
        for(int i = 0; i < dataset.size(); i++){
            Double[] input = dataset.inputAtIndex(i);
            Integer[] output = dataset.outputAtIndex(i);
            double[] outputCalc = calcOutput(input, parameters);
            for(int j = 0; j < output.length; j++){
                error = output[j] - outputCalc[j];
                mse += (error * error);
            }
        }
        mse /= dataset.size();
        return mse;
    }

    private double fRand(double fMin, double fMax){
        return fMin + random.nextDouble() * (fMax - fMin);
    }

    public double sigmoid(double value){
        return 1 / (1 + Math.exp(-value));
    }
}
