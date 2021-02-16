package hr.fer.nenr.lab3;

import hr.fer.nenr.lab1.Debug;
import hr.fer.nenr.lab1.IFuzzySet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Primjer1 {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        Defuzzifier def = new COADefuzzifier();
        FuzzySystem fs = new AkcelFuzzySystemMin(def);
        int L = 0, D = 0, LK = 0, DK = 0, V = 0, S = 0, idx = 0;
        String line;

        System.out.print("Unesi broj pravila: ");
        if ((line = input.readLine()) != null) {
            Scanner s = new Scanner(line);
            idx = s.nextInt();
        }
        Rule rule = fs.getRule(idx);

        System.out.print("Unesi L,D,LK,DK,V,S: ");
        if ((line = input.readLine()) != null) {
            Scanner s = new Scanner(line);
            L = s.nextInt();
            D = s.nextInt();
            LK = s.nextInt();
            DK = s.nextInt();
            V = s.nextInt();
            S = s.nextInt();
        }

        IFuzzySet zakljucak = rule.apply(new int[]{L, D, LK, DK, V, S});
        int value = def.defuzzify(zakljucak);

        Debug.print(zakljucak, "Skup koji je zakljucak pravila:");
        System.out.println("Dekodirana vrijednost zakljucka: " + value);
    }
}
