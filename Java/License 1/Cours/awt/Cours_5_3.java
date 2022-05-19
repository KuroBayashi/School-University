package fr.cours.awt;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Cours_5_3 implements WindowListener, KeyListener {
	
	private Frame f = new Frame();
	private Dessin_3 d = new Dessin_3();
	
	// Constructor
	public Cours_5_3(){
		f.setSize(500, 500);
		f.setTitle("Nouvelle fenetre");
		f.setVisible(true);
		f.setResizable(false);
		
		f.addWindowListener(this);
		d.addKeyListener(this);
		
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

	// Key Listener
	@Override
	public void keyPressed(KeyEvent e) {
		if( e.getKeyCode() == KeyEvent.VK_LEFT){
			d.setPosition(-1, 0);
		}
		if( e.getKeyCode() == KeyEvent.VK_RIGHT){
			d.setPosition(1, 0);
		}
		
		if( e.getKeyCode() == KeyEvent.VK_UP){
			d.setPosition(0, -1);
		}
		if( e.getKeyCode() == KeyEvent.VK_DOWN){
			d.setPosition(0, 1);
		}
		
		d.repaint();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}
	
	
}