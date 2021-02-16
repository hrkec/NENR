package hr.fer.nenr.lab4;

import static hr.fer.nenr.lab4.GeneticUtil.*;

public class GenerationAlgorithm {
    private final double[][] population;
    private final Func func;
    private final BLXCrossing crossing;
    private final GaussMutation mutation;
    private final RouletteWheelSelection selection;
    private final double mutationProbability;
    private final double minError;
    private final int numOfGenerations;
    private final boolean elite;
    private double[] newPopulation;
    private double[][] pop;

    public GenerationAlgorithm(double[][] population, Func func, BLXCrossing crossing, GaussMutation mutation,
                               RouletteWheelSelection selection, double mutationProbability, double minError, int numOfGenerations, boolean elite) {
        this.population = population;
        this.func = func;
        this.crossing = crossing;
        this.mutation = mutation;
        this.selection = selection;
        this.mutationProbability = mutationProbability;
        this.minError = minError;
        this.numOfGenerations = numOfGenerations;
        this.elite = elite;
    }

    public void run(){
        double[][] pop = this.population;
        int numOfGeneration = 0, eliteIdx;
        double bestError = Double.MAX_VALUE;
        double[] best = pop[0], errors;
        while(numOfGeneration < numOfGenerations && minError < bestError){
            errors = getErrors(pop, func);
            eliteIdx = findBest(errors);
            double[][] newPopulation = pop.clone();

            if(elite){
                newPopulation[0] = pop[eliteIdx];
            }

            if(errors[eliteIdx] < bestError){
                bestError = errors[eliteIdx];
                best = pop[eliteIdx];
                printBest(best, numOfGeneration, bestError);
            }

            int elitism = 0;
            if (elite) elitism = 1;

            for(int i = elitism; i < pop.length; i++){
                double[][] parents = selection.select(pop, errors);
                double[] child = crossing.cross(parents[0], parents[1]);
                if(fRand(0, 1) < mutationProbability){
                    child  = mutation.mutate(child);
                }

                newPopulation[i] = child;
            }

            if(numOfGeneration % 500 == 0){
                printBest(best, numOfGeneration, bestError);
            }

            pop = newPopulation;
            numOfGeneration++;
        }

        printBest(best, numOfGeneration, bestError);
    }
}
