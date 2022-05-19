<%-- 
    Document   : discountCode
    Created on : 7 nov. 2018, 14:44:37
    Author     : pedago
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        
        <link href="css/design.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <header class="s-header">
            <h1>Discount Code Panel</h1>
        </header>

        <div class="o-container">
            <h2>Ajouter un nouveau code</h2>
            <form method="GET" class="form">
                <label for="code">Code (1 lettre en MAJ) :</label>
                <input type="text" name="code" id="code" maxlength="1" pattern="[A-Z]{1}+">
                
                <label for="taux">Taux (entre 00.00 et 99.99):</label>
                <input type="number" name="taux" id="taux" step="0.01" min="0.0" max="99.99">
                
                <input type="hidden" name="_action" value="add">

                <button>Ajouter</button>
            </form>

            <c:if test="${not empty flashMessages}">
                <ul>
                    <c:forEach var="flashMessage" items="${flashMessages}">
                        <li>${flashMessage}</li>
                    </c:forEach>
                </ul>
            </c:if>

            <h2>Liste des codes</h2>
            <table>
                <thead>
                    <tr>
                        <th>Code</th>
                        <th>Taux</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="discountCode" items="${discountCodes}">
                        <tr>
                            <td>${discountCode.code}</td>
                            <td>
                                <fmt:formatNumber value="${discountCode.taux}"
                                                  minIntegerDigits="2" maxIntegerDigits="2" 
                                                  minFractionDigits="2" maxFractionDigits="2"/> %
                            </td>
                            <td>
                                <form method="GET">
                                    <input type="hidden" name="code" value="${discountCode.code}">
                                    <input type="hidden" name="_action" value="delete">

                                    <button>Supprimer</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>