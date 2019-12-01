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

<%--        <%@ include file="user_menu.jsp" %>--%>

<%--        <%@ include file="header.jsp" %>--%>
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

        <%--        <form action="/institution" method="post">--%>
        <form:form method="post" modelAttribute="donation">
            <!-- STEP 1: class .active is switching steps -->
<%--            <div data-step="1">--%>
<%--                <h3>Zaznacz co chcesz oddać:</h3>--%>
                    <%--                <form:errors path="*" element="div" cssClass="error"></form:errors>--%>

                    <%--                <c:forEach items="${categoryContainer.categories}" var="category" varStatus="tagStatus">--%>
                    <%--                    <div class="form-group form-group--checkbox">--%>
                    <%--                            &lt;%&ndash;                        <div>&ndash;%&gt;--%>
                    <%--                        <label>--%>
                    <%--                            <form:hidden path="categories[${tagStatus.index}].id"/>--%>
                    <%--                            <form:hidden path="categories[${tagStatus.index}].name"/>--%>
                    <%--                            <form:checkbox name="categories" path="categories[${tagStatus.index}].chosen"--%>
                    <%--                                           value="false"/>--%>
                    <%--                            <span class="checkbox"></span>--%>
                    <%--                            <span class="description">${category.name}</span>--%>
                    <%--                        </label>--%>
                    <%--                    </div>--%>
                    <%--                </c:forEach>--%>


                    <%--                <div class="form-group form-group--checkbox">--%>
                    <%--                    <label>--%>
                    <%--                        <input--%>
                    <%--                                type="checkbox"--%>
                    <%--                                name="categories"--%>
                    <%--                                value="clothes-to-use"--%>
                    <%--                        />--%>
                    <%--                        <span class="checkbox"></span>--%>
                    <%--                        <span class="description"--%>
                    <%--                        >ubrania, które nadają się do ponownego użycia</span--%>
                    <%--                        >--%>
                    <%--                    </label>--%>
                    <%--                </div>--%>

                    <%--                <div class="form-group form-group--checkbox">--%>
                    <%--                    <label>--%>
                    <%--                        <input--%>
                    <%--                                type="checkbox"--%>
                    <%--                                name="categories"--%>
                    <%--                                value="clothes-useless"--%>
                    <%--                        />--%>
                    <%--                        <span class="checkbox"></span>--%>
                    <%--                        <span class="description">ubrania, do wyrzucenia</span>--%>
                    <%--                    </label>--%>
                    <%--                </div>--%>

                    <%--                <div class="form-group form-group--checkbox">--%>
                    <%--                    <label>--%>
                    <%--                        <input type="checkbox" name="categories" value="toys"/>--%>
                    <%--                        <span class="checkbox"></span>--%>
                    <%--                        <span class="description">zabawki</span>--%>
                    <%--                    </label>--%>
                    <%--                </div>--%>

                    <%--                <div class="form-group form-group--checkbox">--%>
                    <%--                    <label>--%>
                    <%--                        <input type="checkbox" name="categories" value="books"/>--%>
                    <%--                        <span class="checkbox"></span>--%>
                    <%--                        <span class="description">książki</span>--%>
                    <%--                    </label>--%>
                    <%--                </div>--%>

                    <%--                <div class="form-group form-group--checkbox">--%>
                    <%--                    <label>--%>
                    <%--                        <input type="checkbox" name="categories" value="other"/>--%>
                    <%--                        <span class="checkbox"></span>--%>
                    <%--                        <span class="description">inne</span>--%>
                    <%--                    </label>--%>
                    <%--                </div>--%>

<%--                <div class="form-group form-group--buttons">--%>
<%--                        &lt;%&ndash;                                            <button type="button" class="btn next-step">Dalej</button>&ndash;%&gt;--%>
<%--                    <input type="submit" value="Dalej" class="btn">--%>
<%--                </div>--%>
<%--            </div>--%>

            <!-- STEP 2 -->
            <%--            <div data-step="2">--%>
            <%--                <h3>Podaj liczbę 60l worków, w które spakowałeś/aś rzeczy:</h3>--%>

            <%--                <div class="form-group form-group--inline">--%>
            <%--                    <label>--%>
            <%--                        Liczba 60l worków:--%>
            <%--                        <input type="number" name="quantity" step="1" min="1"/>--%>
            <%--&lt;%&ndash;                        <form:input path="quantity" type="number" name="quantity" step="1" min="1"/>&ndash;%&gt;--%>
            <%--                    </label>--%>
            <%--                </div>--%>

            <%--                <div class="form-group form-group--buttons">--%>
            <%--                    <button type="button" class="btn prev-step">Wstecz</button>--%>
            <%--                    <button type="submit" class="btn">Dalej</button>--%>
            <%--&lt;%&ndash;                    <input type="submit" class="btn" value="Dalej"></input>&ndash;%&gt;--%>
            <%--                </div>--%>
            <%--            </div>--%>


            <!-- STEP 4 -->
