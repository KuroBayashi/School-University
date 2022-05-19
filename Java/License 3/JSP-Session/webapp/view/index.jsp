<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Devine un nombre</title>
        
        <link href="css/design.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <header>
            <h1>Devine un nombre</h1>
        </header>
        
        <div>
            Il y a actuellement ${app_count_players} joueur(s).<br>
            <br>
            
            <c:if test="${not empty app_best_player}">
                Score à battre : ${app_best_score} essai par ${app_best_player}<br>
            </c:if>
        </div>
        
        <c:choose>
            <c:when test="${empty player_name}">
                <form method="POST">
                    <label for="pseudo">Votre pseudo :</label>
                    <input type="text" name="pseudo" id="pseudo">
                    
                    <input type="hidden" name="_action" value="connexion">
                    
                    <button type="submit">Connexion</button>
                </form>
            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${not player_win}">
                        Bienvenue ${player_name} !<br>
                        <br>
                        <c:if test="${player_try_count > 0}">
                            Nombre d'essai : ${player_try_count}<br>
                            Dernier essai : ${player_try_last} - <strong>${player_try_message}</strong><br>
                            <br>
                        </c:if>

                        Je pense à un nombre compris entre 0 et 100:
                        <form method="POST">
                            <label for="proposition">Votre proposition : </label>
                            <input type="number" name="proposition" id="proposition" min="0" max="100">

                            <input type="hidden" name="_action" value="jouer">

                            <button type="submit">Deviner</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        Bravo, tu as gagné !
                        <form method="POST">
                            <input type="hidden" name="_action" value="rejouer">

                            <button type="submit">Rejouer</button>
                        </form>
                    </c:otherwise>
                </c:choose>
                <br>
                <form method="POST">
                    <input type="hidden" name="_action" value="deconnexion">

                    <button type="submit">Déconnexion</button>
                </form>
            </c:otherwise>
      </c:choose>
    </body>
</html>