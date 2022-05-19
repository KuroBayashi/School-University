package Exercice3;

public class Polynome {
	
	private int[] coeffs = new int[5];
	
	public Polynome(int x4, int x3, int x2, int x1, int x0){
		coeffs[0] = x0;
		coeffs[1] = x1;
		coeffs[2] = x2;
		coeffs[3] = x3;
		coeffs[4] = x4;
	}
	
	public Polynome somme(Polynome p){
		return new Polynome(this.getx(4)+p.getx(4),this.getx(3)+p.getx(3), this.getx(2)+p.getx(2), this.getx(1)+p.getx(1), this.getx(0)+p.getx(0));
	}
	
	public Polynome deriver(){
		return new Polynome(0, this.getx(4)*4, this.getx(3)*3, this.getx(2)*2, this.getx(1));
	}
	
	
	public String toString(){
	    StringBuilder sb = new StringBuilder();

	    int coeff;
	    for(int i=coeffs.length-1; i>=0; i--){
            coeff = coeffs[i];

            sb.append( (coeff < 0 || i==coeffs.length-1) ? ""+coeff : (coeff > 0) ? "+"+coeff : "");
            sb.append( (coeff !=0 && i > 1) ? "x^"+i : (coeff !=0 && i==1) ? "x" : "" );
        }

		return sb.toString();
	}
	
	public static void main(String[] args) {
		Polynome p1 = new Polynome(2,3,0,5,1);
        Polynome p2 = new Polynome(4,0,2,0,7);
        Polynome p3 = new Polynome(0,0,1,-2,-1);

        System.out.println("Question 3 :");
		System.out.println(p1.toString());
		System.out.println(p2.toString());
		System.out.println(p3.toString());
		System.out.println();

        System.out.println("Question 4 :");
        Polynome s = p1.somme(p2);
		System.out.println(s.toString());
        System.out.println();

        System.out.println("Question 5 :");
		System.out.println( p1.deriver().toString() );
		System.out.println( p2.deriver().toString() );
		System.out.println( p3.deriver().toString() );
	}
	
	public int getx(int i){
		return coeffs[i];
	}

}