<%--            <div data-step="3">--%>
<%--                <h3>Wybierz organizacje, której chcesz pomóc:</h3>--%>
                    <%--                <c:forEach items="${institutions}" var="institution">--%>
                    <%--                    <div class="form-group form-group--checkbox">--%>
                    <%--                        <label>--%>
                    <%--                            <input type="radio" name="institution" value="${institution.id}" required>--%>
                    <%--                            <span class="checkbox radio"></span>--%>
                    <%--                            <span class="description">--%>
                    <%--                  <div class="title">${institution.name}</div>--%>
                    <%--                  <div class="subtitle">--%>
                    <%--                    Cel i misja: ${institution.description}--%>
                    <%--                  </div>--%>
                    <%--                </span>--%>
                    <%--                        </label>--%>
                    <%--                    </div>--%>
                    <%--                </c:forEach>--%>

                    <%--                <div class="form-group form-group--checkbox">--%>
                    <%--                    <label>--%>
                    <%--                        <input type="radio" name="organization" value="old"/>--%>
                    <%--                        <span class="checkbox radio"></span>--%>
                    <%--                        <span class="description">--%>
                    <%--                  <div class="title">Fundacja “Bez domu”</div>--%>
                    <%--                  <div class="subtitle">--%>
                    <%--                    Cel i misja: Pomoc dla osób nie posiadających miejsca--%>
                    <%--                    zamieszkania--%>
                    <%--                  </div>--%>
                    <%--                </span>--%>
                    <%--                    </label>--%>
                    <%--                </div>--%>

                    <%--                <div class="form-group form-group--checkbox">--%>
                    <%--                    <label>--%>
                    <%--                        <input type="radio" name="organization" value="old"/>--%>
                    <%--                        <span class="checkbox radio"></span>--%>
                    <%--                        <span class="description">--%>
                    <%--                  <div class="title">Fundacja “Dla dzieci"</div>--%>
                    <%--                  <div class="subtitle">--%>
                    <%--                    Cel i misja: Pomoc osobom znajdującym się w trudnej sytuacji--%>
                    <%--                    życiowej.--%>
                    <%--                  </div>--%>
                    <%--                </span>--%>
                    <%--                    </label>--%>
                    <%--                </div>--%>

<%--                <div class="form-group form-group--buttons">--%>
<%--                    <button type="button" class="btn prev-step">Wstecz</button>--%>
<%--                    <button type="submit" class="btn next-step">Dalej</button>--%>
<%--                </div>--%>
<%--            </div>--%>

            <!-- STEP 5 -->
            <%--            <div data-step="4">--%>
            <%--                <h3>Podaj adres oraz termin odbioru rzeczy przez kuriera:</h3>--%>

            <%--                <div class="form-section form-section--columns">--%>
            <%--                    <div class="form-section--column">--%>
            <%--                        <h4>Adres odbioru</h4>--%>
            <%--                        <div class="form-group form-group--inline">--%>
            <%--                            <label> Ulica <form:input path="street"  type="text" name="address"/> </label>--%>
            <%--                        </div>--%>

            <%--                        <div class="form-group form-group--inline">--%>
            <%--                            <label> Miasto <form:input path="city" type="text" name="city"/> </label>--%>
            <%--                        </div>--%>

            <%--                        <div class="form-group form-group--inline">--%>
            <%--                            <label>--%>
            <%--                                Kod pocztowy <form:input path="zipCode" type="text" name="postcode"/>--%>
            <%--                            </label>--%>
            <%--                        </div>--%>

            <%--                        <div class="form-group form-group--inline">--%>
            <%--                            <label>--%>
            <%--                                Numer telefonu <form:input path="phoneNumber" type="phone" name="phone"/>--%>
            <%--                            </label>--%>
            <%--                        </div>--%>
            <%--                    </div>--%>

            <%--                    <div class="form-section--column">--%>
            <%--                        <h4>Termin odbioru</h4>--%>
            <%--                        <div class="form-group form-group--inline">--%>
            <%--                            <label> Data <form:input path="pickUpDate" type="date" name="data"/> </label>--%>
            <%--                        </div>--%>

            <%--                        <div class="form-group form-group--inline">--%>
            <%--                            <label> Godzina <form:input path="pickUpTime" type="time" name="time"/> </label>--%>
            <%--                        </div>--%>

            <%--                        <div class="form-group form-group--inline">--%>
            <%--                            <label>--%>
            <%--                                Uwagi dla kuriera--%>
            <%--                                <form:textarea path="pickUpComment" name="more_info" rows="5"></form:textarea>--%>
            <%--                            </label>--%>
            <%--                        </div>--%>
            <%--                    </div>--%>
            <%--                </div>--%>
            <%--                <div class="form-group form-group--buttons">--%>
            <%--                    <button type="button" class="btn prev-step">Wstecz</button>--%>
            <%--                    <button type="submit" class="btn next-step">Dalej</button>--%>
            <%--                </div>--%>
            <%--            </div>--%>

            <!-- STEP 6 -->
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
                                                    <c:forEach items="${donation.categories}" var="category" varStatus="tagStatus">
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
                    <button type="button" class="btn prev-step" onclick="location.href='/address'">Wstecz</button>
<%--                    </a>--%>
                    <button type="submit" class="btn">Potwierdzam</button>
                </div>
            </div>
        </form:form>
        <%--        </form>--%>
    </div>
</section>

<%--<%@ include file="footer.jsp" %>--%>

<%--<script src="<c:url value="resources/js/app.js"/>"></script>--%>
</body>
</html>
