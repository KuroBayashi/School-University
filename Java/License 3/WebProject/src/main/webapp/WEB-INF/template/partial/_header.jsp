<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<c:choose>
    <c:when test="${true == isAdmin}">
        <c:set var="home_path" value="${ctx}/admin" />
    </c:when>
    <c:when test="${null != customer}">
        <c:set var="home_path" value="${ctx}/customer" />
    </c:when>
    <c:otherwise>
        <c:set var="home_path" value="${ctx}/" />
    </c:otherwise>
</c:choose>

<header class="s-header-main">
    <h1 class="s-header-main__title">
        <a href="${home_path}" class="link link--nocolor">
            Shopping Online
        </a>
    </h1>
            
    <%@ include file="../partial/_nav.jsp" %>
</header>