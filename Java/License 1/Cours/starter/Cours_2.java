package fr.cours.starter;


public class Cours_2 {

    // Show all exercices
    public void showAll(){
        this.showExo12();

        this.showExo13();

        this.showExo14();

        this.showExo15();
    }

    // Exercice 12
    private void showExo12(){
        String s1 = "abc";
        String s2 = "def";

        int compare = s1.compareTo(s2);

        System.out.print("Cours 2 - Exo 12 : ");

        if( compare < 0 ){
            System.out.print(s1 + " < " + s2);
        }
        else if( compare > 0 ){
            System.out.print(s2 + " < " + s1);
        }
        else{
            System.out.print(s1 + " = " + s2);
        }

        System.out.println();
    }

    // Exercice 13
    private void showExo13(){
        String s1 = "abcdefghi";

        System.out.println("Cours 2 - Exo 13 : " + (s1.indexOf('e') != -1) );
    }

    // Exercice 14
    private void showExo14(){
        String origine = "plolp";
        String reverse = "";

        for( int i=origine.length()-1; i>=0; i-- ){
            reverse += origine.charAt(i);
        }

        if( origine.equals(reverse) ){
            System.out.println("Cours 2 - Exo 14 : Is a palindrome");
        }
        else{
            System.out.println("Cours 2 - Exo 14 : Isn't a palindrome");
        }
    }

    // Exercice 15
    private void showExo15(){
        String text = "Ceci est une [chaine de] caractères.";

        System.out.println("Cours 2 - Exo 15 : " + text.substring(text.indexOf('['), text.indexOf(']')+1));
    }
}
