<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
 
<t:template title="Customer:Home">
 
    <jsp:attribute name="body">
        <h2>Edit Profile</h2>

        <div class="o-wrapper o-grid">
            <form method="POST" class="o-grid__cols--6 c-form">
                <fieldset class="c-form__fieldset">
                <legend class="c-form__fieldset-legend">Informations</legend>
                    <div class="c-form__row">
                        <label for="name">Name <span class="c-form__label-info">(30 characters max)</span></label>
                        <input type="text" name="name" id="name" value="${customer.name}" maxlength="30">
                    </div>
                    <div class="c-form__row">
                        <label for="credit">Credit</label>
                        <input type="number" name="credit" id="credit" value="${customer.credit}" min="0">
                    </div>
                </fieldset>

                <fieldset class="c-form__fieldset">
                    <legend class="c-form__fieldset-legend">Location</legend>
                    <div class="c-form__row">
                        <label for="address_line_1">Address line 1 <span class="c-form__label-info">(30 characters max)</span></label>
                        <input type="text" name="address_line_1" id="address_line_1" value="${customer.location.addressLine_1}" maxlength="30">
                    </div>
                    <div class="c-form__row">
                        <label for="address_line_2">Address line 2 <span class="c-form__label-info">(30 characters max)</span></label>
                        <input type="text" name="address_line_2" id="address_line_2" value="${customer.location.addressLine_2}" maxlength="30">
                    </div>
                    <div class="c-form__row">
                        <label for="zip">Zip <span class="c-form__label-info">(10 characters max)</span></label>
                        <select name="zip" id="zip">
                            <c:forEach var="microMarket" items="${microMarkets}">
                                <option value="${microMarket.zipCode}" <c:if test="${microMarket.zipCode == customer.location.microMarket.zipCode}">selected="selected"</c:if>>
                                    ${microMarket.zipCode}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="c-form__row">
                        <label for="city">City <span class="c-form__label-info">(25 characters max)</span></label>
                        <input type="text" name="city" id="city" value="${customer.location.city}" maxlength="25">
                    </div>
                    <div class="c-form__row">
                        <label for="state">State <span class="c-form__label-info">(2 characters max)</span></label>
                        <input type="text" name="state" id="state" value="${customer.location.state}" maxlength="2">
                    </div>
                </fieldset>

                <fieldset class="c-form__fieldset">
                    <legend class="c-form__fieldset-legend">Contact</legend>
                    <div class="c-form__row">
                        <label for="email">Email <span class="c-form__label-info">(40 characters max)</span></label>
                        <input type="email" name="email" id="email" value="${customer.contact.email}" maxlength="40">
                    </div>
                    <div class="c-form__row">
                        <label for="phone">Phone <span class="c-form__label-info">(12 characters max)</span></label>
                        <input type="text" name="phone" id="phone" value="${customer.contact.phone}" maxlength="12">
                    </div>
                    <div class="c-form__row">
                        <label for="fax">Fax <span class="c-form__label-info">(12 characters max)</span></label>
                        <input type="text" name="fax" id="fax" value="${customer.contact.fax}" maxlength="12">
                    </div>
                </fieldset>

                <input type="hidden" name="_action" value="edit">

                <button type="submit" class="c-btn">Edit</button>
            </form>
        </div>
    </jsp:attribute>

    <jsp:attribute name="script">
    </jsp:attribute>
 
</t:template>