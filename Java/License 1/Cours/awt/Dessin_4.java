package fr.cours.awt;

import java.awt.*;

public class Dessin_4 extends Canvas {

    private Color color = Color.blue;

	@Override
	public void paint(Graphics g){
	    int d = 150;

		g.setColor( color );
		g.fillOval( (this.getWidth()-d)/2, (this.getHeight()-d)/2, d, d);
	}

	@Override
    public Dimension getPreferredSize(){

        return new Dimension(500, 400);
    }

    void setColor(Color c){
        color = c;
    }
}
