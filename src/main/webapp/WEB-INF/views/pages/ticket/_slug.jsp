<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Mathias
  Date: 25-11-2020
  Time: 11:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <div class="container">
        <section>
            <h1>Kundesamtale</h1>
            <p>For ordre: <a href="">#2a5j2-fk99-2221-vfk2a</a></p>
            <p class="mb-0">Ordre status <span class="badge badge-warning">Tilbud afsendt</span></p>
        </section>
        <section>
            <article class="event">
                <div class="event-message accept"><a href="">Ordren</a> blev oprettet af klienten. - 6. Dec, 2020</div>
            </article>
            <article class="message border rounded">
                <section class="d-flex justify-content-between align-items-center">
                    <h2>Martin Riddel</h2>
                    <span class="small">19:06, 9. Dec, 2020</span>
                </section>
                <section>
                    <p>
                        Carporten skal gerne være i mørke farver.
                        <br>
                        <br>
                        Adgium, tus, et epos. Cur vita messis? Ubi est dexter nutrix? Capios mori! Seculas accelerare in lutetia! Impositios sunt exemplars de placidus buxum.
                    </p>
                </section>
            </article>
            <article class="event">
                <div class="event-message accept"><a href="">Ordren</a> blev godkendt af salgsassistenten. - 9. Dec, 2020</div>
            </article>
            <article class="message border rounded staff">
                <section class="d-flex justify-content-between align-items-center">
                    <h2>Emil D.T Jørgensen <span class="badge badge-secondary">Salgsassistent</span></h2>
                    <span class="small">19:06, 9. Dec, 2020</span>
                </section>
                <section>
                    <p>
                        Det bliver ikke noget problem.
                        <br>
                        <br>
                        Med venlig hilsen,
                        <br>
                        <strong>Kundeservice - Fog</strong>
                    </p>
                </section>
            </article>
            <article class="event">
                <div class="event-message accept"><a href="">Tilbud</a> tilsendt af salgsassistenten. - 9 Dec, 2020</div>
            </article>
            <article class="message border rounded alert-warning border-warning">
                <section>
                    <div class="row d-flex align-items-center">
                        <div class="pl-3 d-flex align-items-center">
                            <span class="material-icons-outlined">
                                error
                            </span>
                        </div>
                        <div class="col">
                            <h2>Du har et ubesvaret tilbud.</h2>
                            <p>
                                Besvar tilbudet fra salgsassistenten for at færdiggøre ordren.
                            </p>
                        </div>
                    </div>
                </section>
            </article>
            <article class="reply border rounded">
                <div id="editor"></div>
                <section class="d-flex justify-content-end align-items-center">
                    <textarea hidden></textarea>
                    <button type="submit" class="btn btn-primary">Svar</button>
                </section>
            </article>
        </section>
    </div>
</div>