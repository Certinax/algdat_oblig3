package no.certinax;

import java.util.Comparator;

public class Main {

    public static void main(String[] args) {
	// write your code here
        /*
        System.out.println("Oblig 3");
        {
            ObligSBinTre<String> tre = new ObligSBinTre<>(Comparator.naturalOrder());
            System.out.println(tre.antall());
        }

        {
            Integer[] a = {4, 7, 2, 9, 5, 10, 8, 1, 3, 6};
            ObligSBinTre<Integer> tre = new ObligSBinTre<>(Comparator.naturalOrder());
            for (int verdi : a) tre.leggInn(verdi);
            System.out.println(tre.antall());
        }
        */
        {
            ObligSBinTre<Integer> tre = new ObligSBinTre<>(Comparator.naturalOrder());
            int[] a = {1,7,1,6,1,5,1,4,1,1,1,3};
            for (int verdi : a) tre.leggInn(verdi);

            //System.out.println(tre.antall(1));


            ObligSBinTre<Integer> fire = new ObligSBinTre<>(Comparator.naturalOrder());
            int[] b = {3,1,2,5};
            for (int verdi : b) fire.leggInn(verdi);

            fire.inorden();
            //System.out.println(fire.inorden());
            //System.out.println(fire);
        }
    }
}
