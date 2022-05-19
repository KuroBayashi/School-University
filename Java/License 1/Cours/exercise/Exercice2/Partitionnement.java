package Exercice2;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Partitionnement extends Canvas implements WindowListener{

	private ArrayList<PointColore> points     = new ArrayList<>();
	private ArrayList<PointColore> attractors = new ArrayList<>();

	public Partitionnement(){
	    setBounds(0, 0, 300, 300);

		Random rnd = new Random();
		Color[] colors = {Color.red, Color.blue};

		// Add attractors
		for(Color c : colors){
			PointColore pc = new PointColore(rnd.nextInt(300), rnd.nextInt(300));
			pc.setColor( c );
			
			attractors.add( pc );
		}
		
		// Add points
		for(int i=0; i<100; i++){
			PointColore pt = new PointColore(rnd.nextInt(300), rnd.nextInt(300));

			Color currentColor = Color.black;
			double shortestDist = 1000;
            for(PointColore att : attractors){
                double dist = att.distance(pt);

                if( dist < shortestDist ){
                    shortestDist = dist;
                    currentColor = att.getColor();
                }
            }

            pt.setColor(currentColor);
            points.add( pt );
		}
	}
	
	@Override
	public void paint(Graphics g){
		for(PointColore pt : points){
			pt.afficher(g);
		}
		
		for(PointColore att : attractors){
			g.setColor(att.getColor());
			g.fillOval(att.x, att.y, 10, 10);
		}
	}
	
	
	
	
	/**
	 * Main function
	 * @param args
	 */
	public static void main(String[] args) {
		Frame f = new Frame();
		f.setSize(300, 300);
		f.setTitle("Partitionnement");
		
		f.add(new Partitionnement());

		f.addWindowListener(new Partitionnement());
		f.setVisible(true);
	}

    @Override
    public void windowOpened(WindowEvent e){}

    @Override
    public void windowClosing(WindowEvent e){
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e){}

    @Override
    public void windowIconified(WindowEvent e){}

    @Override
    public void windowDeiconified(WindowEvent e){}

    @Override
    public void windowActivated(WindowEvent e){}

    @Override
    public void windowDeactivated(WindowEvent e){}
}
