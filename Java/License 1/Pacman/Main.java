import Labyrinth.Labyrinth;

import javax.swing.*;

class Main {

  public static void main(String[] args) {
    try {
      JFrame f = new JFrame();
      f.setSize(900, 720);
      f.setTitle("Labyrinth");
      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      Labyrinth labyrinth = new Labyrinth(10,10);

      System.out.println(labyrinth.toString());
      f.add(labyrinth);

      f.setVisible(true);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
