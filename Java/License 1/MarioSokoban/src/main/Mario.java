package main;

import menu.MenuFrame;
import model.Fenetre;

public class Mario {

    public static Fenetre fenetre = new Fenetre();

    private void execute(){
        new MenuFrame( fenetre );
    }


    public static void main(String[] args){
        Mario m = new Mario();
        m.execute();
    }
}
