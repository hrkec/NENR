package hr.fer.nenr.zad6;

import java.util.Random;

public class Rule {
    private double a, b, c, d, p, q, r;
    private double da, db, dc, dd, dp, dq, dr;

    private final Random random;

    public Rule() {
        random = new Random();
        a = fRand(1.);
        b = fRand(1.);
        c = fRand(1.);
        d = fRand(1.);
        p = fRand(1.);
        q = fRand(1.);
        r = fRand(1.);

        da = a;
        db = b;
        dc = c;
        dd = d;
        dp = p;
        dq = q;
        dr = r;
    }

    public double apply(double x, double y){
        return p * x + q * y + r;
    }

    public double sigmoidA(double x){
        return 1. / (1. + Math.exp(b * (x - a)));
    }

    public double sigmoidB(double y){
        return 1. / (1. + Math.exp(d * (y - c)));
    }

    public double getPi(double x, double y){
        return sigmoidA(x) * sigmoidB(y);
    }

    public void calculateDerivatives(double[] pattern, double o, double learningRate, double sumPi, double sumNumerator){
        double x = pattern[0];
        double y = pattern[1];
        double z = pattern[2];

        double alpha = sigmoidA(x);
        double beta = sigmoidB(y);
        double pi = alpha * beta;

        double product1 = learningRate * (z - o) * pi / sumPi;
        dp += product1 * x;
        dq += product1 * y;
        dr += product1;

        double product2 = learningRate * (z - o) * sumNumerator / (sumPi * sumPi);
        da += (product2) * beta * b * alpha * (1 - alpha);
        db -= (product2) * beta * (x - a) * alpha * (1 - alpha);
        dc += (product2) * alpha * d * beta * (1 - beta);
        dd -= (product2) * alpha * (y - c) * beta * (1 - beta);
    }

    public void updateParameters(){
        p = dp;
        q = dq;
        r = dr;
        a = da;
        b = db;
        c = dc;
        d = dd;
    }

    private double fRand(double randScale){
        return 2 * randScale * random.nextDouble() - randScale;
    }

}
