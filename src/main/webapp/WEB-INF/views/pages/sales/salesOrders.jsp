<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Mathias
  Date: 07-12-2020
  Time: 18:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="api.Util" %>
<%@ page import="domain.order.Order" %>
<div class="container" id="orderInfo" data-evenOffer="${Order.evenOffer}"
     <c:choose>
         <c:when test="${requestScope.order.mostRecentOffer != null}">
             data-orderOffer="${requestScope.order.mostRecentOffer.price}"
         </c:when>
         <c:otherwise>
             data-orderOffer="0"
         </c:otherwise>
     </c:choose>
>
    <div class="row">
        <div class="col-8">
            <a id="arrow" href="${pageContext.request.contextPath}/sales/orders">
                <span class="material-icons-outlined">arrow_back</span>
            </a>
            <h3><small class="text-muted">#</small><c:out value="${requestScope.order.uuid}"/></h3>
            <button class="btn btn-warning" type="button"
                    onclick="location.href='${pageContext.request.contextPath}/sales/orders/${requestScope.order.uuid}/edit'">
                <div class="d-flex align-items-center">
                    <span class="material-icons-outlined mr-2">settings</span>
                    Ændre
                </div>
            </button>
            <br>
            <a href="<c:url value="/sales/orders/${requestScope.order.uuid}/ticket" />">Se kunde samtale.</a>
            <br>
            <a href="<c:url value="/order/my-order/${requestScope.order.token}" />">Se kundens ordre-side.</a>
            <br>
            <a target="_blank" href="<c:url value="/sales/orders/${requestScope.order.uuid}/material-list" />">Se materialeliste</a>
            <p>Carport: <c:out value="${requestScope.order.carport.length}"/> X <c:out
                    value="${requestScope.order.carport.width}"/></p>
            <p>Overdække: <c:out value="${requestScope.order.carport.roof}"/></p>
            <c:if test="${requestScope.order.carport.roofAngle != -1}">
                <p>Tag vinkel: <c:out value="${requestScope.order.carport.roofAngle}"/></p>
            </c:if>
            <p>Tagmateriale: <c:out value="${requestScope.roof_material}"/></p>
            <p>Shed size:
                <c:choose>
                    <c:when test="${requestScope.order.shed.length != null}">
                        <c:out value="${requestScope.order.shed.length}"/> X <c:out
                            value="${requestScope.order.shed.width}"/>
                    </c:when>
                    <c:otherwise>
                        N/A
                    </c:otherwise>
                </c:choose>
            </p>
            <form method="post" action="${pageContext.request.contextPath}/sales/orders/">
                <div class="d-flex justify-content-between">
                    <div class="input-group-append">
                        <c:choose>
                            <c:when test="${requestScope.order.mostRecentOffer != null}">
                                <input type="number" value="${requestScope.order.mostRecentOffer.price}" name="offer" id="offer">
                            </c:when>
                            <c:otherwise>
                                <input type="number" name="offer" id="offer">
                            </c:otherwise>
                        </c:choose>

                        <span class="input-group-text">kr</span>
                        <span class="input-group-text" id="profitPercentSpan"></span>
                    </div>
                    <div class="text-right">
                        <input hidden name="uuid" value="${requestScope.order.uuid}">
                        <input hidden name="action" value="update-offer">
                        <button class="btn btn-outline-success pull-right" type="submit" name="order-offer">Send
                            tilbud
                        </button>
                    </div>
                </div>
            </form>

            <form method="post" action="${pageContext.request.contextPath}/sales/orders/" class="mt-3">
                <div class="row">
                    <c:if test="${requestScope.order.hasAcceptedOffer() == null}">
                        <p class="alert-warning alert">Du kan ikke sætte en ordre til at være betalt, hvis kunden ikke har accepteret et tilbud.</p>
                    </c:if>
                    <div class="col">
                        <input hidden name="uuid" value="${requestScope.order.uuid}">
                        <input hidden name="action" value="mark-as-paid">
                        <input ${requestScope.order.hasAcceptedOffer() == null ? "disabled" : ""} id="is-paid" type="checkbox" ${requestScope.order.status.id == 4 ? "checked" : ""} name="is-paid">
                        <label for="is-paid">Er ordren betalt?</label>
                    </div>
                    <div class="col text-right">
                        <input ${requestScope.order.hasAcceptedOffer() == null ? "disabled" : ""} class="btn btn-light" type="submit" value="Opdater ordre">
                    </div>
                </div>
            </form>
        </div>
        <div class="bg-light col-4 float-right" id="customerInfo">
            <h4 class="mt-3">Brugerinfo</h4>
            <p>Kunde: <c:out value="${requestScope.order.customer.firstname}"/> <c:out
                    value="${requestScope.order.customer.lastname}"/></p>
            <p>Email: <c:out value="${requestScope.order.customer.email}"/></p>
            <p>Telefon: <c:out value="${requestScope.order.customer.phone}"/></p>
            <p>Adresse: <c:out value="${requestScope.order.customer.address.address}"/></p>
            <p>By: <c:out value="${requestScope.order.customer.address.city}"/></p>
            <p>Postnummer: <c:out value="${requestScope.order.customer.address.postalCode}"/></p>
        </div>
    </div>
    <section>
        <h3>Alle tilbud til denne ordre.</h3>
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
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <p>Der er ikke blevet lavet nogen tilbud.</p>
            </c:otherwise>
        </c:choose>
    </section>
</div>