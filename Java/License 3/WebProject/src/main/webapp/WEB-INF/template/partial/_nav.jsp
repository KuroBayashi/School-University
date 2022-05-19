<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<nav class="s-nav-main">
    <ul class="c-list c-list--row c-list--clean">
        <c:choose>
            <c:when test="${true == isAdmin}">
                <li>
                    <a href="${ctx}/admin" class="link link--nocolor s-nav-main__link">
                        Admin panel
                    </a>
                </li>
            </c:when>
            <c:when test="${null != customer && -1 != customer.id}">
                <li>
                    <a href="${ctx}/customer" class="link link--nocolor s-nav-main__link">
                        Profile
                    </a>
                </li>
                <li>
                    <a href="${ctx}/purchaseOrder" class="link link--nocolor s-nav-main__link">
                        Purchase orders
                    </a>
                </li>
            </c:when>
        </c:choose>
        
        <li>
            <a href="${ctx}/product" class="link link--nocolor s-nav-main__link">
                Products
            </a>
        </li>
    </ul>
    
    <c:choose>
        <c:when test="${true == isAdmin or (null != customer && -1 != customer.id)}">
            <%@ include file="/WEB-INF/template/auth/_logout.jsp" %>
        </c:when>
        <c:otherwise>
            <a href="${ctx}/" class="link link--no-decoration c-btn">Login</a>
        </c:otherwise>
    </c:choose>
</nav>
