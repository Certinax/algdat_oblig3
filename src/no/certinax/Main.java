package no.certinax;

import java.sql.SQLOutput;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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

            int n = 10000000;

            ObligSBinTre<Integer> fire = new ObligSBinTre<>(Comparator.naturalOrder());
            int[] b = {4,7,2,9,4,10,8,7,4,6,1};

            /*ThreadLocalRandom random = ThreadLocalRandom.current();
            for(int i = 50; i < b.length; i--)
                b[i] = random.nextInt(0,1000);*/

            for (int verdi : b)
                fire.leggInn(verdi);

            //fire.nesteInorden();
            //System.out.println(fire.inorden());
            ///System.out.println(fire);
            //System.out.println(fire.omvendtString());

            //System.out.println("Hele treet: " + fire);
            //System.out.println("Høyre gren: " + fire.høyreGren());;
            //fire.fjern(4);
            //System.out.println(fire);
            //fire.fjernAlle(7);
            //System.out.println(fire);

            //fire.nullstill();
            //System.out.println(fire);


            //long tic = System.currentTimeMillis();
            //fire.omvendtString();
            //long tac = System.currentTimeMillis() - tic;
            //System.out.println(tac + " ms.");
        }
        /*
        {

            ObligSBinTre<Character> fem = new ObligSBinTre<>(Comparator.naturalOrder());
            char[] verdier = "IATBHJCRSOFELKGDMPQN".toCharArray();
            for (char c : verdier) fem.leggInn(c);

            //System.out.println(fem);
            System.out.println(fem.høyreGren());
            System.out.println(fem.lengstGren());
        }
        */

        {
            ObligSBinTre<Character> tre = new ObligSBinTre<>(Comparator.naturalOrder());
            char[] verdier = "IATBHJCRSOFELKGDMPQN".toCharArray();
            for (char c : verdier) tre.leggInn(c);
            System.out.println(tre.høyreGren() + " " + tre.lengstGren());


            //tre.grener();

            //System.out.println("Oppgaveutkjøring");

            //String[] s = tre.grener();
            //for (String gren : s) System.out.println(gren);


            System.out.println(tre.bladnodeverdier());
        }
    }
}
