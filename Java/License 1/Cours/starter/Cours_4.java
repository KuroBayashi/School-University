package fr.cours.starter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Cours_4 {

    public void showAll(){
        this.showExo1();
        this.showExo2();
        this.showExo10();
        this.showExo3();
        this.showExo4();
        this.showExo5();
        this.showExo6();
        this.showExo7();
        this.showExo8();
        this.showExo9();
    }

    private void showExo1(){
        Random rnd = new Random();
        int n;

        System.out.print("Cours 4 - Exo 1 : ");

        do{
            n = rnd.nextInt(100);
            System.out.print(n + " ");
        }while( n != 99);

        System.out.println();
    }

    private void showExo2(){
        int size = 10;
        int get = 3;
        double array[] = new double[size];

        Random rnd = new Random();

        for( int i=0; i<size; i++ ){
            array[i] = rnd.nextDouble();
        }

        System.out.print("Cours 4 - Exo 2 : ");

        for( int i=0; i<get; i++ ){
            System.out.print( ((i==0)?"":", ") + array[ rnd.nextInt(array.length) ] );
        }

        System.out.println();
    }

    private void showExo10(){
        int jet[] = {2,4,1};
        Arrays.sort(jet);

        if( Arrays.equals(new int[] {1,2,4}, jet) ){
            System.out.println("Cours 4 - Exo 10 : Win");
        }
        else{
            System.out.println("Cours 4 - Exo 10 : Loose");
        }
    }

    private void showExo3(){
        int size = 10;
        String values[] = {"zero", "un", "deux"};
        ArrayList<String> list = new ArrayList<>();

        Random rnd = new Random();

        for( int i=0; i<size; i++ ){
            list.add( values[ rnd.nextInt(values.length) ] );
        }

        System.out.println("Cours 4 - Exo 3 : " + list);
    }

    private void showExo4(){
        int size = 10;
        ArrayList<Integer> list = new ArrayList<>();

        for( int i=0; i<size; i++ ){
            list.add(i);
        }

        Collections.shuffle( list );

        System.out.println("Cours 4 - Exo 4 : " + list);
    }

    private void showExo5(){
        int size = 20;
        int max = 10;
        int min = -10;
        ArrayList<Integer> list = new ArrayList<>();
        Random rnd = new Random();

        // Partie a
        for( int i=0; i<size; i++ ){
            list.add( rnd.nextInt(max+1-min) -max );
        }

        System.out.println("Cours 4 - Exo 5 a) : " + list);

        // Partie b
        for( int i=size-1; i>=0; i-- ){
            if( 0 > list.get(i) || list.get(i)%2 == 0 ){
                list.remove(i);

            }
        }

        System.out.println("Cours 4 - Exo 5 b) : " + list);
    }

    private void showExo6(){

    }

    private void showExo7(){
        double r = 10;

        System.out.println("Cours 4 - Exo 7 : " + (Math.PI * Math.pow(r, 2)));
    }

    private void showExo8(){
        int size = 20;
        int min = 10;
        int max = 50;
        int arrayMax = 0;
        int array[] = new int[size];

        for( int i=0; i<size; i++ ){
            array[i] = (int) (Math.random() * (max-min)) + min;

            if( i != 0){
                arrayMax = Math.max(array[i-1], array[i]);
            }
        }

        System.out.println("Cours 4 - Exo 8 : " + arrayMax);
    }

    private void showExo9(){
        double a = 1;
        double b = 10;
        double c = 3;

        double delta = Math.pow(b, 2) - 4 * a * c;

        if( delta >= 0 ){
            double sqrt = Math.sqrt(delta);

            double x1 = (-b-sqrt)/(2*a);
            double x2 = (-b+sqrt)/(2*a);

            System.out.println("Cours 4 - Exo 9 : " + x1 + " et " + x2);
        }
        else{
            System.out.println("Cours 4 - Exo 9 : No solution in R");
        }
    }
}
