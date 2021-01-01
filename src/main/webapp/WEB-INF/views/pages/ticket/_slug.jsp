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
<div class="container">
    <section>
        <h2>Kundesamtale</h2>
        <p>Har du nogen spørgsmål eller ændringer til ordren? Så skriv til vores support team!</p>
        <c:forEach items="${requestScope.ticket.events}" var="eventOrMessage">
            <c:choose>
                <c:when test="${eventOrMessage.getClass().simpleName == 'TicketMessage'}">
                    <article class="message border rounded <c:if test="${eventOrMessage.getAuthor().getClass().simpleName == 'SalesRepresentative'}">staff</c:if>">
                        <section class="d-flex justify-content-between align-items-center">
                            <h2>
                                <c:out value="${eventOrMessage.getAuthor().getFullName()}"/>
                                <c:if test="${eventOrMessage.getAuthor().getClass().simpleName == 'SalesRepresentative'}">
                                    <span class="badge badge-primary">Salgsassistent</span>
                                </c:if>
                            </h2>
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
                                <c:when test="${eventOrMessage.getScope() == 'ORDER_UPDATED'}">
                                    Ordren blev ændret af <c:out value="${eventOrMessage.getAuthor().getFullName()}" />
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
            <form method="post" action="<c:url value="/ticket/${requestScope.ticket.order.token}"/>">
                <section class="d-flex justify-content-end align-items-center">
                    <input hidden name="content"/>
                    <p class="mb-0 mr-3">
                        Du svarer som
                        <b>
                            <c:choose>
                                <c:when test="${sessionScope.user != null}">
                                    ${sessionScope.user.fullName}
                                </c:when>
                                <c:otherwise>
                                    ${requestScope.ticket.order.customer.fullName}
                                </c:otherwise>
                            </c:choose>
                        </b>
                    </p><button type="submit" class="btn btn-primary">Svar</button>
                </section>
            </form>
            <c:if test="${sessionScope.user.type == 'SALES_REPRESENTATIVE'}">
                <section class="footer justify-content-end d-flex align-content-center">
                    <p class="m-0 text-uppercase small font-weight-bold">Du er logget ind som Salgsassistent</p>
                </section>
            </c:if>
        </article>
    </section>
</div>