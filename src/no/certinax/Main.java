package no.certinax;

import java.util.Comparator;

public class Main {

    public static void main(String[] args) {
	// write your code here

        System.out.println("Oblig 3");

        ObligSBinTre<String> tre = new ObligSBinTre<>(Comparator.naturalOrder());
        System.out.println(tre.antall());
    }
}
