<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<ul class="nav--actions">
    <c:if test="${userId!=null}">
        <li class="logged-user">
            Witaj ${firstName}
            <ul class="dropdown">
                    <%--                <li><a href="#">Profil</a></li>--%>
                    <%--                <li><a href="#">Ustawienia</a></li>--%>
                    <%--                <li><a href="#">Moje zbiórki</a></li>--%>
                <li><a href="/logout">Wyloguj</a></li>
            </ul>
        </li>
    </c:if>
</ul>
