public class ExceptionIndiceHorsLimites extends Exception {

  public ExceptionIndiceHorsLimites(int indice){
    super("L'indice fourni est en dehors des limites de la liste : "+ indice);
  }
}
