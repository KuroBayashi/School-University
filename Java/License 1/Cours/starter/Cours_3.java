package fr.cours.starter;

import java.util.Arrays;

public class Cours_3 {

    // Show all exercices
    public void showAll(){
        this.showExo11();

        this.showExo16();

        this.showExo17();

        this.showExo18();
    }

    // Exercice 11
    private void showExo11(){
        // Partie a
        int array[] = {2, 4, 59, 8, 6, 2, 5, 6, 63, 7};

        // Partie b
        System.out.println("Cours 3 - Exo 11 b) : " + Arrays.toString(array));

        // Partie c
        int count3 = 0;

        for( int i: array ){
            if( i%3 == 0 ){
                count3++;
            }
        }

        System.out.println("Cours 3 - Exo 11 c) : " + count3);

        // Partie d
        int sommePair = 0;

        for( int i: array ){
            if( i%2 == 0 ){
                sommePair += i;
            }
        }

        System.out.println("Cours 3 - Exo 11 d) : " + sommePair);

        // Partie e
        int sommeIPair = 0;

        for( int i=0, j=array.length; i<j; i++ ){
            if( i%2 == 0 ){
                sommeIPair += array[i];
            }
        }

        System.out.println("Cours 3 - Exo 11 e) : " + sommeIPair);

        // Partie f
        int max = 0;

        for( int el: array ){
            if( el > max ){
                max = el;
            }
        }

        System.out.println("Cours 3 - Exo 11 f) : " + max);

        // Partie g
        int first = array[0];

        for( int i=0, j=array.length-1; i<=j; i++ ){
            array[i] = (i == j) ? first : array[i+1];
        }

        System.out.println("Cours 3 - Exo 11 g) : " + Arrays.toString(array));

        // Partie h
        int length = array.length;

        for( int i=0, j=length/2; i<j; i++ ){
            int tmp = array[i];

            array[ i ] = array[ length-i-1 ];
            array[ length-i-1 ] = tmp;
        }

        System.out.println("Cours 3 - Exo 11 h) : " + Arrays.toString(array) );
    }

    // Exercice 16
    private void showExo16(){
        String text = "a ab abcd ef efg efghijk klm lmn lmno pqr rst pqrstu v wxy vwxyz";
        String replaced = text.replaceAll("\\s*\\b\\w{1,3}\\b", "");

        System.out.println("Cours 3 - Exo 16 : " + replaced);
    }

    // Exercice 17
    private void showExo17(){
        int matrice[][] = { {1, 2, 3}, {4, 5, 6}, {7, 8, 9} };
        int len = matrice.length;
        int transpo[][] = new int[len][len];

        for( int i=0; i<len; i++ ){
            for( int k=0, l=matrice[i].length; k<l; k++ ){
                transpo[i][k] = matrice[k][i];
            }
        }

        System.out.println("Cours 3 - Exo 17 : " + Arrays.deepToString(transpo));
    }

    // Exercice 18
    private void showExo18(){
        int matrice[][] = { {4,14,15,1}, {9,7,6,12}, {5,11,10,8}, {16,2,3,13} };
        int len = matrice.length;
        boolean isMagic = true;

        // check lines & columns
        int sum = 0;

        for( int i=0; i<len; i++ ){
            int sumL = 0;
            int sumC = 0;

            for( int k=0; k<len; k++ ){
                sumL += matrice[i][k];
                sumC += matrice[k][i];
            }

            if( i==0 ){
                sum = sumL;
            }
            else if( sum != sumL || sum != sumC ){
                isMagic = false;
            }
        }

        // check diagonals
        int sumD1 = 0;
        int sumD2 = 0;

        for( int i=0; i<len; i++ ){
            sumD1 += matrice[i][i];
            sumD2 += matrice[len-1-i][len-1-i];
        }

        if( sum != sumD1 || sum != sumD2 ){
            isMagic = false;
        }


        // Render
        if( isMagic ){
            System.out.println("Cours 3 - Exo 18 : Is magic.");
        }
        else{
            System.out.println("Cours 3 - Exo 18 : Isn't magic.");
        }
    }
}
