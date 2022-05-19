package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.Item;
import model.Level;
import model.MovableItem;
import model.StateableItem;
import model.item.*;

@SuppressWarnings("serial")
public class GamePanel extends Panel implements KeyListener {
	
	private final static int itemSize = 34;
	
	private Level level = new Level(1);
	private Player player;
	
	public GamePanel(){
		for( int i=0; i<level.getItemMap().size(); i++){
			if( level.getItemMap().get(i).getClass() == (new Player()).getClass() ){
				player = (Player) level.getItemMap().get(i);
				break;
			}
		}
		
		addKeyListener(this);
	}
	
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		for( Item item: level.getItemMap() ){
			if( item.getClass() == (new Player()).getClass() ){
				player = (Player) item;
			}
			
			g2d.drawImage(item.getSprite().getImage(), item.getPosX()*itemSize, item.getPosY()*itemSize, itemSize, itemSize, null);
		}

		if( level.isComplete() ){
			g2d.setColor(Color.black);
			g2d.setFont( new Font("default", Font.BOLD, 32) ); 
			g2d.drawString("Congratz", 125, 150);

            g2d.setFont( new Font("default", Font.BOLD, 12) );

            if( level.next() ){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                repaint();
            }
		}
	}

	protected void setMap(int currentPlayerX, int currentPlayerY, int newPlayerX, int newPlayerY, int newMovableX, int newMovableY){
        // Player goes out of map
        if( 0 > newPlayerX || Level.width < newPlayerX || 0 > newPlayerY || Level.height < newPlayerY ){
            System.out.println("Player goes out of map.");
            return;
        }
        else{
            // Check if object already exists on new player position
            mainloop:
            for(Item i: level.getItemMap()){
                // Object already exists on new player position
                if( i.getPosX() == newPlayerX && i.getPosY() == newPlayerY){
                    // Object is a goal (can be hovered)
                    if( Goal.class.isAssignableFrom(i.getClass()) ){
                        level.getItemMap().remove(i);
                        break;
                    }
                    // Object can be move
                    if( MovableItem.class.isAssignableFrom(i.getClass()) && ((MovableItem) i).isMovable() ){
                        // Object goes out of map
                        if( 0 > newMovableX || Level.width < newMovableX || 0 > newMovableY || Level.height < newMovableY ){
                            System.out.println("Movable item goes out of map.");
                            return;
                        }
                        // Check if movable item new position is empty or a goal
                        for(Item j: level.getItemMap()){
                            // Object already exists on new movable item position
                            if(j.getPosX() == newMovableX && j.getPosY() == newMovableY){
                                // Object is a goal position
                                if( Goal.class.isAssignableFrom(j.getClass()) ){
                                    if( StateableItem.class.isAssignableFrom(i.getClass()) ){
                                        StateableItem si = (StateableItem) i;

                                        si.setState(true);
                                        si.setPos(newMovableX, newMovableY);
                                        level.getItemMap().remove(j);

                                        break mainloop;
                                    }
                                }
                                else{
                                    System.out.println("MovableItem is block by another one.");
                                    return;
                                }
                            }
                        }

                        if( StateableItem.class.isAssignableFrom(i.getClass()) ){
                            StateableItem si = (StateableItem) i;
                            si.setState(false);
                            si.setPos(newMovableX, newMovableY);

                            break;
                        }
                        else{
                            i.setPos(newMovableX, newMovableY);
                            player.setPos(newPlayerX, newPlayerY);
                        }
                    }
                    else{
                        return;
                    }
                }
            }

            // No object on new player position
            player.setPos(newPlayerX, newPlayerY);

            // Add goal position
            for(Goal goal: level.getGoalMap()){
                if( goal.getPosX() == currentPlayerX && goal.getPosY() == currentPlayerY ){
                    level.getItemMap().add( new Goal(currentPlayerX, currentPlayerY));
                }
            }
        }
    }

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {
		int currentPlayerX, newPlayerX, newMovableX;
		int currentPlayerY, newPlayerY, newMovableY;
		currentPlayerX = newPlayerX = newMovableX = player.getPosX();
		currentPlayerY = newPlayerY = newMovableY = player.getPosY();

		switch( e.getKeyCode() ){
			case KeyEvent.VK_UP:
				--newPlayerY;
				newMovableY = newPlayerY -1;
				player.setSprite(Player.DIR.HAUT);
				break;
			case KeyEvent.VK_LEFT:
				--newPlayerX;
				newMovableX = newPlayerX -1;
                player.setSprite(Player.DIR.GAUCHE);
				break;
			case KeyEvent.VK_RIGHT:
				++newPlayerX;
				newMovableX = newPlayerX +1;
                player.setSprite(Player.DIR.DROITE);
				break;
			case KeyEvent.VK_DOWN:
				++newPlayerY;
				newMovableY = newPlayerY +1;
                player.setSprite(Player.DIR.BAS);
				break;
            case KeyEvent.VK_R:
                level.reset();
                player.setSprite(Player.DIR.HAUT);
                break;
		}

        setMap(currentPlayerX, currentPlayerY, newPlayerX, newPlayerY, newMovableX, newMovableY);
		
		repaint();
	}
}
