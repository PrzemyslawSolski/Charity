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
<header class="header--form-page">
    <nav class="container container--70">
        <ul class="nav--actions"></ul>

        <%--        <%@ include file="user_menu.jsp" %>--%>

                <%@ include file="header.jsp" %>
    </nav>

    <div class="slogan container container--90">
        <div class="slogan--item">
            <h1>
                Oddaj rzeczy, których już nie chcesz<br/>
                <span class="uppercase">potrzebującym</span>
            </h1>

            <div class="slogan--steps">
                <div class="slogan--steps-title">Wystarczą 4 proste kroki:</div>
                <ul class="slogan--steps-boxes">
                    <li>
                        <div><em>1</em><span>Wybierz rzeczy</span></div>
                    </li>
                    <li>
                        <div><em>2</em><span>Spakuj je w worki</span></div>
                    </li>
                    <li>
                        <div><em>3</em><span>Wybierz fundację</span></div>
                    </li>
                    <li>
                        <div><em>4</em><span>Zamów kuriera</span></div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</header>

<section id="data" class="form--steps">
    <div class="form--steps-instructions">
        <div class="form--steps-container">
            <h3>Ważne!</h3>
            <p data-step="1">
                Uzupełnij szczegóły dotyczące Twoich rzeczy. Dzięki temu będziemy
                wiedzieć komu najlepiej je przekazać.
            </p>
            <p data-step="2">
                Uzupełnij szczegóły dotyczące Twoich rzeczy. Dzięki temu będziemy
                wiedzieć komu najlepiej je przekazać.
            </p>
            <p data-step="3">
                Wybierz jedną organizację, do
                której trafi Twoja przesyłka.
            </p>
            <p data-step="4">Podaj adres oraz termin odbioru rzeczy.</p>
            <p data-step="5" class="active">Sprawdź i potwierdź dane.</p>
        </div>
    </div>

    <div class="form--steps-container">
        <div class="form--steps-counter"><span></span></div>
        <%--TODO wyczyścić wszystkei jsp z komentarzy--%>

        <form:form method="post" modelAttribute="donation">
            <!-- STEP 1: class .active is switching steps -->

            <!-- STEP 5 -->
            <div data-step="5" class="active">
                <h3>Podsumowanie Twojej darowizny</h3>
                <form:hidden path="quantity"/>
                <form:hidden path="institution.id"/>
                <form:hidden path="institution.name"/>
                <form:hidden path="institution.description"/>
                <form:hidden path="street"/>
                <form:hidden path="city"/>
                <form:hidden path="zipCode"/>
                <form:hidden path="phoneNumber"/>
                <form:hidden path="pickUpDate"/>
                <form:hidden path="pickUpTime"/>
                <form:hidden path="pickUpComment"/>
                <c:forEach items="${donation.categories}" var="category" varStatus="tagStatus">
                    <form:hidden path="categories[${tagStatus.index}].id"/>
                    <form:hidden path="categories[${tagStatus.index}].name"/>
                </c:forEach>

                <div class="summary">
                    <div class="form-section">
                        <h4>Oddajesz:</h4>
                        <ul>
                            <li>
                                <span class="icon icon-bag"></span>
                                <span class="summary--text"
                                ><c:out value="${donation.quantity}"/>
                                    <c:choose>
                                        <c:when test="${donation.quantity==1}">
                                            worek
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${donation.quantity<5}">
                                                    worki
                                                </c:when>
                                                <c:otherwise>
                                                    worków
                                                </c:otherwise>
                                            </c:choose>

                                        </c:otherwise>
                                    </c:choose>
                                     kategorii: <br/>
                                    <div class="form-group form-group--checkbox">
                                        <label>
                                            <span class="description">
                                                <div class="subtitle">&nbsp;
                                                </div>
                                                <div class="title">
                                                    <c:forEach items="${donation.categories}" var="category"
                                                               varStatus="tagStatus">
                                                        <c:if test="${donation.categories.size()>1}">
                                                            <c:out value="-"></c:out>
                                                        </c:if>
                                                        <c:out value="${category.name}"></c:out> <br/>
                                                    </c:forEach>
                                                </div>
                                            </span>
                                        </label>
                                    </div>
                                </span>
                            </li>

                            <li>
                                <span class="icon icon-hand"></span>
                                <span class="summary--text">
                                    Dla:
                                    <div class="form-group form-group--checkbox">
                                    <label>
                                        <span class="description">
                                            <div class="title">${donation.institution.name}</div>
                                            <div class="subtitle">${donation.institution.description}</div>
                                        </span>
                                    </label>
                                    </div>
                                </span>
                            </li>
                        </ul>
                    </div>

                    <div class="form-section form-section--columns">
                        <div class="form-section--column">
                            <h4>Adres odbioru:</h4>
                            <ul>
                                <li>${donation.street}</li>
                                <li>${donation.city}</li>
                                <li>${donation.zipCode}</li>
                                <li>${donation.phoneNumber}</li>
                            </ul>
                        </div>

                        <div class="form-section--column">
                            <h4>Termin odbioru:</h4>
                            <ul>
                                <li>${donation.pickUpDate}</li>
                                <li>${donation.pickUpTime}</li>
                                <li>${donation.pickUpComment}</li>
                            </ul>
                        </div>
                    </div>
                </div>

                <div class="form-group form-group--buttons">
                        <%--                    <a href="${header.referer}">--%>
                    <button type="button" class="btn prev-step" onclick="location.href='/address#data'">Wstecz</button>
                        <%--                    </a>--%>
                    <button type="submit" class="btn">Potwierdzam</button>
                </div>
            </div>
        </form:form>
    </div>
</section>

<%--<%@ include file="footer.jsp" %>--%>

<%--<script src="<c:url value="resources/js/app.js"/>"></script>--%>
</body>
</html>
