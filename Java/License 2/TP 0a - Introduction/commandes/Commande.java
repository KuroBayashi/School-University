package commandes;

public class Commande {

  private int identifiant;
  private int codeStatus;

  public static final int EN_COURS = 0;
  public static final int VALIDEE = 1;
  public static final int LIVREE = 2;
  public static final int PAYEE = 3;

  public int getCodeStatus() {
    return codeStatus;
  }

  public static String getPlainStatus(int code) {
    switch(code) {
      case EN_COURS:
        return "En cours";
      case VALIDEE:
        return "Validée";
      case LIVREE:
        return "Livrée";
      case PAYEE:
        return "Payée";
      default:
        return "Inconnu";
    }
  }

  public Commande(int id, int code) {
    codeStatus = code;
    identifiant = id;
  }

  @Override
  public String toString() {
    return "" + identifiant;
  }
}
