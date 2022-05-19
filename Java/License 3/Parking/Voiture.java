/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parking;

import java.util.Random;

/**
 *
 * @author tboisnie
 */
public class Voiture extends JVoiture implements Runnable {
    
    public final static String ETATS[] = {"Je roule", "Je suis garé", "J'attends une place"};
    
    private Parking parking;
    
    public Voiture(int num, String nom, String etat, Parking parking) {
        super(num, nom, etat);
        
        this.parking = parking;
    }

    @Override
    public void run() {
        Random rng = new Random();
        
        try {
            while (true) {
                // Roule
                this.setEtat(ETATS[0]);
                Thread.sleep(1000 + rng.nextInt(4000));

                // Attend
                this.setEtat(ETATS[2]);
                this.parking.getFileAttente().add(this);

                // Gare
                this.parking.setNbPlace(this.parking.getNbPlace() - 1);
                this.setEtat(ETATS[1]);
                Thread.sleep(1000 + rng.nextInt(4000));

                // Sort
                this.parking.setNbPlace(this.parking.getNbPlace() + 1);
            }
        }
        catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
    
}
