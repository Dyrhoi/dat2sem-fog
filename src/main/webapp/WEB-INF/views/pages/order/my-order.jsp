<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Windowshoi
  Date: 01/01/2021
  Time: 20.45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="api.Util" %>
<jsp:include page="/WEB-INF/views/includes/my-order/my-order-navigation.jsp" />
<div>
    <section>
        <div class="container">
            <h1>Ordre Information</h1>
            <p class="mb-0">
                <span class="badge badge-dyrhoi border" style="background-color:${requestScope.order.status.getColorRGBA(0.2)}; border-color:${requestScope.order.status.colorRGBA} !important;">${requestScope.order.status.name}</span>
                <strong>#${requestScope.order.uuid}</strong>.
                Oprettet ${Util.formatDateTime(requestScope.order.date)}
            </p>

        </div>
    </section>
    <section>
        <div class="container">
            <h3>Kunde</h3>
            <div class="row">
                <div class="col-sm">
                    <p>
                        <strong>Navn:</strong> ${requestScope.order.customer.fullName}
                        <br>
                        <br>
                        <strong>E-mail:</strong> ${requestScope.order.customer.email}
                        <br>
                        <strong>Tlf. nr.:</strong> ${requestScope.order.customer.phone}
                    </p>
                    <p class="mb-0">
                        <strong>Addresse:</strong> ${requestScope.order.customer.address.address}
                        <br>
                        <strong>Postnr.:</strong> ${requestScope.order.customer.address.postalCode}
                        <br>
                        <strong>By:</strong> ${requestScope.order.customer.address.city}
                    </p>
                </div>
            </div>
        </div>
    </section>
    <section class="bg-light" id="your_carport">
        <div class="container">
            <div class="row">
                <div class="col d-flex align-items-center">
                    <div>
                        <h2>Din carport</h2>
                        <p>
                            Carport: ${requestScope.order.carport.length} X ${requestScope.order.carport.width}
                            <br>
                            Overdække: <c:choose>
                                <c:when test="${requestScope.order.carport.roof == 'FLAT'}">
                                    Fladt tag.
                                </c:when>
                                <c:otherwise>
                                    Med rejsning.
                                </c:otherwise>
                            </c:choose>
                            <br>
                            Tagmateriale:
                            <br>
                            Skur:
                            <c:choose>
                                <c:when test="${requestScope.order.carport.shed != null}">
                                    ${requestScope.order.carport.shed.length} X ${requestScope.order.carport.shed.width}
                                </c:when>
                                <c:otherwise>
                                    N/A
                                </c:otherwise>
                            </c:choose>
                        </p>
                    </div>
                </div>
                <div class="col">
                    <div id="svg-request-updater">
                        ${requestScope.svgCarportDrawing}
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>
