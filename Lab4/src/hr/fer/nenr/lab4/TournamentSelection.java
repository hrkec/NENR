package hr.fer.nenr.lab4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TournamentSelection {
    private final int N;
    Random random;

    public TournamentSelection(int n) {
        N = n;
        this.random = new Random();
    }

    public double[][] select (double[][] population, double[] errors){
        List<Integer> indices = new ArrayList<>(N);
        int idx;
        for(int i = 0; i < N; i++){
            idx = random.nextInt(population.length);
            if(!indices.contains(idx)){
                indices.add(idx);
            } else {
                i--;
            }
        }

        double[][] tournamentPop = new double[N][5];
        double[] tournamentErrors = new double[N];

        for(int i = 0; i < N; i++){
            tournamentPop[i] = population[indices.get(i)];
            tournamentErrors[i] = errors[indices.get(i)];
        }

        sortByError(tournamentPop, tournamentErrors);

        return tournamentPop;
    }

    void sortByError(double[][] population, double[] errors) {
        double[] t;
        double temp, min;
        int k = 0;
        for(int i = 0; i < population.length - 1; i++){
            min = errors[i];
            k = i; temp = errors[i]; t = population[i];
            for(int j = i + 1; j < population.length; j++){
                if(errors[j] < errors[i]){
                    if(errors[j] < min){
                        min = errors[j];
                        temp = errors[j];
                        t = population[j];
                        k = j;
                    }
                }
            }
            errors[k] = errors[i];
            errors[i] = temp;
            population[k] = population[i];
            population[i] = t;
        }
    }

}
