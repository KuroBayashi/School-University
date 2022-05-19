package model.item;

import model.Item;

public class Wall extends Item{

    public final static int ID = 1;

    public Wall(){
        setSprite("Images/mur.jpg");
    }

    public Wall(int x, int y){
        posX = x;
        posY = y;

        setSprite("Images/mur.jpg");
    }

}
