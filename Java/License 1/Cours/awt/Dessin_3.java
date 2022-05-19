package fr.cours.awt;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Dessin_3 extends Canvas {
	
    private Point point = new Point();
	
	@Override
	public void paint(Graphics g){
		int x = (int) point.getX(), y = (int) point.getY();
		
		g.setColor( Color.darkGray );
		g.fillRect(x, y, 30, 30);
	}
	
	void setPosition(int x, int y){
		int xx = (int) (point.getX()+x), yy = (int) (point.getY()+y);

		if( 0 > xx || 0 > yy || this.getWidth() < xx+30 || this.getHeight() < yy+30 ){
			return;
		}
			
		point.translate(x, y);
	}
}
