package model.item;

import model.Item;

import javax.swing.*;

public class Player extends Item {

    public final static int ID = 5;
    public enum DIR {HAUT, BAS, GAUCHE, DROITE};

    public Player(){    	
    	setSprite(DIR.BAS);
    }
    
    public Player(int x, int y){
    	posX = x;
    	posY = y;
    	
        setSprite(DIR.BAS);
    }

    public void setSprite(DIR dir){
        String img;

        switch(dir){
            case HAUT:
                img = "mario_haut.gif";
                break;
            case GAUCHE:
                img = "mario_gauche.gif";
                break;
            case DROITE:
                img = "mario_droite.gif";
                break;
            default:
                img = "mario_bas.gif";
                break;
        }

        sprite = new ImageIcon("Images/"+img);
    }
}

