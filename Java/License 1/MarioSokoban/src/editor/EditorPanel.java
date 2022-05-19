package editor;

import model.Item;
import model.Level;
import model.item.Caisse;
import model.item.Goal;
import model.item.Player;
import model.item.Wall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created on 26/03/2017.
 */
public class EditorPanel extends Panel implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private final static int itemSize = 34;

    private Item[] items = {new Caisse(), new Goal(), new Player(), new Wall()};
    private int currentItemIndex = 0;
    private Item currentItem = items[currentItemIndex];

    private Level level = new Level();

    EditorPanel(){
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        addKeyListener(this);
    }

    @Override
    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        for( Item item: level.getItemMap() ){
            g2d.drawImage(item.getSprite().getImage(), item.getPosX()*itemSize, item.getPosY()*itemSize, itemSize, itemSize, null);
        }

        g2d.drawImage(currentItem.getSprite().getImage(), currentItem.getPosX(), currentItem.getPosY(), itemSize, itemSize, null);
    }

    @Override
    public void mouseClicked(MouseEvent e){
        int x = e.getX() / itemSize;
        int y = e.getY() / itemSize;

        level.getItemMap().removeIf(item -> item.getPosX() == x && item.getPosY() == y);

        if( e.getButton() == MouseEvent.BUTTON1 ){
            try {
                Item i = currentItem.getClass().newInstance();
                i.setPos(x, y);

                level.getItemMap().add( i );
            } catch (InstantiationException | IllegalAccessException e1) {
                e1.printStackTrace();
            }
        }

        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e){}

    @Override
    public void mouseReleased(MouseEvent e){}

    @Override
    public void mouseEntered(MouseEvent e){}

    @Override
    public void mouseExited(MouseEvent e){}

    @Override
    public void mouseDragged(MouseEvent e){}

    @Override
    public void mouseMoved(MouseEvent e){
        currentItem.setPos(e.getX(), e.getY());

        repaint();

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e){
        if( e.getWheelRotation() < 0 ){
            if( currentItemIndex > 0 ){
                currentItemIndex--;
            }
            else{
                currentItemIndex = items.length -1;
            }
        }
        else if( e.getWheelRotation() > 0 ){
            if( currentItemIndex < items.length -1 ){
                currentItemIndex++;
            }
            else{
                currentItemIndex = 0;
            }
        }

        currentItem = items[currentItemIndex];
        currentItem.setPos(e.getX(), e.getY());

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_S:
                level.save();
                break;
            case KeyEvent.VK_R:
                level.clear();
                break;
            case KeyEvent.VK_L:
                level.load( JOptionPane.showInputDialog("Which level would you want to load ?") );
                break;
        }
    }
}
