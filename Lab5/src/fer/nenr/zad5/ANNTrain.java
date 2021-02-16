package fer.nenr.zad5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ANNTrain {

    private final int M;
    private final int numberOfGestures;
    private final int numberOfEpochs;

    private final String pathToSamples;

    public ANNTrain(int M, int numberOfGestures, int numberOfEpochs, String pathToSamples) {
        this.M = M;
        this.numberOfEpochs = numberOfEpochs;
        this.numberOfGestures = numberOfGestures;
        this.pathToSamples = pathToSamples;
    }

    public void train(int[] layers, double learningRate, String saveFilename, int version) throws IOException {
        ANN ann = new ANN(layers, learningRate);
        List<String> lines = Files.readAllLines(Paths.get(this.pathToSamples));
        double[] input, output;
        double[][] inputs, outputs;

        inputs = new double[5 * numberOfGestures][lines.get(0).length() - 5];
        outputs = new double[5 * numberOfGestures][5];
        int k = 0;

        for(String line : lines){
            input = new double[2 * M]; output = new double[5];
            String[] nums = line.split(" ");

            for(int i = 0; i < nums.length - 5; i++){
                input[i] = Double.parseDouble(nums[i]);
            }

            for(int i = nums.length - 5; i < nums.length; i++){
                output[i - nums.length + 5] = Double.parseDouble(nums[i]);
            }

            inputs[k] = input;
            outputs[k] = output;
            k++;
        }

        if(version == 1){
            for(int i = 0; i < numberOfEpochs; i++){
                double error = 0;

                error += ann.backPropagateBatch(inputs, outputs);
                if(i % 1000 == 0) System.out.println("Error at step "+i+" is "+error);
            }
        } else if (version == 2){
            for(int i = 0; i < numberOfEpochs; i++){
                double error = 0;
                for(int j = 0; j < inputs.length; j++){
                    //                error += ann.backPropagate(inputs[j], outputs[j]);
                    error += ann.backPropagateBatch(new double[][]{inputs[j]}, new double[][]{outputs[j]});
                }
                if(i % 1000 == 0) System.out.println("Error at step "+i+" is "+error);
            }
        } else if (version == 3){
            for(int i = 0; i < numberOfEpochs; i++){
                double error = 0;
                for(int j = 0; j < 5; j++){
                    double[][] newInput = new double[10][2 * M];
                    double[][] newOutput = new double[10][5];
                    for(int l = 0; l < 9; l+=2){
                        newInput[l] = inputs[l + j * numberOfGestures];
                        newInput[l + 1] = inputs[l + j * numberOfGestures + 1];
                        newOutput[l] = outputs[l + j * numberOfGestures];
                        newOutput[l + 1] = outputs[l + j * numberOfGestures + 1];
                    }

                    error += ann.backPropagateBatch(newInput, newOutput);
                }
                if(i % 1000 == 0) System.out.println("Error at step " + i + " is " + error);
            }
        }

        System.out.println("Learning completed!");

        ann.save(saveFilename);
    }

}
