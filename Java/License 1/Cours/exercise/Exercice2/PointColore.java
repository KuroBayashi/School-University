package Exercice2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class PointColore extends Point{

	private Color color = Color.black;
	
	public PointColore(int posx, int posy){
		super();
		x = posx;
		y = posy;
	}
	
	public void afficher(Graphics g){
		g.setColor(color);
		g.drawString("+", x, y);
	}
	
	public void setColor(Color c){
		color = c;
	}
	
	public Color getColor(){
		return color;
	}
}
