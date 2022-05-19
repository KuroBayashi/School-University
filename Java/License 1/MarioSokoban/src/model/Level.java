package model;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import model.item.Caisse;
import model.item.Goal;
import model.item.Player;
import model.item.Wall;

public class Level {

    public final static int width  = 12;
    public final static int height = 12;

    private int level = -1;
    
    private List<Item> itemMap = new ArrayList<>(width*height);
    private List<Goal> goalMap = new ArrayList<>();

    public Level(){

    }

    public Level(int lvl){	
        level = (lvl > 0) ? lvl : 1;

        loadMaps();
    }
    
    private void loadMaps(){
        itemMap.clear();
        goalMap.clear();

    	try{
            FileReader content = new FileReader("Levels/"+level+".txt");

            int c, i = 0, j = 0;
            while( (c = Character.getNumericValue( content.read()) ) != -1 ){
            	if( c == Wall.ID ) {
                    itemMap.add( new Wall(j, i) );
                }
                else if( c == Goal.ID ){
                	Goal g = new Goal(j, i);
            	    itemMap.add(g);
            	    goalMap.add(g);
                }
                else if( c == Caisse.ID ){
                    itemMap.add( new Caisse(j, i) );
                }
                else if( c == Caisse.getId(true) ){
                    itemMap.add( new Caisse(j, i, true) );
                    goalMap.add( new Goal(j, i) );
                }
                else if( c == Player.ID ){
                    itemMap.add( new Player(j, i) );
                }

                if(j == width -1){
                    j = 0;
                    i++;
                }else{
                    j++;
                }
                
            }

            content.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<Item> getItemMap(){
    	return itemMap;
    }

    public List<Goal> getGoalMap(){
    	return goalMap;
    }

    public boolean isComplete(){
		int c = 0;
		for( Goal goal: getGoalMap() ){
			for( Item i: getItemMap() ){
				if( StateableItem.class.isAssignableFrom(i.getClass()) && goal.getPosX() == i.getPosX() && goal.getPosY() == i.getPosY() ){
					c++;
					break;
				}
			}
		}

		return c == getGoalMap().size();
	}

    public void reset(){
        loadMaps();
    }

	public boolean next(){
    	if( (new File("Levels/"+ (level+1) +".txt")).isFile() ){
            ++level;
            loadMaps();

            return true;
        }
        return false;
    }

    private String getName(){
	    if(level < 0){
            File[] files = new File("Levels/").listFiles();

            level = (files != null && files.length > 0) ? files.length+1 : 1;
        }

        return ""+level;
    }

    public void save(){
        try {
            FileWriter fw = new FileWriter("Levels/"+getName()+".txt");
            StringBuilder sb = new StringBuilder();

            for( int i=0; i<width*height; i++){
                sb.append("0");
            }

            int start;
            for( Item item : itemMap ){
                start = item.getPosX() + item.getPosY()*width;

                Field field = item.getClass().getDeclaredField("ID");
                field.setAccessible(true);

                sb.replace(start, start+1, field.get(item).toString());
            }

            fw.write(sb.toString());
            fw.close();
        } catch (IOException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void clear(){
        itemMap.clear();
    }

    public void load(String name){
        File[] files = new File("Levels/").listFiles();

        if( files != null ){
            for( File file : files ){
                if( file.isFile() && file.getName().equals(name+".txt") ){
                    level = Integer.parseInt(name);
                    loadMaps();
                    return;
                }
            }
        }

        System.out.println("Level loading failed !");
    }
}
