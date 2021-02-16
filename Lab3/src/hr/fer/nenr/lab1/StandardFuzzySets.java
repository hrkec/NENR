package hr.fer.nenr.lab1;

public class StandardFuzzySets {

    public static IIntUnaryFunction lFunction(int alpha, int beta){
        return new IIntUnaryFunction() {
            @Override
            public double valueAt(int x) {
                if (x < alpha) {
                    return 1;
                }
                if (x < beta) { // && alpha <= x -> TRUE
                    return 1.0 * (beta - x) / (beta - alpha);
                }
                return 0;
            }
        };
    }

    public static IIntUnaryFunction gammaFunction(int alpha, int beta){
        return new IIntUnaryFunction() {
            @Override
            public double valueAt(int x) {
                if (x < alpha){
                    return 0;
                }
                if (x < beta){ // && alpha <= x -> TRUE
                    return 1.0 * (x - alpha) / (beta - alpha);
                }
                return 1;
            }
        };
    }

    public static IIntUnaryFunction lambdaFunction(int alpha, int beta, int gamma){
        return new IIntUnaryFunction() {
            @Override
            public double valueAt(int x) {
                if (x < alpha){
                    return 0;
                }
                if(x < beta){
                    return 1.0 * (x - alpha) / (beta - alpha);
                }
                if(x < gamma){
                    return 1.0 * (gamma - x) / (gamma - beta);
                }
                return 0;
            }
        };
    }
}
