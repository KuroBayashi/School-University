/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bowlingmultiplayer;

import bowling.MultiPlayerGame;
import bowling.SinglePlayerGame;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author pedago
 */
public class Game implements MultiPlayerGame {

    private Map<String, SinglePlayerGame> players = null;
    private Iterator<String> players_iterator = null;

    private String current_player_name = null;

    /**
     * Démarre une nouvelle partie pour un groupe de joueurs
     * @param playerName un tableau des noms de joueurs (il faut au moins un joueur)
     * @return une chaîne de caractères indiquant le prochain joueur,
     * de la forme "Prochain tir : joueur Bastide, tour n° 5, boule n° 1"
     * @throws java.lang.Exception si le tableau est vide ou null
     */
    @Override
    public String startNewGame(String[] playerName) throws Exception {
        if (playerName == null || Arrays.stream(playerName).noneMatch(Objects::nonNull))
            throw new Exception("Au moins 1 joueur est requis.");
        
        players = Arrays
            .stream(playerName)
            .filter(Objects::nonNull)
            .collect(
                Collectors.toMap(
                    name -> name,
                    name -> new SinglePlayerGame(),
                    (name_1, name_2) -> name_1,
                    LinkedHashMap::new
                )
            );

        return getMessage(getNextPlayerName());
    }

    /**
     * Enregistre le nombre de quilles abattues pour le joueur courant, dans le frame courant, pour la boule courante
     * @param nombreDeQuillesAbattues : nombre de quilles abattue à ce lancer
     * @return une chaîne de caractères indiquant le prochain joueur,
     * de la forme "Prochain tir : joueur Bastide, tour n° 5, boule n° 1",
     * ou bien "Partie terminée" si la partie est terminée.
     * @throws java.lang.Exception si la partie n'est pas démarrée, ou si elle est terminée.
     */
    @Override
    public String lancer(int nombreDeQuillesAbattues) throws Exception {
        if (players == null)
            throw new Exception("La partie n'est pas démarrée.");

        players.get(current_player_name).lancer(nombreDeQuillesAbattues);

        return getMessage(getNextPlayerName());
    }

    /**
     * Donne le score pour le joueur playerName
     * @param playerName le nom du joueur recherché
     * @return le score pour ce joueur
     * @throws Exception si le playerName ne joue pas dans cette partie
     */
    @Override
    public int scoreFor(String playerName) throws Exception {
        if (!players.containsKey(playerName))
            throw new Exception("Le joueur " + playerName + " ne joue pas dans cette partie.");

        return players.get(playerName).score();
    }

    private String getMessage(String name) {
        int frame_number = players.get(name).getCurrentFrame().getFrameNumber();
        int ball_number = players.get(name).getCurrentFrame().getBallsThrown();

        if (players.get(name).getCurrentFrame().isFinished()) {
            frame_number += 1;
            ball_number = 0;
        }
        return "Prochain tir : joueur " + name + ", tour n°" + frame_number + ", boule n°" + ball_number;
    }

    private String getNextPlayerName() {
        if (players_iterator == null || !players_iterator.hasNext())
            players_iterator = players.keySet().iterator();

        if (current_player_name == null || players.get(current_player_name).getCurrentFrame().isFinished())
            current_player_name = players_iterator.next();

        return current_player_name;
    }

    Set<String> getPlayersNames() {
        return players.keySet();
    }

}