package menu;

import java.awt.*;

import editor.EditorFrame;
import game.GameFrame;
import main.Mario;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created on 27/03/2017.
 */
public class MenuPanel extends Panel implements ActionListener{

    protected Button play = new Button("Play");
    protected Button edit = new Button("Level editor");

    MenuPanel(){
        add(play);
        add(edit);

        play.addActionListener(this);
        edit.addActionListener(this);
    }

    @Override
    public void paint(Graphics g){

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if( e.getSource() == play ){
            GameFrame gfrm = new GameFrame( Mario.fenetre );
        }
        else if( e.getSource() == edit ){
            EditorFrame efrm = new EditorFrame( Mario.fenetre );
        }
    }
}
