package fr.cours.awt;

import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Cours_5_1 implements WindowListener, MouseListener {
	
	private Frame f = new Frame();
	private Dessin_1 d = new Dessin_1();
	
	// Constructor
	public Cours_5_1(){
		f.setSize(500, 500);
		f.setTitle("Nouvelle fenetre");
		f.setVisible(true);
		f.setResizable(false);
		
		f.addWindowListener(this);
		d.addMouseListener(this);
		
		showExo1();
	}
	
	// Exercice 1
	private void showExo1(){
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
	public void mouseClicked(MouseEvent e) {
		d.addArrow(e.getX(), e.getY());
		d.repaint();
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
}