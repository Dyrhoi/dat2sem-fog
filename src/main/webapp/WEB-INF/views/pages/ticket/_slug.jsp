<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Mathias
  Date: 25-11-2020
  Time: 11:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="api.Util" %>
<div>
    <div class="container">
        <section>
            <h1>Kundesamtale</h1>
            <p>
                For ordre:
                <a href="<c:url value="/sales/orders/${requestScope.ticket.order.uuid}" />">
                    #<c:out value="${requestScope.ticket.order.uuid}" />
                </a>
            </p>
            <p class="mb-0">Ordre status <span class="badge badge-warning">Tilbud afsendt</span></p>
        </section>
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
        <section>
            <c:forEach items="${requestScope.ticket.events}" var="eventOrMessage">
                <c:choose>
                    <c:when test="${eventOrMessage.getClass().simpleName == 'TicketMessage'}">
                        <article class="message border rounded">
                            <section class="d-flex justify-content-between align-items-center">
                                <h2><c:out value="${eventOrMessage.getAuthor().getFullName()}"/></h2>
                                <span class="small"><c:out value="${Util.formatDateTime(eventOrMessage.getDate())}"/></span>
                            </section>
                            <section>
                                <c:out escapeXml="false" value="${eventOrMessage.getContent()}" />
                            </section>
                        </article>
                    </c:when>
                    <c:when test="${eventOrMessage.getClass().simpleName == 'TicketEvent'}">
                        <article class="event">
                            <div class="event-message">
                                <c:choose>
                                    <c:when test="${eventOrMessage.getScope() == 'ORDER_CREATED'}">
                                        Ordren blev oprettet af <c:out value="${eventOrMessage.getAuthor().getFullName()}" />
                                    </c:when>
                                    <c:when test="${eventOrMessage.getScope() == 'OFFER_SENT'}">
                                        Noget andet tekst.
                                    </c:when>
                            </c:choose>
                                - <c:out value="${Util.formatDate(eventOrMessage.getDate())}" />
                            </div>
                        </article>
                    </c:when>
                </c:choose>
            </c:forEach>
            <article class="reply border rounded">
                <div id="editor"></div>
                <form method="post">
                    <section class="d-flex justify-content-end align-items-center">
                        <input hidden name="content"/>
                        <button type="submit" class="btn btn-primary">Svar</button>
                    </section>
                </form>
            </article>
        </section>
    </div>
</div>