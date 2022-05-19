package commandes;

import java.util.Vector;

/**
 * Exercice 4 - Catalogue clients
 */

public class FichierClient {

  private Vector<Client> catalogue = new Vector<>();

  public Vector<Client> clientsVierges() {
    Vector<Client> cleans = new Vector<>();

    for (Client client : catalogue) {
      if (client.getHistorique().size() == 0)
        cleans.add(client);
    }

    return cleans;
  }

  public Vector<Commande> listerCommandes(int code) {
    Vector<Commande> commands = new Vector<>();

    for (Client client : catalogue) {
      for (Commande commande : client.getHistorique()) {
        if (commande.getCodeStatus() == code)
          commands.add(commande);
      }
    }

    return commands;
  }

  public FichierClient() {
    catalogue.add( (new Client("Martin", "Valérie", "Albi"))
      .ajouterCommande( new Commande(12897, Commande.EN_COURS) )
      .ajouterCommande( new Commande(86416, Commande.VALIDEE) )
      .ajouterCommande( new Commande(98716, Commande.LIVREE) )
    );
    catalogue.add( (new Client("Ndiaye", "Marie", "Dakar"))
        .ajouterCommande( new Commande(61573, Commande.EN_COURS) )
        .ajouterCommande( new Commande(36475, Commande.LIVREE) )
    );
    catalogue.add( (new Client("Smith", "Peter", "New-York")) );
  }

  public static void main(String[] args) {
    FichierClient fc = new FichierClient();

    Vector<Client> cleans = fc.clientsVierges();
    for (Client client : cleans) {
      System.out.println("Client (Clean) : " + client.toString());
    }

    Vector<Commande> commands = fc.listerCommandes(Commande.EN_COURS);
    System.out.print( "Commande (" + Commande.getPlainStatus(Commande.EN_COURS) + ") :" );
    for (Commande commande : commands) {
      System.out.print( " " + commande.toString() );
    }
    System.out.println();
  }

}
