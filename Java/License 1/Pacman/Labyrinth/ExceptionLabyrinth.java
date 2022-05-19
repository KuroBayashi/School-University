package Labyrinth;

public class ExceptionLabyrinth extends Exception {

  ExceptionLabyrinth(String message) {
    super("Labyrinth exception : " + message);
  }

}
