package editor;

import model.Fenetre;

import java.awt.*;

public class EditorFrame {

    public EditorFrame(Fenetre f){
        f.setTitle("Mario Sokoban - Editor");
        f.setSize(414, 437);
        f.removeAll();

        f.add( new EditorPanel() );

        f.setVisible(true);
    }

}
