import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Main {
    
    public static void main(String[] args) {
        // File read
        try (
            Scanner scanner = new Scanner(new File("resources/matrice10.txt"));
        ) {
            //CacheDirect cache = new CacheDirect(2);
            CacheAssociatif cache = new CacheAssociatif(3, 2);
            int success = 0;
            int fail    = 0;
            
            // Run
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(":");

                System.out.print(Integer.parseInt(line[0]) + " " + line[1]);
                System.out.print(" (" + cache.getEtiquette(Integer.parseInt(line[0])) + ", " + cache.getPosition(Integer.parseInt(line[0])) + ")");

                if (cache.get(Integer.parseInt(line[0]))) {
                    ++success;
                    System.out.print(" -- SUCCESS");
                }
                else {
                    ++fail;
                    cache.add(Integer.parseInt(line[0]));
                }

                System.out.println();
            }
        
            // Result
            System.out.println();
            System.out.println("Success : " + success);
            System.out.println("Fail : " + fail);
            System.out.println("Average : " + ((success * 5 + fail * 50) / (success + fail)) + "ns");
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
}
