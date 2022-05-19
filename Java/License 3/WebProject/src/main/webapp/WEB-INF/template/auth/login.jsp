<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
 
<t:template title="Auth:Login">
 
    <jsp:attribute name="body">
        <h2>Login</h2>
        
        <div class="o-wrapper o-grid">
            <form method="POST" class="o-grid__cols--6 c-form">
                <div class="c-form__row">
                    <label for="username">Username <span class="c-form__label-info">(email)</span></label>
                    <input type="text" name="username" id="username">
                </div>
                <div class="c-form__row">
                    <label for="password">Password <span class="c-form__label-info">(id)</span></label>
                    <input type="password" name="password" id="password">
                </div>

                <input type="hidden" name="_action" value="login">

                <button type="submit" class="c-btn">Login</button>
            </form>
        </div>
    </jsp:attribute>

    <jsp:attribute name="script">
    </jsp:attribute>
 
</t:template>
