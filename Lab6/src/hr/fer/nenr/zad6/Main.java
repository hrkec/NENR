package hr.fer.nenr.zad6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Main {
    public static final int numberOfRules = 10;

    public static final int numberOfIterations = 100000;
    public static final double learningRate = 0.001;
    public static int version = 1;

//    public static final int numberOfIterations = 1000000;
//    public static final double learningRate = 0.00001;
//    public static int version = 2;
    public static Rule[] rules;

    private double sumPi, sumPiF;

    public Main(){
        rules = new Rule[numberOfRules];
        for(int i = 0; i < numberOfRules; i++){
            rules[i] = new Rule();
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.train(false);
    }

    public void train(boolean generateErrorsFile){
        StringBuilder trainError = new StringBuilder();
        double[][] patterns = generatePatterns();

        if(version == 1){ // batch
            for(int i = 0 ; i < numberOfIterations; i++){
                for(double[] pattern : patterns){
                    double x = pattern[0];
                    double y = pattern[1];
                    double o = forwardPass(x, y);
                    for(Rule rule : rules){
                        double f = rule.apply(x, y);
                        rule.calculateDerivatives(pattern, o, learningRate, sumPi, sumPi * f - sumPiF);
                    }
                }
                if(generateErrorsFile) trainError.append(i).append(" ").append(calculateError(patterns)).append("\n");
                for(Rule rule : rules){
                    rule.updateParameters();
                }
                if(i % 10000 == 0){
                    printError(patterns, i);
                }
            }
        } else if (version == 2){
            for(int i = 0; i < numberOfIterations; i++){
                double[] pattern = patterns[i % patterns.length];
                double o = forwardPass(pattern[0], pattern[1]);
                if(generateErrorsFile) trainError.append(i).append(" ").append(calculateError(patterns)).append("\n");

                for(Rule rule : rules){
                    double f = rule.apply(pattern[0], pattern[1]);
                    rule.calculateDerivatives(pattern, o, learningRate, sumPi, sumPi * f - sumPiF);
                    rule.updateParameters();
                }
                if(i % 10000 == 0){
                    printError(patterns, i);
                }
            }
        }

        if(generateErrorsFile) {
            try {
                Files.writeString(Paths.get("error_iterations.txt"), trainError.toString(), StandardOpenOption.CREATE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        printPatterns(patterns);
//        printErrors(patterns);
//        printRules();
//        printRules2();
    }

    private void printPatterns(double[][] patterns){
        StringBuilder stringBuilder = new StringBuilder();
        double prvi = patterns[0][0];
        for(double[] pattern : patterns){
            double output = forwardPass(pattern[0], pattern[1]);
            if(pattern[0] != prvi){
                prvi = pattern[0];
                stringBuilder.append("\n");
            }
            stringBuilder.append(pattern[0]).append(" ").append(pattern[1]).append(" ").append(output).append("\n");
            System.out.printf("x=%f\ty=%f\tz=%f\to=%f\tz-o=%f\n", pattern[0], pattern[1], pattern[2], output, pattern[2] - output);
        }
        try {
            Files.writeString(Paths.get("data.txt"), stringBuilder.toString(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printErrors(double[][] patterns){
        StringBuilder stringBuilder = new StringBuilder();
        double prvi = patterns[0][0];
        for(double[] pattern : patterns){
            double output = forwardPass(pattern[0], pattern[1]);
            if(pattern[0] != prvi){
                prvi = pattern[0];
                stringBuilder.append("\n");
            }
            stringBuilder.append(pattern[0]).append(" ").append(pattern[1]).append(" ").append(output - pattern[2]).append("\n");
        }
        try {
            Files.writeString(Paths.get("errors_patterns.txt"), stringBuilder.toString(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printRules(){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = -4; i <= 4; i++){
            stringBuilder.append(i);
            for (Rule rule : rules) {
                stringBuilder.append(" ").append(rule.sigmoidA(i));
            }
            stringBuilder.append("\n");
        }
        try {
            Files.writeString(Paths.get("A.txt"), stringBuilder.toString(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        stringBuilder = new StringBuilder();
        for(int i = -4; i <= 4; i++){
            stringBuilder.append(i);
            for (Rule rule : rules) {
                stringBuilder.append(" ").append(rule.sigmoidB(i));
            }
            stringBuilder.append("\n");
        }
        try {
            Files.writeString(Paths.get("B.txt"), stringBuilder.toString(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printRules2(){
        int k = 0;
        for(Rule rule : rules){
            k++;
            StringBuilder stringBuilder = new StringBuilder();
            StringBuilder stringBuilder1 = new StringBuilder();
            StringBuilder stringBuilder2 = new StringBuilder();
            StringBuilder stringBuilder3 = new StringBuilder();
            for(int i = -4; i <= 4; i++){
                for(int j = -4; j <= 4; j++){
                    stringBuilder.append(i).append(" ").append(j).append(" ").append(rule.apply(i, j)).append("\n");
                    stringBuilder1.append(i).append(" ").append(j).append(" ").append(rule.getPi(i, j)).append("\n");
                    stringBuilder2.append(i).append(" ").append(j).append(" ").append(rule.sigmoidA(i)).append("\n");
                    stringBuilder3.append(i).append(" ").append(j).append(" ").append(rule.sigmoidB(j)).append("\n");
                }
                stringBuilder.append("\n");
                stringBuilder1.append("\n");
                stringBuilder2.append("\n");
                stringBuilder3.append("\n");
            }
            try {
                Files.writeString(Paths.get("rule" + k + ".txt"), stringBuilder.toString(), StandardOpenOption.CREATE);
                Files.writeString(Paths.get("rule" + k + "pi.txt"), stringBuilder1.toString(), StandardOpenOption.CREATE);
                Files.writeString(Paths.get("rule" + k + "A.txt"), stringBuilder2.toString(), StandardOpenOption.CREATE);
                Files.writeString(Paths.get("rule" + k + "B.txt"), stringBuilder3.toString(), StandardOpenOption.CREATE);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    private double calculateError(double[][] patterns) {
        double sum = 0;
        for(double[] pattern : patterns){
            sum += Math.pow(forwardPass(pattern[0], pattern[1]) - pattern[2], 2);
        }
        return sum / patterns.length;
    }

    public void printError(double[][] patterns, int i){
        double error = calculateError(patterns);
        System.out.printf("%d. MSE = %.16f\n", i, error);
    }

    private double forwardPass(double x, double y){
        sumPi = 0;
        sumPiF = 0;
        for(Rule rule : rules){
            double pi = rule.getPi(x, y);
            sumPi += pi;
            sumPiF += (pi * rule.apply(x, y));
        }
        return sumPiF / sumPi;
    }


    private static double[][] generatePatterns(){
        double[][] patterns = new double[81][3];
        int b = 0;
        for(int x = -4; x <= 4; x++){
            for(int y = -4; y <= 4; y++){
                double cosine = Math.cos((double) x / 5);
                patterns[b][0] = x;
                patterns[b][1] = y;
                patterns[b++][2] = (Math.pow(x - 1, 2) + Math.pow(y + 2, 2) - 5 * x * y + 3) * cosine * cosine;
            }
        }
        return patterns;
    }
}
