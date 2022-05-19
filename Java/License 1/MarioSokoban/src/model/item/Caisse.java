package model.item;

import model.StateableItem;

import javax.swing.*;

public class Caisse extends StateableItem {

    public final static int ID = 2;

    public Caisse(){
        setSprite( false );
    }

    public Caisse(int x, int y){
        posX = x;
        posY = y;
        
        setSprite( false );
    }

    public Caisse(boolean sta){
    	state = sta;
        setSprite( sta );
    }

    public Caisse(int x, int y, boolean sta){
        posX = x;
        posY = y;

        state = sta;
        setSprite(sta);
    }
    
    protected void setSprite(boolean sta){
        if( sta ){
            sprite = new ImageIcon("Images/caisse_ok.jpg");
        }
        else{
            sprite = new ImageIcon("Images/caisse.jpg");
        }
    }
    
    public void setState(boolean sta){
    	state = sta;
    	
    	setSprite(sta);
    }

	public static int getId(boolean sta) {
		return (sta) ? ID+1 : ID;
	}

}
