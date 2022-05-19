import java.util.ArrayList;

/**
 * Exercice 2 - Commande
 */
public class LigneCommande {

  private String article;
  private double prixUnitaire;
  private int quantite;

  public double getPrixUnitaire() {
    return prixUnitaire;
  }

  public int getQuantite() {
    return quantite;
  }

  public LigneCommande(String name, double prix, int qte) {
    article = name;
    prixUnitaire = prix;
    quantite = qte;
  }

  public static void main(String[] args) {
    ArrayList<LigneCommande> lignes = new ArrayList<>();

    lignes.add( new LigneCommande("Bac 16L tri-matière renforcé", 86.80, 1) );
    lignes.add( new LigneCommande("Protection ThermaQuiet luminium noir", 14.90, 1) );
    lignes.add( new LigneCommande("Fixation à clip 1/8\" 6.5mm", 4.35, 5) );

    double somme = 0;

    for (LigneCommande command : lignes) {
      somme += command.getQuantite() * command.getPrixUnitaire();
    }

    System.out.println( String.format("Somme : %.2f", somme) );
  }
}
