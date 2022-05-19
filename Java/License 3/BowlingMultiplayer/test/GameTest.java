/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bowlingmultiplayer;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author pedago
 */
public class GameTest {

    private Game game;

    // Helpers
    private String getMessage(String name, int tour, int boule) {
        return "Prochain tir : joueur " + name + ", tour n°" + tour + ", boule n°" + boule;
    }


    // TEST
    @Before
    public void setUp() {
        game = new Game();
    }

    /**
     * Recuperation des noms des joueurs
     */
    @Test
    public void testGetPlayersNames() {
        try {
            game.startNewGame(new String[]{"David", "Sarah"});
            assertTrue(game.getPlayersNames().contains("David"));
            assertTrue(game.getPlayersNames().contains("Sarah"));
            assertTrue(!game.getPlayersNames().contains("Charles"));
        } catch (Exception e) {
            fail();
        }
    }

    /**
     * Si il n'y a pas de joueur
     */
    @Test
    public void testNullPlayers() {
        try {
            game.startNewGame(null);
            fail();
        } catch (Exception e) {
            assertEquals("Au moins 1 joueur est requis.", e.getMessage());
        }
    }

    /**
     * Si il n'y a pas de joueur
     */
    @Test
    public void testNoPlayer() {
        try {
            game.startNewGame(new String[]{});
            fail();
        } catch (Exception e) {
            assertEquals("Au moins 1 joueur est requis.", e.getMessage());
        }
    }

    /**
     * Si il y a des joueurs "null"
     */
    @Test
    public void testNullPlayer(){
        try {
            game.startNewGame(new String[]{null, "David", "Sarah", null, "Alice"});
            assertEquals(3, game.getPlayersNames().toArray().length);
        } catch(Exception e) {
            fail();
        }
    }

    /**
     * Message début de partie
     */
    @Test
    public void testFirstPlayerMessage(){
        try {
            assertEquals(getMessage("David", 1, 0), game.startNewGame(new String[]{"David", "Sarah", "Alice"}));
        } catch(Exception e) {
            fail();
        }
    }

    /**
     * Tout le monde ne fait que des strikes
     */
    @Test
    public void testAllStrike(){
        try {
            game.startNewGame(new String[]{"David", "Sarah", "Alice"});

            for (int i = 0; i < 3*12; ++i)
                game.lancer(10);
        } catch(Exception ignored) {
            fail("Erreur dans les lancers");
        }

        try {
            assertEquals(300, game.scoreFor("David"));
            assertEquals(300, game.scoreFor("Sarah"));
            assertEquals(300, game.scoreFor("Alice"));
        } catch (Exception e) {
            fail("Erreur dans les scores ");
        }
    }

    /**
     * Message joueur suivant
     */
    @Test
    public void testMessageNextLancer(){
        try {
            assertEquals(getMessage("David", 1, 0), game.startNewGame(new String[]{"David", "Sarah"}));
            assertEquals(getMessage("Sarah", 1, 0), game.lancer(10));
            assertEquals(getMessage("David", 2, 0), game.lancer(10));
            assertEquals(getMessage("David", 2, 1), game.lancer(5));
            assertEquals(getMessage("Sarah", 2, 0), game.lancer(2));
            assertEquals(getMessage("David", 3, 0), game.lancer(10));
        } catch(Exception e) {
            fail();
        }
    }

    /**
     * Message fin de partie
     */
    @Test
    public void testFinDePartie(){
        try {
            game.startNewGame(new String[]{"Bob", "Alice"});
            for (int i = 0; i < 2*21+1; i++) {
                game.lancer(5);
            }
        } catch(Exception e) {
            assertEquals("Le jeu est fini", e.getMessage());
        }
    }

}
