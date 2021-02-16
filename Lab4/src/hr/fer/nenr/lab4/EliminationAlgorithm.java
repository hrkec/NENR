package hr.fer.nenr.lab4;

import static hr.fer.nenr.lab4.GeneticUtil.*;

public class EliminationAlgorithm {
    private final double[][] population;
    private final Func func;
    private final BLXCrossing crossing;
    private final GaussMutation mutation;
    private final TournamentSelection selection;
    private final double mutationProbability;
    private final double minError;
    private final int numOfGenerations;

    public EliminationAlgorithm(double[][] population, Func func, BLXCrossing crossing, GaussMutation mutation,
                                TournamentSelection selection, double mutationProbability, double minError, int numOfGenerations) {
        this.population = population;
        this.func = func;
        this.crossing = crossing;
        this.mutation = mutation;
        this.selection = selection;
        this.mutationProbability = mutationProbability;
        this.minError = minError;
        this.numOfGenerations = numOfGenerations;
    }

    public void run(){
        double[][] pop = population;
        int numOfGeneration = 0, eliteIdx;
        double bestError = Double.MAX_VALUE;
        double[] errors, best = pop[0];
        
        while(numOfGeneration < numOfGenerations && minError < bestError){
            errors = getErrors(pop, func);
            eliteIdx = findBest(errors);

            if(errors[eliteIdx] < bestError){
                bestError = errors[eliteIdx];
                best = pop[eliteIdx];
                printBest(best, numOfGeneration, bestError);
            }

            double[][] parents = selection.select(pop, errors);
            double[] child = crossing.cross(parents[0], parents[1]);
            if(fRand(0, 1) < mutationProbability){
                child = mutation.mutate(child);
            }

            for(int i = 0; i < pop.length; i++){
                if(pop[i] == parents[2]){
                    pop[i] = child;
                    break;
                }
            }

            if(numOfGeneration % 500 == 0){
                printBest(best, numOfGeneration, bestError);
            }

            numOfGeneration++;
        }
        printBest(best, numOfGeneration, bestError);
    }
}
