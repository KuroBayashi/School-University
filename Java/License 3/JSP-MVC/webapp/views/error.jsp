<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error</title>
    </head>
    <body>
        <h1>Error !</h1>
        Message : ${error}
        
        <a href='${pageContext.request.contextPath}'>Retour au menu</a><br>
    </body>
</html>