package Exercice1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cargaison {
	
	private int poids = 0;
	private int volume = 0;
	
	private ArrayList<Cargaison> groupedWith = new ArrayList<>();

	public Cargaison(){
        Random rnd = new Random();

		poids  = 5  + rnd.nextInt(5);
		volume = 10 + rnd.nextInt(20);
	}

	public void grouper(Cargaison carg) throws ExceptionPoidsExcede, ExceptionVolumeExcede {
	    int p = poids;
	    int v = volume;

        for(Cargaison c : groupedWith){
            p += c.getPoids();
            v += c.getVolume();
        }

        if( p + carg.getPoids() > 25 ){
			throw new ExceptionPoidsExcede();
		}
		else if( v + carg.getVolume() > 60 ){
			throw new ExceptionVolumeExcede();
		}
		else{
			groupedWith.add(carg);
		}
	}

	public int getPoids(){
		return poids;
	}

	public int getVolume(){
		return volume;
	}

	public ArrayList<Cargaison> getGroupedWith(){
		return groupedWith;
	}

	public String toString(){
		return "["+poids+","+volume+"]";
	}

	public static void main(String[] args) {
		ArrayList<Cargaison> cargs = new ArrayList<>();
		Cargaison c;

		// Question 3
        System.out.println("Question 3 :");
		for(int i=0; i<50; i++){
		    c = new Cargaison();
			cargs.add( c );
			System.out.print( c.toString() +" " );
		}
		System.out.println();

		// Question 5
        int origin = 0;
        for(int i=1; i<cargs.size(); i++){
            try{
                cargs.get(origin).grouper(cargs.get(i));
            }
            catch (ExceptionPoidsExcede | ExceptionVolumeExcede e) {
                origin = i;
            }
        }
		
		// Question 6
        System.out.println("Question 6 : ");
        for(Cargaison carg : cargs){
            if( carg.getGroupedWith().isEmpty() ){
                continue;
            }

            System.out.print( "Group : "+ carg.toString());
            for(Cargaison gpw : carg.getGroupedWith()){
                System.out.print(gpw.toString());
            }
            System.out.println();
        }
	}
}
