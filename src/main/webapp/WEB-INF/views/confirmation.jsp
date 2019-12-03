<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Document</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>"/>
<%--    <link rel="stylesheet" href="<c:url value="../resources/css/style.css"/>"/>--%>
</head>
<body>
<header class="header--form-page">
    <nav class="container container--70">
        <%--        <ul class="nav--actions"></ul>--%>
        <%@ include file="user_menu.jsp" %>

        <%@ include file="header.jsp" %>
    </nav>

    <div class="slogan container container--90">
        <h2>
            <c:forEach items="${messages}" var="message">
                <div>${message}</div>
            </c:forEach>
        </h2>
    </div>
</header>

<%@ include file="footer.jsp" %>

<script src="js/app.js"></script>
</body>
</html>
