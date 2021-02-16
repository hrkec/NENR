package hr.fer.nenr.lab4;

public class Func {
    private final double[] xs, ys, fs;
    private final int datasetSize;

    public Func(double[] xs, double[] ys, double[] fs) {
        this.xs = xs;
        this.ys = ys;
        this.fs = fs;
        this.datasetSize = xs.length;
    }

    public double valueAt(double[] betas){
        double error, totalError = 0.;
        double beta0 = betas[0], beta1 = betas[1], beta2 = betas[2], beta3 = betas[3], beta4 = betas[4];
        double x, y, f, sine, cosine, fract, result;
        for(int i = 0; i < this.datasetSize; i++){
            x = xs[i];
            y = ys[i];
            f = fs[i];
            sine = Math.sin(beta0 + beta1 * x);
            cosine = Math.cos(x * (beta3 + y));
            fract = 1. / (1 + Math.exp(Math.pow(x - beta4, 2)));
            result = sine + beta2 * cosine * fract;
            error = result - f;
            error *= error;
            totalError += error;
        }
        return totalError / datasetSize;
    }
}
