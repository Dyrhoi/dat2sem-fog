<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Windowshoi
  Date: 04/01/2021
  Time: 18.14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="api.Util" %>
<jsp:include page="/WEB-INF/views/includes/my-order/my-order-navigation.jsp" />
<div>
    <section>
        <div class="container">
            <h1>Mine Tilbud</h1>
            <p class="mb-0">
                <span class="badge badge-dyrhoi border" style="background-color:${requestScope.order.status.getColorRGBA(0.2)}; border-color:${requestScope.order.status.colorRGBA} !important;">${requestScope.order.status.name}</span>
                <strong>#${requestScope.order.uuid}</strong>.
                Oprettet ${Util.formatDateTime(requestScope.order.date)}
            </p>
        </div>
    </section>
    <c:if test="${requestScope.order.hasAcceptedOffer() != null}">
        <section class="bg-light">
            <div class="container">
                <h3>Dit accepteret tilbud</h3>
                <p>
                    Tilbuddet blev sendt ${Util.formatDateTime(requestScope.order.hasAcceptedOffer().createdAt)}.
                    <br>
                    Den endelige pris er ${Util.formatPrice(requestScope.order.hasAcceptedOffer().price)}.
                </p>
                <p>
                    En faktura vil blive tilsendt af vores salgsassistent. Når fakturaen er betalt vil du få adgang til din materialeliste.
                </p>
                <p class="mb-0">
                    <strong>Hvis du har yderlige spørgsmål om din ordre eller tilbud, kan du altid kontakte support, via linket ovenover til Support Samtale.</strong>
                </p>
            </div>

        </section>
    </c:if>
    <section>
        <div class="container">
            <c:choose>
                <c:when test="${requestScope.order.offers.size() > 0}">
                    <table class="table">
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Pris</th>
                            <th scope="col">Dato</th>
                            <th scope="col"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${requestScope.order.offers}" var="offer" varStatus="iteration">
                            <tr class=" ${requestScope.order.hasAcceptedOffer() != null && requestScope.order.hasAcceptedOffer() == offer ? "active" : "" }">
                                <th class="align-middle" scope="row">${requestScope.order.offers.size() + 1 - iteration.count}</th>
                                <td class="align-middle">${Util.formatPrice(offer.price)}</td>
                                <td class="align-middle">${Util.formatDateTime(offer.createdAt)}</td>
                                <td class="align-middle text-right">
                                    <c:choose>
                                        <c:when test="${requestScope.order.hasAcceptedOffer() != null}">
                                            <c:if test="${requestScope.order.hasAcceptedOffer() != null && requestScope.order.hasAcceptedOffer() == offer}">
                                                <span class="badge badge-dyrhoi border" style="background-color:rgba(55, 228, 49, 0.2); border-color:rgba(55, 228, 49, 0.7) !important; font-size:1em;">Accepteret</span>
                                            </c:if>
                                        </c:when>
                                        <c:otherwise>
                                            <form method="post">
                                                <input name="array-id" value="${iteration.count - 1}" hidden>
                                                <button type="submit" class="btn btn-light">
                                                    Accepter tilbud
                                                </button>
                                            </form>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <p>Du har ikke modtaget nogen tilbud.</p>
                </c:otherwise>
            </c:choose>
        </div>
    </section>
</div>

