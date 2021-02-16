package hr.fer.nenr.lab4;

import java.util.Random;

public class GeneticUtil {
    public static double[][] newPopulation(int populationSize) {
        double[] betas;

        double[][] population = new double[populationSize][5];
        for(int i = 0; i < populationSize; i++){
            betas = new double[5];
            for(int k = 0; k < 5; k++){
                betas[k] = fRand(-4, 4);
            }
            population[i] = betas;
        }

        return population;
    }

    public static double[] getErrors(double[][] pop, Func func) {
        double[] errors = new double[pop.length];
        for(int i = 0; i < pop.length; i++){
            errors[i] = func.valueAt(pop[i]);
        }
        return errors;
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

    public static double fRand(double fMin, double fMax){
        Random random = new Random();
        return fMin + random.nextDouble() * (fMax - fMin);
    }

    public static void printSolution(double[] solution) {
        System.out.print("\t");
        for (double value : solution) {
            System.out.print(value + " ");
        }
        System.out.println();
    }

    public static void printBest(double[] best, int numOfGeneration, double bestError){
        System.out.printf("Generacija %d:\n", numOfGeneration);
        printSolution(best);
        System.out.printf("\tKazna: %.13f\n", bestError);
    }
}
