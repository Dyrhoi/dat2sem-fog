<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Mathias
  Date: 07-12-2020
  Time: 18:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">
    <div class="row">
        <div class="col-8" id="orderInfo">
            <h3><small class="text-muted">#</small><c:out value="${requestScope.order.uuid}"/></h3>
            <a href=""><c:out value="${requestScope.order.token}"/> </a>
            <p>Carport: <c:out value="${requestScope.order.carport.length}"/> x <c:out value="${requestScope.order.carport.width}"/></p>
            <p>Overdække: <c:out value="${requestScope.order.carport.roof}"/></p>
            <p>Tagmateriale: <c:out value="${requestScope.roof_material}"/></p>
            <c:choose>
                <c:when test="${requestScope.order.shed.length != null}">
                    <p>Shed size: <c:out value="${requestScope.order.shed.length}"/> x <c:out value="${requestScope.order.shed.width}"/></p>
                </c:when>
                <c:otherwise>
                    <p>Shed size: N/A</p>
                </c:otherwise>
            </c:choose>
            <form>
                <div class="input-group">
                    <input type="number" value="${requestScope.prePrice}">
                    <div class="input-group-append">
                        <span class="input-group-text">kr</span>
                    </div>
                </div>
                <input type="button" value="Send tilbud">
            </form>
        </div>
        <div class="bg-light col-4 float-right" id="customerInfo">
            <p><h4>Brugerinfo</h4></p>
            <p>Kunde: <c:out value="${order.customer.firstname}"/> <c:out value="${order.customer.lastname}"/></p>
            <p>Email: <c:out value="${order.customer.email}"/></p>
            <p>Telefon: <c:out value="${order.customer.phone}"/></p>
            <p>Adresse: <c:out value="${order.customer.address.address}"/></p>
            <p>By: <c:out value="${order.customer.address.city}"/></p>
            <p>Postnummer: <c:out value="${order.customer.address.postalCode}"/></p>
        </div>
    </div>
</div>