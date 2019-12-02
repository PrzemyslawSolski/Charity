<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:choose>
    <c:when test="${userId!=null}">
        <ul class="nav--actions">
            <li class="logged-user">
                Witaj ${firstName}
                <ul class="dropdown">
                        <%--                <li><a href="#">Profil</a></li>--%>
                        <%--                <li><a href="#">Ustawienia</a></li>--%>
                        <%--                <li><a href="#">Moje zbiórki</a></li>--%>
                    <li><a href="/logout">Wyloguj</a></li>
                </ul>
            </li>
        </ul>
    </c:when>
    <c:otherwise>
        <ul class="nav--actions">
            <li><a href="/login" class="btn btn--small btn--without-border">Zaloguj</a></li>
            <li><a href="/register#register" class="btn btn--small btn--highlighted">Załóż konto</a></li>
        </ul>

    </c:otherwise>

</c:choose>
