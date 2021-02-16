package hr.fer.nenr.lab4;

public class GaussMutation {
    private final double sigma;

    public GaussMutation(double sigma) {
        this.sigma = sigma;
    }

    public double[] mutate(double[] child){
        double[] mutatedChild = new double[5];
        for(int i = 0; i < 5; i++){
            mutatedChild[i] = child[i] - sigma + 2 * sigma * Math.random();
            if(mutatedChild[i] < -4) mutatedChild[i] = -4;
            if(mutatedChild[i] > 4) mutatedChild[i] = 4;
        }
        return mutatedChild;
    }
}
