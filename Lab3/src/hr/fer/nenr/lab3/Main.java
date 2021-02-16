/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hr.fer.nenr.lab3;

import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		// Biramo način dekodiranja neizrazitosti:
		Defuzzifier def = new COADefuzzifier();

		// Stvaranje oba sustava:
		// Grade se baza pravila i sve se inicijalizira
		FuzzySystem fsAkcel = new AkcelFuzzySystemMin(def);
		FuzzySystem fsKormilo = new KormiloFuzzySystemMin(def);
	    int L = 0, D = 0, LK = 0, DK = 0, V = 0, S = 0, akcel, kormilo;

		String line;
		while(true){
			if((line = input.readLine()) != null){
				if(line.charAt(0) == 'K') break;
				Scanner s = new Scanner(line);
				L = s.nextInt();
				D = s.nextInt();
				LK = s.nextInt();
				DK = s.nextInt();
				V = s.nextInt();
				S = s.nextInt();
	        }

	        // fuzzy magic ...

			int[] entry = new int[]{L, D, LK, DK, V, S};
			akcel = fsAkcel.conclude(entry);
			kormilo = fsKormilo.conclude(entry);

	        System.out.println(akcel + " " + kormilo);
	        System.out.flush();
	   }
    }

}

