package fr.cours.controle;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Controle_2 implements WindowListener, MouseListener {

	private Frame f = new Frame();
	private Dessin_2 d = new Dessin_2();

	// Constructor
	public Controle_2(){
		f.setSize(300, 300);
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
	public void mouseClicked(MouseEvent e) {
		d.hideCase(e.getX(), e.getY());
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