<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<t:template title="Error">
 
    <jsp:attribute name="body">
        <h2>Erreur</h2>
        
        <div class="o-wrapper">
            ${error}
            <br>
            <c:choose>
                <c:when test="${true == isAdmin}">
                    <a href="${ctx}/admin" class="link">Back to admin panel</a>
                </c:when>
                <c:when test="${null != customer}">
                    <a href="${ctx}/customer">Back to your profile</a>
                </c:when>
                <c:otherwise>
                    <a href="${ctx}/">Back to login page</a>
                </c:otherwise>
            </c:choose>
        </div>
    </jsp:attribute>

    <jsp:attribute name="script">
    </jsp:attribute>
 
</t:template>
