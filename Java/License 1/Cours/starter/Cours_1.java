package fr.cours.starter;

import java.util.Calendar;

public class Cours_1 {

    // Show all exercices
    public void showAll(){
        this.showExo1();
        this.showExo1Bis();

        this.showExo2();
        this.showExo2Bis();

        this.showExo3();
        this.showExo3Bis();

        this.showExo4();
        this.showExo4Bis();

        this.showExo6();
        this.showExo6Bis();

        this.showExo7();

        this.showExo8();

        this.showExo9();

        this.showExo10();
    }

    // Exercice 1
    private void showExo1(){
        int heure = 3600;
        int jour  = heure * 24;
        long an   = jour * 365;
        long siecle = an * 100;

        System.out.format("Cours 1 - Exo 1      : %,8d%n", siecle);
    }

    private void showExo1Bis(){
        Calendar start  = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        end.add(Calendar.YEAR, 100);

        long secondes = (long) Math.floor(end.getTimeInMillis() - start.getTimeInMillis()) / 1000;

        System.out.format("Cours 1 - Exo 1(bis) : %,8d%n", secondes);
    }

    // Exercice 2
    private void showExo2(){
        float pi = 3.14159f;
        int rayon = 2;
        double volume = 4/3 * pi * rayon * rayon * rayon;

        System.out.println("Cours 1 - Exo 2      : " + volume);
    }

    private void showExo2Bis(){
        float pi = 3.14159f;
        int rayon = 2;
        double volume = 4/3 * pi * Math.pow(rayon, 3);

        System.out.format("Cours 1 - Exo 2(bis) : %1$.6f \n", volume);
    }

    // Exercice 3
    private void showExo3(){
        int annee = 2017;

        if( (annee % 4 == 0 && annee % 100 != 0) || (annee % 400 == 0) ){
            System.out.println("Cours 1 - Exo 3      : " + annee + " is a leap year.");
        }
        else{
            System.out.println("Cours 1 - Exo 3      : " + annee + " isn't a leap year.");
        }
    }

    private void showExo3Bis(){
        int annee = 2017;
        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, annee);

        if( date.getActualMaximum( Calendar.DAY_OF_YEAR ) == 366 ){
            System.out.println("Cours 1 - Exo 3(bis) : " + annee + " is a leap year.");
        }
        else{
            System.out.println("Cours 1 - Exo 3(bis) : " + annee + " isn't a leap year.");
        }
    }

    // Exercice 4
    private void showExo4(){
        int a = 1;
        int b = 2;
        int c = 3;

        if( (b>=a && a>=c) || (c>=a && a>=b) ){
            System.out.println("Cours 1 - Exo 4      : a=" + a);
        }
        else if( (a>=b && b>=c) || (c>=b && b>=a) ){
            System.out.println("Cours 1 - Exo 4      : b=" + b);
        }
        else{
            System.out.println("Cours 1 - Exo 4      : c=" + c);
        }
    }

    private void showExo4Bis(){
        int a = 1;
        int b = 2;
        int c = 3;

        int middle = a>=b ? (c>=a ? a : (b>=c ? b : c)) : (c>=b ? b : (a>=c ? a : c));

        System.out.println("Cours 1 - Exo 4(bis) : " + middle);
    }

    // Exercice 6
    private void showExo6(){
        int n = 27;

        if( n % 5 <= 2 ){
            System.out.println("Cours 1 - Exo 6      : " + (n - n%5));
        }
        else{
            System.out.println("Cours 1 - Exo 6      : " + (n - n%5 + 5));
        }
    }

    private void showExo6Bis(){
        int n = 22;

        System.out.println("Cours 1 - Exo 6(bis) : " + (5 * Math.round((double) n / 5)));
    }

    // Exercice 7
    private void showExo7(){
        int somme = 421;

        System.out.print("Cours 1 - Exo 7      : ");

        for( int i=0; i<10; i++ ){
            somme += i * 42;
            System.out.print(somme + " ");
        }

        System.out.println();
    }

    // Exercice 8
    private void showExo8(){
        int n = 0;

        for( int i=2014; i<=2020; i++ ){
            if( i % 5 == 0){
                n++;
            }
        }

        System.out.println("Cours 1 - Exo 8      : " + n);
    }

    // Exercice 9
    private void showExo9(){
        int c = 3;

        System.out.println("Cours 1 - Exo 9      : ");

        for( int i=0; i<c; i++ ){
            for( int j=0; j<c; j++ ){
                System.out.print( (i+j)%2 );
            }

            System.out.println();
        }
    }

    // Exercice 10
    private void showExo10(){
        int n = 14;

        System.out.print("Cours 1 - Exo 10     : ");

        while( n > 1 ){
            n = (n%2 == 0) ? n/2 : 3*n+1;

            System.out.print(n + " ");
        }

        System.out.println();
    }
}
