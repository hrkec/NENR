package fer.nenr.zad5;

import java.io.IOException;

public class Main {
    public static final int M = 10;
    public static final int numOfGestures = 20;
    public static final int numOfEpochs = 10000;
    public static final double learningRate = 1;
    public static final int[] layers = new int[]{2 * M, 6, 6, 5};
    public static final String sampleFile = "samples.txt";
    public static final String trainedWeightsFile = "trained_weights.txt";

    public static final int version = 2;

    public static void main(String[] args) throws IOException {
//        GUIGreekGenerate.generate(M, numOfGestures, sampleFile);

        ANNTrain annTrain = new ANNTrain(M, numOfGestures, numOfEpochs, sampleFile);
        annTrain.train(layers, learningRate, trainedWeightsFile, version);

        ANN trainedANN = ANN.load(trainedWeightsFile, layers);
        GUIGreekTest.start(trainedANN);

    }
}
