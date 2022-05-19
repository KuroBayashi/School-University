package fr.cours.awt;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Dessin_2 extends Canvas {
	
	private Color[] colors = {Color.red, Color.blue, Color.orange, Color.green, Color.magenta};
	private ArrayList<Point> points = new ArrayList<>();
	
	@Override
	public void paint(Graphics g){
		// Point
		for( Point a : points ){
			int x = (int) a.getX(), y = (int) a.getY();
			g.setColor( colors[(x+y)%colors.length] );
			g.fillOval(x, y, 30, 30);
		}
	}
	
	void addPoint(int x, int y){
		boolean del = false;
		for( Point a : points ){
			double dist = Math.sqrt( Math.pow(x-a.getX(), 2) + Math.pow(y-a.getY(), 2) );
			if(dist <= 30){
				points.remove( a );
				del = true;
				break;
			}
		}
		
		if( !del ){
			points.add(new Point(x, y));
		}
	}
	
	
}
