/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parking;

import java.util.concurrent.ConcurrentLinkedQueue;


public class Parking extends JParking {

    public final static String ETATS[] = {"Vide", "Libre", "Plein"};
    
    private final static int NB_PLACE = 5;
    
 
    private int nb_place;
    private ConcurrentLinkedQueue file_attente;
    
    public Parking(int num, String nom, String etat) {
        super(num, nom, etat);
        
        this.nb_place = NB_PLACE;
        this.file_attente = new ConcurrentLinkedQueue();
    }
    
    
    public int getNbPlace() {
        return this.nb_place;
    }
    
    public void setNbPlace(int nb_place) {
        this.nb_place = nb_place;
    }
    
    public boolean hasFreePlace() {
        return this.nb_place > 0;
    }
    
    public ConcurrentLinkedQueue getFileAttente() {
        return this.file_attente;
    }
    
    
    
    public static void main(String[] args) {
        final int NB_VOITURE = 10;
        
        Parking parking = new Parking(1, "Parking", Parking.ETATS[0]);
        
        for (int i = 0; i < NB_VOITURE; ++i) {
            new Thread( new Voiture(i, "Voiture "+ i, Voiture.ETATS[0], parking)).start();
        }
        
        while (true) {
            while (!parking.getFileAttente().isEmpty() && parking.hasFreePlace()) {
                parking.getFileAttente().poll();
            }
            
            if (parking.getNbPlace() == 0) {
                parking.setEtat(Parking.ETATS[2]);
            }
            else if (parking.getNbPlace() == Parking.NB_PLACE) {
                parking.setEtat(Parking.ETATS[0]);
            }
            else {
                parking.setEtat(Parking.ETATS[1]);
            }
        }
        
    }
    
}
