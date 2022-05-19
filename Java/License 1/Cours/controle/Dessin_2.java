package fr.cours.controle;

import java.awt.*;
import java.util.Calendar;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Dessin_2 extends Canvas {

    private int[][] grille = new int[5][5];
    private long start = 0;

    Dessin_2(){
        Random rnd = new Random();

        for( int i=0; i<grille.length; i++ ){
            for( int j=0; j<grille[i].length; j++ ){
                grille[i][j] = rnd.nextInt(100);
            }
        }
    }


    @Override
    public void paint(Graphics g) {
        boolean completed = true;

        for( int i=0; i<grille.length; i++ ){
            for( int j=0; j<grille[i].length; j++ ){
                if( -1 == grille[i][j] ) {
                    g.setColor(Color.red);
                }
                else{
                    g.setColor( Color.black );
                    completed = false;
                }

                g.drawRect(50*j, 50*i, 50, 50);
                g.drawString(""+grille[i][j], 50*j+20, 50*i+30);
            }
        }

        if( completed ){
            long time = Calendar.getInstance().getTimeInMillis() - start;

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            g.setColor( Color.white );
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.setColor( Color.black );
            g.drawString( "Game Completed in : " + (time/1000) + "seconds.", 30, this.getHeight()/2-30 );
        }
    }

    void hideCase(double x, double y){
        int a = (int) y/50, b = (int) x/50, v=-1;
        
        startTimer();
        
        try{
            v = grille[a][b];
        } catch( Exception e ){
            System.out.println("Click out of matrix.");
        }

        for( int[] l : grille ){
            for( int m : l ){
                if( v < m ){
                    return;
                }
            }
        }
        
        grille[a][b] = -1;
    }


    void startTimer(){
        if( 0 == start ){
             start = Calendar.getInstance().getTimeInMillis();
        }
    }
}
