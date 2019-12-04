<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Zacznij pomagać</title>
    <link rel="stylesheet" href="<c:url value="resources/css/style.css"/>"/>
</head>
<body>
<header>
    <nav class="container container--70">
        <%--      <ul class="nav--actions">--%>
        <%--        <li><a href="/login">Zaloguj</a></li>--%>
        <%--        <li class="highlighted"><a href="/register#register">Załóż konto</a></li>--%>
        <%--      </ul>--%>
        <%@ include file="user_menu.jsp" %>

        <%@ include file="header.jsp" %>
    </nav>
</header>

<section class="login-page">
    <h2>Wpisz adres konta, do którego chcesz odzyskać dostęp:</h2>
    <form action="/remind" method="post">
        <div class="form-group">
            <div class="stats--item">
                <p>Na podany email wyślemy link do resetu hasła</p>
            </div>
            <input type="email" name="email" placeholder="Email"/>
            <%--          <form:errors path="email" element="div" cssClass="error"/>--%>
        </div>
        <%--        <div class="form-group">--%>
        <%--          <form:input type="password" path="password" placeholder="Hasło" />--%>
        <%--          <form:errors path="password" element="div" cssClass="error"/>--%>
        <%--          <a href="#" class="btn btn--small btn--without-border reset-password">Nie pamiętam hasła</a>--%>
        <%--        </div>--%>


        <div class="form-group form-group--buttons">
            <%--          <a href="/register#register" class="btn btn--without-border">Załóż konto</a>--%>
            <button class="btn" type="submit">Wyślij link</button>
        </div>
    </form>
</section>

<%@ include file="footer.jsp" %>
</body>
</html>
