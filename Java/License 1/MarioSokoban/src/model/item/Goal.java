package model.item;

import model.Item;

public class Goal extends Item{

    public final static int ID = 4;

    public Goal(){
        setSprite("Images/objectif.png");
    }

    public Goal(int x, int y){
        posX = x;
        posY = y;

        setSprite("Images/objectif.png");
    }

}
