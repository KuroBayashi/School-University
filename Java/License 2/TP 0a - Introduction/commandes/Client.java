package commandes;

import java.util.Vector;

public class Client {

  private String nom;
  private String prenom;
  private String ville;
  private Vector<Commande> historique = new Vector<>();

  public Vector<Commande> getHistorique() {
    return historique;
  }

  public Client ajouterCommande(Commande c) {
    historique.add(c);
    return this;
  }

  public Client(String name, String fname, String city) {
    nom = name;
    prenom = fname;
    ville = city;
  }

  @Override
  public String toString() {
    return prenom + " " + nom + " (" + ville + ")";
  }
}