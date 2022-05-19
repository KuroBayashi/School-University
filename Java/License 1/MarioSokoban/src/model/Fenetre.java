package model;

import menu.MenuFrame;

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

@SuppressWarnings("serial")
public class Fenetre extends Frame implements KeyListener, WindowListener {

    public Fenetre(){
        this.setTitle("Nouvelle fenetre");
    	this.setSize(500, 350);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        this.setFocusable(true);

        this.addWindowListener(this);
        this.addKeyListener(this);
    }

	@Override
	public void windowActivated(WindowEvent arg0) {}

	@Override
	public void windowClosed(WindowEvent arg0) {}

	@Override
	public void windowClosing(WindowEvent arg0) {
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {}

	@Override
	public void windowDeiconified(WindowEvent arg0) {}

	@Override
	public void windowIconified(WindowEvent arg0) {}

	@Override
	public void windowOpened(WindowEvent arg0) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println(e.getKeyCode() +"  "+ KeyEvent.VK_ESCAPE);
        if( e.getKeyCode() == KeyEvent.VK_ESCAPE ){
            new MenuFrame(this);
        }
    }
}
