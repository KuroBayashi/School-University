package menu;

import model.Fenetre;

import java.awt.*;

public class MenuFrame {

    public MenuFrame(Fenetre f){
        f.setTitle("Mario Sokoban - Menu");
        f.setSize(300, 200);
        f.removeAll();

        f.add( new MenuPanel() );

        f.setVisible(true);
    }

}
