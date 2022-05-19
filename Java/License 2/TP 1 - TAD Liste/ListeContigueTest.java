import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ListeContigueTest {

  private Liste l;

  @BeforeEach
  void setUp() {
    l = new ListeContigue();
  }

  @AfterEach
  void tearDown() {

  }

  @Test
  void testLongueur() {
    assertEquals(l.longueur(), 0);
  }

  @Test
  void testInsertion() {
    try {
      l.inserer(0, 'a');
      assertEquals(l.longueur(), 1);
      assertEquals(l.ieme(0), 'a');
    } catch (ExceptionIndiceHorsLimites e) {
      assertEquals(e.getMessage().startsWith("L'indice fourni est en dehors des limites de la liste : "), true);
    }
  }

  @Test
  void testInsertionMauvaisIndice() {
      Assertions.assertThrows(ExceptionIndiceHorsLimites.class, () -> {
        l.inserer(1, 'a');
      });
  }

}