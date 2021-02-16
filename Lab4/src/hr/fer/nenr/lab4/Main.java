package hr.fer.nenr.lab4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static hr.fer.nenr.lab4.GeneticUtil.newPopulation;

public class Main {
    public static final boolean GENERATION = true;
    public static final String DATASET_PATH = "zad4-dataset1.txt";
    public static final int POPULATION_SIZE = 200;
    public static final int NUMBER_OF_GENERATIONS = 10000;
    public static final double ALPHA = 0.3;
    public static final double MUTATION_PROBABILITY = 0.1;
    public static final double SIGMA = 0.01;
    public static final double MINIMAL_ERROR = 1e-6;
    public static final boolean ELITISM = true;

//    public static final boolean GENERATION = false;
//    public static final String DATASET_PATH = "zad4-dataset1.txt";
//    public static final int POPULATION_SIZE = 200;
//    public static final int NUMBER_OF_GENERATIONS = 10000;
//    public static final double ALPHA = 0.5;
//    public static final double MUTATION_PROBABILITY = 0.1;
//    public static final double SIGMA = 0.01;
//    public static final double MINIMAL_ERROR = 1e-6;
    public static final int N = 3;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(DATASET_PATH));
        int datasetSize = lines.size();
        double[] xs = new double[datasetSize], ys = new double[datasetSize], fs = new double[datasetSize];
        String[] values;

        for(int i = 0; i < datasetSize; i++){
            values = lines.get(i).split("\\s");
            xs[i] = Double.parseDouble(values[0]);
            ys[i] = Double.parseDouble(values[1]);
            fs[i] = Double.parseDouble(values[2]);
        }

        Func func = new Func(xs, ys, fs);

        double[][] population = newPopulation(POPULATION_SIZE);

        BLXCrossing crossing = new BLXCrossing(ALPHA);
        GaussMutation mutation = new GaussMutation(SIGMA);

        if(GENERATION){
            RouletteWheelSelection selection = new RouletteWheelSelection();
            GenerationAlgorithm generationAlgorithm = new GenerationAlgorithm(population, func, crossing, mutation,
                    selection, MUTATION_PROBABILITY, MINIMAL_ERROR, NUMBER_OF_GENERATIONS, ELITISM);
            generationAlgorithm.run();
        } else {
            TournamentSelection selection = new TournamentSelection(N);
            EliminationAlgorithm eliminationAlgorithm = new EliminationAlgorithm(population, func, crossing, mutation,
                    selection, MUTATION_PROBABILITY, MINIMAL_ERROR, NUMBER_OF_GENERATIONS);
            eliminationAlgorithm.run();
        }
    }


}
