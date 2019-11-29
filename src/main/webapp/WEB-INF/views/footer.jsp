<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<footer>
        <div id="contact" class="contact">
            <h2>Skontaktuj się z nami</h2>
            <h3>Formularz kontaktowy</h3>
            <form class="form--contact" action="/email" method="post">
                <div class="form-group form-group--50">
                    <input type="text" name="name" placeholder="Imię"/>
                </div>
                <div class="form-group form-group--50">
                    <input type="text" name="surname" placeholder="Nazwisko"/>
                </div>

                <div class="form-group">
            <textarea
                    name="message"
                    placeholder="Wiadomość"
                    rows="1"
            ></textarea>
                </div>

                <button class="btn" type="submit">Wyślij</button>
            </form>
        </div>
        <div class="bottom-line">
            <span class="bottom-line--copy">Copyright &copy; 2018</span>
            <div class="bottom-line--icons">
                <a href="#" class="btn btn--small"><img src="<c:url value="resources/images/icon-facebook.svg"/>"
                                                        alt="Facebook"/></a>
                <a href="#" class="btn btn--small"><img src="<c:url value="resources/images/icon-instagram.svg"/>"
                                                        alt="Instagram"/></a>
            </div>
        </div>
</footer>