import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileContigueTest {

  private FileChaine fc;

  @BeforeEach
  void setUp() {
    this.fc = new FileChaine();
  }

  @AfterEach
  void tearDown() {

  }

  @Test
  void testEstVide() {
    try {
      assertEquals(fc.est_vide(), true);
      assertEquals(fc.enfiler("a").est_vide(), false);
      System.out.println(fc.premier());
      System.out.println(fc.defiler().premier());
      assertEquals(fc.defiler().est_vide(), true);
    } catch (ExceptionFileVide e) {
      assertEquals(e.getMessage().startsWith("ExceptionFileVide"), true);
    }
  }

  @Test
  void testPremier() {
    Assertions.assertThrows(ExceptionFileVide.class, () -> {
      fc.premier();
    });
  }

  @Test
  void testDefiler() {
    Assertions.assertThrows(ExceptionFileVide.class, () -> {
      fc.defiler();
    });
  }

  @Test
  void testOrder() {
    try {
      fc.enfiler("a").enfiler("b").enfiler("c");
      assertEquals(fc.premier(), "a");
      fc.defiler();
      assertEquals(fc.premier(), "b");
      fc.defiler();
      assertEquals(fc.premier(), "c");
      fc.defiler();
    } catch (ExceptionFileVide e) {
      assertEquals(e.getMessage().startsWith("ExceptionFileVide"), true);
    }
  }
}