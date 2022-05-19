package fr.cours.controle;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Dessin_1 extends Canvas {

	private ArrayList<Line2D.Double> segments = new ArrayList<>();

	Dessin_1(){
        addSegment(  0,   0, 500, 500);
        addSegment(  0, 500, 500,   0);
        addSegment(  0, 250, 500, 250);
        addSegment(250,   0, 250, 500);
    }

	@Override
	public void paint(Graphics g){
        for( Line2D.Double s : segments ){
            int x1 = (int) s.getX1(), y1 = (int) s.getY1(), x2 = (int) s.getX2(), y2 = (int) s.getY2();

            // Color
            if( x1 == x2 ){
                g.setColor( Color.red );
            }
            else if( y1 == y2 ){
                g.setColor( Color.blue );
            }
            else if( 0 > (x2-x1)/(y2-y1) ){
                g.setColor( Color.magenta );
            }
            else{
                g.setColor( Color.green );
            }

            // Line
            g.drawLine(x1, y1, x2, y2);
        }

	}

	void addSegment(double x1, double y1, double x2, double y2){
        if( x1 == x2 && y1 == y2 ){
            System.out.println("Segment null not added.");
            return;
        }

	    segments.add( new Line2D.Double(x1, y1, x2, y2) );
	}

	
}
