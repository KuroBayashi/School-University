package game;

import model.Fenetre;

@SuppressWarnings("serial")
public class GameFrame{
	
    public GameFrame(Fenetre f){
		f.setTitle("Mario Sokoban - Game");
		f.setSize(414, 437);
		f.removeAll();
        
    	f.add( new GamePanel() );

    	f.setVisible(true);
    }
}
