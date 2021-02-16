package fer.nenr.zad5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ANN_test {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("kvadrat.txt"));
        double[] result;
        double[] input, output;
        double[][] inputs, outputs;
        inputs = new double[lines.size()][lines.get(0).length() - 1];
        outputs = new double[lines.size()][lines.get(0).length() - 1];
        int k = 0;
        for(String line : lines){
            input = new double[1];
            output = new double[1];
            String[] nums = line.split(" ");

            for(int i = 0; i < nums.length - 1; i++){
                input[i] = Double.parseDouble(nums[i]);
            }

            output[0] = Double.parseDouble(nums[nums.length - 1]);

            inputs[k] = input;
            outputs[k] = output;
            k++;
        }

        int[] layers = new int[]{1, 6, 6, 1};

        ANN ann = new ANN(layers, 0.1);
        System.out.println(inputs.length);

        for(int i = 0; i < 100000; i++){
            double error = 0;

            for(int j = 0; j < inputs.length; j++){
                error += ann.backPropagate(inputs[j], outputs[j]);
            }

            if(i % 1000 == 0) System.out.println("Error at step " + i + " is " + error);
        }

        System.out.println("Learning completed!");

        /* Test */
        for (double[] doubles : inputs) {
            result = ann.forwardPass(doubles);
            System.out.printf("%f -> %f\n", doubles[0], result[0]);
        }

        result = ann.forwardPass(new double[]{0.55});
        System.out.printf("%f -> %f\n", 0.55, result[0]);
        result = ann.forwardPass(new double[]{-0.9});
        System.out.printf("%f -> %f\n", -0.9, result[0]);
        result = ann.forwardPass(new double[]{-0.65});
        System.out.printf("%f -> %f\n", -0.65, result[0]);

    }
}
