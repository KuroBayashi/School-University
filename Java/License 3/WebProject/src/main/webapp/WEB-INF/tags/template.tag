<%@tag description="layout" pageEncoding="UTF-8" %>
 
<%@attribute name="title" %>
<%@attribute name="home_path" %>
<%@attribute name="body"   fragment="true" %>
<%@attribute name="script" fragment="true" %>
 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${title}</title>
        
        <link rel="stylesheet" type="text/css" href="./css/style.css"> 
    </head>
    <body>
        <jsp:include page="/WEB-INF/template/partial/_header.jsp">
            <jsp:param name="home_path" value="${home_path}" />
        </jsp:include>
        
        <div class="s-container-main">
            <jsp:include page="/WEB-INF/template/partial/_flashes.jsp">
                <jsp:param name="flashBag" value="${flashBag}" />
            </jsp:include>
            
            <jsp:invoke fragment="body"/>
        </div>
        
        <%@ include file="/WEB-INF/template/partial/_footer.jsp" %>
        
        <jsp:invoke fragment="script"/>
    </body>
</html>