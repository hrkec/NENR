package hr.fer.nenr.lab4;

import static hr.fer.nenr.lab4.GeneticUtil.fRand;

public class BLXCrossing {
    private double alpha;

    public BLXCrossing(double alpha) {
        this.alpha = alpha;
    }

    public double[] cross(double[] parent1, double[] parent2){
        double[] child = new double[5];
        double cMin, cMax, I, t;

        for(int i = 0; i< 5; i++){
            cMin = parent1[i];
            cMax = parent2[i];

            if(cMin > cMax){
                t = cMin;
                cMin = cMax;
                cMax = t;
            }

            I = cMax - cMin;
            child[i] = fRand(cMin - I * alpha, cMax + I * alpha);

            if(child[i] < -4) child[i] = -4;
            if(child[i] > 4) child[i] = 4;
        }
        return child;
    }
}
