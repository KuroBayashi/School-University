<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
 
<t:template title="Product:Edit">
 
    <jsp:attribute name="body">
        <h2>Edit Products</h2>

        <table class="o-wrapper c-table c-table--expend">
            <thead>
                <tr>
                    <th class="c-table__th">Description</th>
                    <th class="c-table__th">Code</th>
                    <th class="c-table__th">Manufacturer</th>
                    <th class="c-table__th">Quantity</th>
                    <th class="c-table__th">Purchase cost</th>
                    <th class="c-table__th">Markup</th>
                    <th class="c-table__th">Options</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="product" items="${products}">
                    <tr>
                        <td class="c-table__td">
                            ${product.description}
                        </td>
                        <td class="c-table__td">
                            <select name="code" form="form_edit_${product.id}">
                                <c:forEach var="productCode" items="${productCodes}">
                                    <option value="${productCode.code}" <c:if test="${productCode.code == product.code.code}">selected="selected"</c:if> >
                                        ${productCode.description}
                                    </option>
                                </c:forEach>
                            </select>
                        </td>
                        <td class="c-table__td">
                            <select name="manufacturer_id" form="form_edit_${product.id}">
                                <c:forEach var="manufacturer" items="${manufacturers}">
                                    <option value="${manufacturer.id}" <c:if test="${manufacturer.id == product.manufacturer.id}">selected="selected"</c:if> >
                                        ${manufacturer.name}
                                    </option>
                                </c:forEach>
                            </select>
                        </td>
                        <td class="c-table__td">
                            <input type="number" name="quantity" value="${product.quantity}" min="0" form="form_edit_${product.id}">
                        </td>
                        <td class="c-table__td">
                            <input type="number" name="purchase_cost" value="${product.purchaseCost}" step="0.01" form="form_edit_${product.id}">
                        </td>
                        <td class="c-table__td">
                            <input type="number" name="markup" value="${product.markup}" step="0.01" form="form_edit_${product.id}">
                        </td>
                        <td class="c-table__td">
                            <form method="POST" id="form_edit_${product.id}" class="u-display--inline c-form">
                                <input type="hidden" name="_action" value="edit">
                                <input type="hidden" name="product_id" value="${product.id}">

                                <button type="submit" name="submit" class="c-btn">Edit</button>
                            </form>
                            <form method="POST" class="u-display--inline c-form">
                                <input type="hidden" name="_action" value="delete">
                                <input type="hidden" name="product_id" value="${product.id}">

                                <button type="submit" class="c-btn c-btn--danger">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </jsp:attribute>

    <jsp:attribute name="script">
    </jsp:attribute>
 
</t:template>
