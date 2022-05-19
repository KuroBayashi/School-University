import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Main {

  private Map<String, Situation> situations = new Hashtable<>();

  private void loadData() {
    // Situations
    try (Stream<String> stream = Files.lines(Paths.get("situations.txt"))) {
      this.situations = stream.collect(Collectors.toMap(
        k -> k.split(";")[0],
        v -> new Situation(v.split(";")[1])) )
      ;
    } catch (IOException e) {
      e.printStackTrace();
    }

    // Choix
    try (Stream<String> stream = Files.lines(Paths.get("choix.txt"))) {
      stream.forEach( l -> this.situations
        .get(l.split(";")[0])
        .ajouterChoix(
          new Choix(l.split(";")[2], l.split(";")[3], this.situations.get(l.split(";")[1]))
        )
      );
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void run() {
    Situation situation = this.situations.get("s0");
    Scanner scan = new Scanner(System.in);

    int userChoice;
    while (!situation.estFinale()) {
      while (true) {
        try {
          System.out.println( situation.toString() );
          System.out.print("Votre choix : ");

          userChoice = scan.nextInt();

          if (userChoice < 0 || userChoice >= situation.choix.size())
            throw new Exception("Choix invalide.");

          break;
        } catch (InputMismatchException e) {
          System.out.println("Je n'ai pas compris.\n");
        } catch (Exception e) {
          System.out.println( e.getMessage() + "\n");
        } finally {
          scan.nextLine();
        }
      }

      System.out.println( "\n" + situation.choix.get(userChoice).message + "\n" );
      situation = situation.choix.get(userChoice).situation;
    }
    System.out.println( "\n" + situation.toString() + "\n" );
  }

  private Main() {
    this.loadData();
  }

  public static void main(String[] args) {
    (new Main()).run();
  }

}
