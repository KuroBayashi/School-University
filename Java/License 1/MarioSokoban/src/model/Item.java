package model;

import java.lang.reflect.Field;

import javax.swing.*;

public abstract class Item {

    protected ImageIcon sprite;

    protected int posX = 0;
    protected int posY = 0;

    protected void setSprite(String filepath){
        sprite = new ImageIcon(filepath);
    }

    public ImageIcon getSprite(){
        return sprite;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int x) {
        posX = x;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int y) {
        posY = y;
    }
    
    public void setPos(int x, int y){
    	setPosX(x);
    	setPosY(y);
    }
    
    public String toString(){
		StringBuilder sb = new StringBuilder();
			sb.append(getClass().getName());
			sb.append(": ");
		
		for (Field f : getClass().getDeclaredFields()) {
			sb.append(f.getName());
			sb.append("=");
			
			try {
				sb.append(f.get(this));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			sb.append(", ");
		}
		
		return sb.toString();
    }

}
