<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<t:template title="Product:Home">
 
    <jsp:attribute name="body">
        <h2>Home Product</h2>
        
        <c:if test="${true == isAdmin}">
            <form method="POST" class="c-form">
                <input type="hidden" name="_action" value="create">

                <button type="submit" class="c-btn">Create new product</button>
            </form>
        </c:if>
        
        <div class="o-wrapper o-grid o-grid-gap--s">
            <c:forEach var="product" items="${products}">
                <div class="o-grid__cols--4 c-card">
                    <header class="o-wrapper-inside c-card__header">
                        <h3>${product.description}</h3>
                    </header>
                    <div class="c-card__content">
                        <ul>
                            <li>Purchase cost : ${product.purchaseCost}</li>
                            <li>Markup : ${product.markup}</li>
                            <li>Quantity on hand : ${product.quantity}</li>
                            <li>Manufacturer : ${product.manufacturer.name}</li>
                            <li>Product code : ${product.code.description}</li>
                        </ul>
                    </div>
                    <footer class="o-wrapper-inside c-card__footer">
                        <c:choose>
                            <c:when test="${null != customer}">
                                <form method="POST" action="${ctx}/purchaseOrder">
                                    <input type="hidden" name="_action" value="create">
                                    <input type="hidden" name="product_id" value="${product.id}">

                                    <button type="submit" class="c-btn">Purchase</button>
                                </form>
                            </c:when>
                            <c:when test="${true == isAdmin}">
                                <form method="POST" class="u-display--inline c-form">
                                    <input type="hidden" name="_action" value="edit">
                                    <input type="hidden" name="product_id" value="${product.id}">

                                    <button type="submit" class="c-btn">Edit</button>
                                </form>
                            </c:when>
                        </c:choose>
                    </footer>
                </div>
            </c:forEach>
        </div>
    </jsp:attribute>

    <jsp:attribute name="script">
    </jsp:attribute>
 
</t:template>
