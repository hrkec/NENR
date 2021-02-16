package hr.fer.nenr.lab4;

import java.util.Arrays;

import static hr.fer.nenr.lab4.GeneticUtil.fRand;

public class RouletteWheelSelection {

    public double[][] select (double[][] population, double[] errors){
        double[] parent1 = selectOneParent(population, errors);
        double[] parent2 = selectOneParent(population, errors);
        return new double[][] { parent1, parent2 };
    }

    private double[] selectOneParent(double[][] population, double[] errors) {
        int worstIdx = findWorstIdx(errors), idx = 0;
        double[] roulette = new double[errors.length];
        double sum = 0, selection;

        for(int i = 0; i < errors.length;i++){
            roulette[i] = errors[worstIdx] - errors[i];
        }

        for (double v : roulette) {
            sum += v;
        }

        selection = fRand(0, sum);

        sum = 0;
        for(double v : roulette){
            sum += v;
            if(sum >= selection){
                return population[idx];
            }
            idx++;
        }
        return population[0];
    }

    private int findWorstIdx(double[] errors) {
        int worstIdx = 0;
        for(int i = 1; i < errors.length; i++){
            if(errors[i] > errors[worstIdx]){
                worstIdx = i;
            }
        }
        return worstIdx;
    }
}
