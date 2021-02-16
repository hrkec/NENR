package fer.nenr.zad7;

import java.io.IOException;
import java.util.Random;

public class Main {
    public static int[] layers = {2, 8, 3};
    public static int numOfGenerations = 1_000_000;
    public static int populationSize = 20;
    public static double minError = 1e-7;
    public static double mutationProbability1 = 0.001;
    public static double variance1 = 0.005;
    public static double t1 = 1;
    public static double mutationProbability2 = 0.001;
    public static double variance2 = 0.05;
    public static double t2 = 2;
    public static double mutationProbability3 = 0.001;
    public static double variance3 = 0.2;
    public static double t3 = 1;

    public static double ALPHA = 0.3;
    public static double c1 = 0.4;
    public static double c2 = 0.4;

    public static void main(String[] args) throws IOException {
        Dataset dataset = new Dataset("zad7-dataset.txt");
        Random random = new Random();

        GenetskiAlgoritam algoritam = new GenetskiAlgoritam(populationSize, layers);
        RouletteWheelSelection selection = new RouletteWheelSelection();

        double tSum = t1 + t2 + t3;
        double v1 = t1 / tSum;
        double v2 = t2 / tSum;
        double v3 = t3 / tSum;
        if(v1 == 0 && v2 == 0 && v3 == 0) v1 = 1;

        double[][] pop = algoritam.parametersPopulation;
        double[] errors;
        int eliteIdx; double bestError = Double.MAX_VALUE;
        int numOfGeneration = 0;
        
        for(; numOfGeneration < numOfGenerations; numOfGeneration++){
            errors = new double[pop.length];
            for(int j = 0; j < pop.length; j++){
                errors[j] = algoritam.getErrors(dataset, pop[j]);
            }
            eliteIdx = findBest(errors);
            double[][] newPopulation = pop.clone();

            newPopulation[0] = pop[eliteIdx];

            if(errors[eliteIdx] < bestError){
                bestError = errors[eliteIdx];
            }

            for(int l = 1; l < pop.length; l++){
                double[][] parents = selection.select(pop, errors);
                
                double whichCrossover = random.nextDouble();
                double[] child;
                if(whichCrossover <= c1){
                    child = algoritam.arithmeticRecombinationCross(parents[0], parents[1]);
                } else if(whichCrossover <= c1 + c2){
                    child = algoritam.BLXCross(parents[0], parents[1], ALPHA);
                } else {
                    child = algoritam.simulatedBinaryCross(parents[0], parents[1]);
                }
                
                double whichMutation = random.nextDouble();
                if(whichMutation <= v1){
                    child = algoritam.mutation1(child, mutationProbability1, variance1);
                } else if(v1 < whichMutation && whichMutation <= v1 + v2){
                    child = algoritam.mutation1(child, mutationProbability2, variance2);
                } else {
                    child = algoritam.mutation2(child, mutationProbability3, variance3);
                }

                newPopulation[l] = child;
            }

            if(numOfGeneration % 10000 == 0){
                printBestError(numOfGeneration, bestError);
            }

            if(bestError < minError) break;

            pop = newPopulation;
        }

        printBestError(numOfGeneration, bestError);
        printBest(pop[0]);

        NeuronskaMreza mreza = new NeuronskaMreza(layers);

        for(int i = 0; i < dataset.size(); i++){
            Double[] input = dataset.inputAtIndex(i);
            double[] outputCalc = mreza.calcOutput(input, pop[0]);
            double max = 0; int idx = 0;
            for(int j = 0; j < outputCalc.length; j++){
                if(outputCalc[j] > max){
                    max = outputCalc[j];
                    idx = j;
                }
            }
            for (Double value : input) {
                System.out.printf("%f ", value);
            }
            for(int j = 0; j < outputCalc.length; j++){
                if (j != idx) {
                    System.out.printf("0 ");
                } else {
                    System.out.printf("1 ");
                }
            }
            System.out.println();
        }
    }

    public static int findBest(double[] errors) {
        int eliteIdx = 0;
        for(int i = 1; i < errors.length; i++){
            if(errors[i] < errors[eliteIdx]){
                eliteIdx = i;
            }
        }
        return eliteIdx;
    }

    private static void printBest(double[] best) {
        System.out.printf("Najbolja jedinka:\n");
        for(double value : best){
            System.out.printf("%f ", value);
        }
        System.out.println();
    }

    public static void printBestError(int numOfGeneration, double bestError){
        System.out.printf("Generacija %d:", numOfGeneration);
        System.out.printf("\tKazna: %.16f\n", bestError);
    }

}
