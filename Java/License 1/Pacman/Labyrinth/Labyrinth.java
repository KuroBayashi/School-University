package Labyrinth;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Labyrinth extends JPanel {

  private int width;
  private int height;

  private byte[][] map;

  @Override
  public void paint(Graphics g) {
    int decalage = 25;
    int cSize    = 40;

    g.drawLine(decalage+0, decalage+0, decalage+this.width*cSize, decalage+0);
    g.drawLine(decalage+0, decalage+0, decalage+0, decalage+this.height*cSize);

    for (int y = 0; y < this.height; y++) {
      for (int x = 0; x < this.width; x++) {
        if (((this.map[y][x] >> 1) & 1) == 1)
          g.drawLine(decalage+(x+1)*cSize, decalage+y*cSize, decalage+(x+1)*cSize, decalage+(y+1)*cSize);
        if (((this.map[y][x] >> 2) & 1) == 1)
          g.drawLine(decalage+x*cSize, decalage+(y+1)*cSize, decalage+(x+1)*cSize, decalage+(y+1)*cSize);
      }
    }
  }

  private void generate() {
    Random rand = new Random();
    List<Byte> walls = Arrays.asList(Wall.EAST, Wall.SOUTH);

    for (int y = 0; y < this.height; y++) {
      for (int x = 0; x < this.width; x++) {
        // Must have walls and complete NORTH and WEST walls gesture
        if (y == 0 || ((this.map[y-1][x] >> 2) & 1) == 1)
          this.map[y][x] += Wall.NORTH;
        if (x == 0 || ((this.map[y][x-1] >> 1) & 1) == 1)
          this.map[y][x] += Wall.WEST;
        if (y == this.height-1)
          this.map[y][x] += Wall.SOUTH;
        if (x == this.width-1)
          this.map[y][x] += Wall.EAST;

        // Already 2 walls ? Go to next cell
        if (Integer.bitCount(this.map[y][x]) >= 2)
          continue;

        // EAST and SOUTH gesture
        if (Integer.bitCount(this.map[y][x]) == 0)
          this.map[y][x] += Wall.EAST + Wall.SOUTH;
        else {
          if (y == 0)
            this.map[y][x] += 0;
          else if (x == this.width-1)
            this.map[y][x] += 0;
          else if (((this.map[y][x] & 1) == 0) && (((this.map[y-1][x+1] >> 2) & 1) == 0) && ((this.map[y-1][x+1] >> 3) & 1) == 0)
            this.map[y][x] += Wall.EAST;

          // On ajoute un mur aleatoire si necessaire
          if (Integer.bitCount(this.map[y][x]) < 2){}
            this.map[y][x] += walls.get(rand.nextInt(walls.size()));
        }
      }
    }
  }

  public Labyrinth(int w, int h) throws ExceptionLabyrinth {
    if (w <= 3 || h <= 3)
      throw new ExceptionLabyrinth("Labyrinth width and height must be equals or greater than 3.");

    this.width  = w;
    this.height = h;
    this.map    = new byte[w][h];

    this.generate();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (byte[] l: this.map) {
      for (int v : l) {
        sb.append(v).append("\t");
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}
