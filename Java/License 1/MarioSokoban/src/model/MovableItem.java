package model;

public abstract class MovableItem extends Item{

    protected boolean movable = true;

    public void setMovable(boolean b){
        movable = b;
    }

    public boolean isMovable(){
        return movable;
    }
}
