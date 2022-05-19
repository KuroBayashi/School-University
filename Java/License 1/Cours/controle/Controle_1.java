package fr.cours.controle;

import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Point2D;

public class Controle_1 implements WindowListener, MouseListener {
	
	private Frame f = new Frame();
	private Dessin_1 d = new Dessin_1();

	private double x1, y1;

	// Constructor
	public Controle_1(){
		f.setSize(500, 500);
		f.setTitle("Nouvelle fenetre");
		f.setVisible(true);
		f.setResizable(false);
		
		f.addWindowListener(this);
		d.addMouseListener(this);

		f.add( d );
	}
	
	// Window Listener 
	@Override
	public void windowActivated(WindowEvent arg0) {}

	@Override
	public void windowClosed(WindowEvent arg0) {}

	@Override
	public void windowClosing(WindowEvent arg0) { System.exit(0); }

	@Override
	public void windowDeactivated(WindowEvent arg0) {}

	@Override
	public void windowDeiconified(WindowEvent arg0) {}

	@Override
	public void windowIconified(WindowEvent arg0) {}

	@Override
	public void windowOpened(WindowEvent arg0) {}
	
	// Mouse Listener
	@Override
	public void mouseClicked(MouseEvent e) {}
	
	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
	    x1 = e.getX();
	    y1 = e.getY();
    }

	@Override
	public void mouseReleased(MouseEvent e) {
        d.addSegment( x1, y1, e.getX(), e.getY() );

        d.repaint();
    }
}