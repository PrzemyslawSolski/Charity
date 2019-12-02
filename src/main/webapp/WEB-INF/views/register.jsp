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
    <title>Document</title>
  <link rel="stylesheet" href="<c:url value="resources/css/style.css"/>"/>
</head>
<body>
<header>
    <nav class="container container--70">
        <ul class="nav--actions">
            <li><a href="/login">Zaloguj</a></li>
            <li class="highlighted"><a href="#">Załóż konto</a></li>
        </ul>

        <%@ include file="header.jsp" %>
    </nav>
</header>

<section id="register" class="login-page">
    <h2>Załóż konto</h2>
    <form:form method="post" id="edit-form" modelAttribute="user">
        <div class="form-group">
            <form:input type="text" path="name" placeholder="Imię"/>
            <form:errors path="name" element="div" cssClass="error"></form:errors>
        </div>
        <div class="form-group">
            <form:input type="text" path="surname" placeholder="Nazwisko"/>
            <form:errors path="surname" element="div" cssClass="error"></form:errors>
        </div>
        <div class="form-group">
            <form:input type="email" path="email" placeholder="Email"/>
            <form:errors path="email" element="div" cssClass="error"></form:errors>
        </div>
        <div class="form-group">
            <form:input type="password" path="password" placeholder="Hasło"/>
            <form:errors path="password" element="div" cssClass="error"></form:errors>
        </div>
        <div class="form-group">
            <input type="password" name="password2" placeholder="Powtórz hasło"/>
        </div>

        <div class="form-group form-group--buttons">
            <a href="/login" class="btn btn--without-border">Zaloguj się</a>
            <button class="btn" type="submit">Załóż konto</button>
        </div>
    </form:form>>
</section>

<%@ include file="footer.jsp" %>
</body>
</html>
