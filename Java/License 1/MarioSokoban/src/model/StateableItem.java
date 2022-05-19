package model;

public abstract class StateableItem extends MovableItem{

	protected static boolean state = false;

    public void setState(boolean b){
    	state = b;
    }
    
    public static boolean getState(){
    	return state;
    }
 
}
