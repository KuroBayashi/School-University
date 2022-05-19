package fr.cours.awt;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

public class Dessin_1 extends Canvas {
	
	private int score = 0;
	private ArrayList<int[]> arrows = new ArrayList<>();
	
	@Override
	public void paint(Graphics g){
		int x = this.getWidth()/2, y = this.getHeight()/2;
		
		// Target
		g.setColor(Color.blue);
		g.fillOval(x-200, y-200, 400, 400); // Blue - diam : 400
		
		g.setColor(Color.red);
		g.fillOval(x-150, y-150, 300, 300); // Red - diam : 300
		
		g.setColor(Color.yellow);
		g.fillOval(x-75, y-75, 150, 150); // Yellow - diam : 150
		
		g.setColor(Color.black);
		g.fillOval(x-20, y-20, 40, 40); // Black - diam : 40
		
		// Arrow
		int i = 0;
		for( int[] a : arrows ){
			g.setColor(Color.cyan);
			g.fillOval(a[0], a[1], 5, 5);
			g.drawString(""+(++i), a[0], a[1]);
		}
		
		// Score
		g.setFont(new Font("Times New Roman", Font.BOLD, 20));
		g.drawString(""+score, 20, 20);
	}
	
	private void setScore(int x, int y){
		double dist = Math.sqrt( Math.pow(x-this.getWidth()/2, 2) + Math.pow(y-this.getHeight()/2, 2) );
		
		if( 20 >= dist){
			score += 50;
		}
		else if( 75 >= dist ){
			score += 30;
		}
		else if( 150 >=  dist ){
			score += 20;
		}
		else if( 200 >= dist ){
			score += 10;
		}
		else{
			System.out.println("Arrow is out of target.");
		}
	}
	
	void addArrow(int x, int y){
		arrows.add(new int[] {x, y});
		setScore(x, y);
	}
	
	
}
