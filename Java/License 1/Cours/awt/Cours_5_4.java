package fr.cours.awt;

import java.awt.*;
import java.awt.event.*;

public class Cours_5_4 implements WindowListener, ActionListener {

	private Frame f = new Frame();
	private Dessin_4 d = new Dessin_4();


    private Button b1 = new Button("Red");
    private Button b2 = new Button("Blue");
    private Button b3 = new Button("Green");

	// Constructor
	public Cours_5_4(){
		f.setSize(500, 500);
		f.setTitle("Nouvelle fenetre");
		f.setVisible(true);
		f.setResizable(false);

		// Layout
		f.setLayout( new FlowLayout() );

		// Circle
        f.add( d );

        // Buttons
		f.add( b1 );
		f.add( b2 );
		f.add( b3 );

        // Listener
        f.addWindowListener(this);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
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
	
	// Action Listener
	@Override
	public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if( b1 == source ){
            d.setColor( Color.red );
        }
        else if( b2 == source ){
            d.setColor( Color.blue );
        }
        else if( b3 == source ){
            d.setColor( Color.green );
        }

        d.repaint();
	}
}