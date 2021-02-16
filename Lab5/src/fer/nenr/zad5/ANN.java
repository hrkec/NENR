package fer.nenr.zad5;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ANN  {
    private final double learningRate;
    private final Layer[] layers;
    private final ActivationFunction activationFunction;

    public ANN(int[] layers, double learningRate){
        this.learningRate = learningRate;
        this.activationFunction = new ActivationFunction();
        this.layers = new Layer[layers.length];

        for(int i = 0; i < layers.length; i++){
            if(i != 0){
                this.layers[i] = new Layer(layers[i], layers[i - 1]);
            } else {
                this.layers[i] = new Layer(layers[i], 0);
            }
        }
    }

    public double[] forwardPass(double[] input) {
        double newValue;
        double[] output = new double[this.layers[this.layers.length - 1].getSize()];

        // Ulazne vrijednost
        for(int i = 0; i < this.layers[0].getSize(); i++){
            this.layers[0].getNeurons()[i].setValue(input[i]);
        }

        // Unaprijedni prolaz
        for(int i = 1; i < this.layers.length; i++)
        {
            for(int j = 0; j < this.layers[i].getSize(); j++)
            {
                newValue = 0.0;
                for(int k = 0; k < this.layers[i - 1].getSize(); k++)
                    newValue += this.layers[i].getNeurons()[j].getWeights()[k] * this.layers[i - 1].getNeurons()[k].getValue();

                newValue += this.layers[i].getNeurons()[j].getBias();

                this.layers[i].getNeurons()[j].setValue(activationFunction.evaluate(newValue));
            }
        }

        // Izlazne vrijednosti
        for(int i = 0; i < this.layers[this.layers.length - 1].getSize(); i++){
            output[i] = this.layers[this.layers.length - 1].getNeurons()[i].getValue();
        }

        return output;
    }

    public double backPropagate(double[] input, double[] desiredOutput) {
        double[] output = forwardPass(input);
        double error;

        for(int i = 0; i < this.layers[this.layers.length - 1].getSize(); i++)
        {
            error = desiredOutput[i] - output[i];
            this.layers[this.layers.length - 1].getNeurons()[i].setDelta(error * activationFunction.evaluateDerivative(output[i])) ;
        }

        for(int i = this.layers.length - 2; i >= 0; i--) {
            for(int j = 0; j < this.layers[i].getSize(); j++) {
                error = 0.0;
                for(int k = 0; k < this.layers[i + 1].getSize(); k++)
                    error += this.layers[i + 1].getNeurons()[k].getDelta() * this.layers[i + 1].getNeurons()[k].getWeights()[j];

                this.layers[i].getNeurons()[j].setDelta(error * activationFunction.evaluateDerivative(this.layers[i].getNeurons()[j].getValue()));
            }

            for(int j = 0; j < this.layers[i + 1].getSize(); j++) {
                for(int k = 0; k < this.layers[i].getSize(); k++)
                    this.layers[i + 1].getNeurons()[j].getWeights()[k] += this.learningRate * this.layers[i + 1].getNeurons()[j].getDelta() *
                            this.layers[i].getNeurons()[k].getValue();
                this.layers[i + 1].getNeurons()[j].setBias(this.layers[i + 1].getNeurons()[j].getBias() + this.learningRate * this.layers[i + 1].getNeurons()[j].getDelta());
            }
        }

        error = 0.;
        for(int i = 0; i < desiredOutput.length; i++)
        {
            error += Math.pow(output[i] - desiredOutput[i], 2);
        }

        error = error / desiredOutput.length;
        return error;
    }

    public double backPropagateBatch(double[][] inputs, double[][] desiredOutputs)
    {
        double error;
        double[] input, desiredOutput = new double[0], newOutput;
        double[][] newOutputs = new double[inputs.length][inputs[0].length];
        for(int l = 0; l < inputs.length; l++) {
            input = inputs[l];
            desiredOutput = desiredOutputs[l];
            newOutput = forwardPass(input);
            newOutputs[l] = newOutput;

            for (int i = 0; i < this.layers[this.layers.length - 1].getSize(); i++) {
                error = desiredOutput[i] - newOutput[i];
                if (l != 0) {
                    this.layers[this.layers.length - 1].getNeurons()[i].setDelta(
                            this.layers[this.layers.length - 1].getNeurons()[i].getDelta() +
                                    error * activationFunction.evaluateDerivative(newOutput[i]));
                } else {
                    this.layers[this.layers.length - 1].getNeurons()[i].setDelta(error * activationFunction.evaluateDerivative(newOutput[i]));
                }
            }
        }

        for(int i = this.layers.length - 2; i >= 0; i--)
        {
            for(int j = 0; j < this.layers[i].getSize(); j++)
            {
                error = 0.0;
                for(int k = 0; k < this.layers[i + 1].getSize(); k++)
                    error += this.layers[i + 1].getNeurons()[k].getDelta() * this.layers[i + 1].getNeurons()[k].getWeights()[j];

                this.layers[i].getNeurons()[j].setDelta(error * activationFunction.evaluateDerivative(this.layers[i].getNeurons()[j].getValue()));

            }

            for(int j = 0; j < this.layers[i + 1].getSize(); j++)
            {
                for(int k = 0; k < this.layers[i].getSize(); k++)
                    this.layers[i + 1].getNeurons()[j].getWeights()[k] += this.learningRate * this.layers[i + 1].getNeurons()[j].getDelta() *
                            this.layers[i].getNeurons()[k].getValue();
                this.layers[i + 1].getNeurons()[j].setBias(this.learningRate * this.layers[i + 1].getNeurons()[j].getDelta());
            }
        }

        error = 0.0;

        for(int i = 0; i < desiredOutputs.length; i++)
        {
            for(int j = 0; j < desiredOutput.length; j++){
                error += Math.pow(newOutputs[i][j] - desiredOutputs[i][j], 2);
            }
        }

        error = (error / desiredOutputs.length) / desiredOutput.length;
        return error;
    }

    public void save(String path){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 1; i < this.layers.length; i++){
            for(int j = 0; j < this.layers[i].getSize(); j++){
                stringBuilder.append(this.layers[i].getNeurons()[j].getBias()).append(" ");
                for(int k = 0; k < this.layers[i].getNeurons()[j].getWeights().length; k++){
                    stringBuilder.append(this.layers[i].getNeurons()[j].getWeights()[k]).append(" ");
                }
                stringBuilder.append("\n");
            }
            stringBuilder.append("\n");
        }

        try {
            Files.writeString(Paths.get(path), stringBuilder.toString());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static ANN load(String path, int[] layers){
        ANN ann = new ANN(layers, 0);
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            int numOfLayer = 1, numOfNeuron = 0;
            for(String line : lines){
                String[] nums = line.split(" ");
                if(nums[0].equals("")){
                    numOfLayer++;
                    numOfNeuron = 0;
                    continue;
                }
                ann.layers[numOfLayer].getNeurons()[numOfNeuron].setBias(Double.parseDouble(nums[0]));
                for(int i = 1; i < nums.length; i++){
                    ann.layers[numOfLayer].getNeurons()[numOfNeuron].getWeights()[i - 1] = Double.parseDouble(nums[i]);
                }
                numOfNeuron++;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ann;
    }
}
