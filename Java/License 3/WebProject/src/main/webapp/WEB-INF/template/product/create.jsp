<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
 
<t:template title="Product:Edit">
 
    <jsp:attribute name="body">
        <h2>Create Product</h2>

        <div class="o-wrapper o-grid">
            <form method="POST" class="o-grid__cols--6 c-form">
            
                <div class="c-form__row">
                    <label for="description">Description <span class="c-form__label-info">(50 characters max)</span></label>
                    <input type="text" name="description" id="description" value="${product.description}" maxlength="50">
                </div>
                
                <div class="c-form__row">
                    <label for="product_code">Code</label>
                    <select name="product_code" id="product_code">
                        <c:forEach var="productCode" items="${productCodes}">
                            <option value="${productCode.code}" <c:if test="${productCode.code == product.code.code}">selected="selected"</c:if> >
                                ${productCode.description}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="c-form__row">
                    <label for="manufacturer_id">Manufacturer</label>
                    <select name="manufacturer_id" id="manufacturer_id">
                        <c:forEach var="manufacturer" items="${manufacturers}">
                            <option value="${manufacturer.id}" <c:if test="${manufacturer.id == product.manufacturer.id}">selected="selected"</c:if> >
                                ${manufacturer.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="c-form__row">
                    <label for="quantity">Quantity</label>
                    <input type="number" name="quantity" id="quantity" value="${product.quantity}" min="1">
                </div>
                
                <div class="c-form__row">
                    <label for="purchase_cost">Purchase cost</label>
                    <input type="number" name="purchase_cost" id="purchase_cost" value="${product.purchaseCost}" min="0" step="0.01">
                </div>
                
                <div class="c-form__row">
                    <label for="markup">Markup</label>
                    <input type="number" name="markup" id="markup" value="${product.markup}" min="1" step="0.01">
                </div>
            
                <input type="hidden" name="_action" value="create">

                <button type="submit" name="submit" class="c-btn">Save</button>
            </form>
        </div>
    </jsp:attribute>

    <jsp:attribute name="script">
    </jsp:attribute>
 
</t:template>
