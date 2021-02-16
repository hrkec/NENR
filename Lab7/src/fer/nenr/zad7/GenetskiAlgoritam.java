package fer.nenr.zad7;

import java.util.Random;

public class GenetskiAlgoritam {
    double[][] parametersPopulation;
    private int populationSize;
    private int[] layers;
    Random random = new Random();

    public GenetskiAlgoritam(int populationSize, int[] layers){
        this.populationSize = populationSize;
        this.layers = layers;
        double[] parameters = new double[NeuronskaMreza.numOfParameters(layers)];
        this.parametersPopulation = new double[populationSize][NeuronskaMreza.numOfParameters(layers)];
        for(int k = 0; k < populationSize; k++){
            for(int i = 0; i < parameters.length; i++){
                parameters[i] = fRand(-1, 1);
            }
            this.parametersPopulation[k] = parameters;
        }
    }

    public double getErrors(Dataset dataset, double[] chromosome){
        double output = new NeuronskaMreza(this.layers).calcError(dataset, chromosome);
        return output;
    }

    public double[] arithmeticRecombinationCross(double[] parent1, double[] parent2){
        double[] child = new double[parent1.length];
        for(int i = 0; i < parent1.length; i++){
            child[i] = parent1[i] + parent2[i];
            child[i] /= 2;
        }
        return child;
    }

    public double[] BLXCross(double[] parent1, double[] parent2, double alpha){
        double[] child = new double[parent1.length];
        double cMin, cMax, I, t;

        for(int i = 0; i < parent1.length; i++){
            cMin = parent1[i];
            cMax = parent2[i];

            if(cMin > cMax){
                t = cMin;
                cMin = cMax;
                cMax = t;
            }

            I = cMax - cMin;
            child[i] = fRand(cMin - I * alpha, cMax + I * alpha);

        }
        return child;
    }

    public double[] simulatedBinaryCross(double[] parent1, double[] parent2){
        double[] child = new double[parent1.length];
        double alpha = random.nextDouble();
        for(int i = 0; i < parent1.length; i++){
            child[i] = (1 - alpha) * parent1[i] + alpha * parent2[i];
        }
        return child;
    }

    public double[] mutation1(double[] child, double mutationProbability, double variance){
        double[] mutant = new double[child.length];
        for(int i = 0; i < child.length; i++){
            mutant[i] = child[i];
            if(random.nextDouble() < mutationProbability){
                mutant[i] += getGaussian(0, variance);
            }
        }
        return mutant;
    }

    public double[] mutation2(double[] child, double mutationProbability, double variance){
        double[] mutant = new double[child.length];
        for(int i = 0; i < child.length; i++){
            mutant[i] = child[i];
            if(random.nextDouble() < mutationProbability){
                mutant[i] = getGaussian(0, variance);
            }
        }
        return mutant;
    }

    private double getGaussian(double mean, double variance){
        return mean + random.nextGaussian() * variance;
    }

    private double fRand(double fMin, double fMax){
        return fMin + random.nextDouble() * (fMax - fMin);
    }
}
