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
            <a id="arrow" href="${pageContext.request.contextPath}/sales/orders">
                <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" class="bi bi-arrow-left" viewBox="0 0 16 16">
                    <path fill-rule="evenodd" d="M15 8a.5.5 0 0 0-.5-.5H2.707l3.147-3.146a.5.5 0 1 0-.708-.708l-4 4a.5.5 0 0 0 0 .708l4 4a.5.5 0 0 0 .708-.708L2.707 8.5H14.5A.5.5 0 0 0 15 8z"/>
                </svg>
            </a>
            <h3><small class="text-muted">#</small><c:out value="${requestScope.order.uuid}"/></h3>
            <a href=""><c:out value="${requestScope.order.token}"/> </a>
            <button class="btn btn-warning" type="button" onclick="location.href='${pageContext.request.contextPath}/sales/orders/${order.uuid}/#'">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-gear" viewBox="0 0 16 16">
                <path fill-rule="evenodd" d="M8.837 1.626c-.246-.835-1.428-.835-1.674 0l-.094.319A1.873 1.873 0 0 1 4.377 3.06l-.292-.16c-.764-.415-1.6.42-1.184 1.185l.159.292a1.873 1.873 0 0 1-1.115 2.692l-.319.094c-.835.246-.835 1.428 0 1.674l.319.094a1.873 1.873 0 0 1 1.115 2.693l-.16.291c-.415.764.42 1.6 1.185 1.184l.292-.159a1.873 1.873 0 0 1 2.692 1.116l.094.318c.246.835 1.428.835 1.674 0l.094-.319a1.873 1.873 0 0 1 2.693-1.115l.291.16c.764.415 1.6-.42 1.184-1.185l-.159-.291a1.873 1.873 0 0 1 1.116-2.693l.318-.094c.835-.246.835-1.428 0-1.674l-.319-.094a1.873 1.873 0 0 1-1.115-2.692l.16-.292c.415-.764-.42-1.6-1.185-1.184l-.291.159A1.873 1.873 0 0 1 8.93 1.945l-.094-.319zm-2.633-.283c.527-1.79 3.065-1.79 3.592 0l.094.319a.873.873 0 0 0 1.255.52l.292-.16c1.64-.892 3.434.901 2.54 2.541l-.159.292a.873.873 0 0 0 .52 1.255l.319.094c1.79.527 1.79 3.065 0 3.592l-.319.094a.873.873 0 0 0-.52 1.255l.16.292c.893 1.64-.902 3.434-2.541 2.54l-.292-.159a.873.873 0 0 0-1.255.52l-.094.319c-.527 1.79-3.065 1.79-3.592 0l-.094-.319a.873.873 0 0 0-1.255-.52l-.292.16c-1.64.893-3.433-.902-2.54-2.541l.159-.292a.873.873 0 0 0-.52-1.255l-.319-.094c-1.79-.527-1.79-3.065 0-3.592l.319-.094a.873.873 0 0 0 .52-1.255l-.16-.292c-.892-1.64.902-3.433 2.541-2.54l.292.159a.873.873 0 0 0 1.255-.52l.094-.319z"/>
                <path fill-rule="evenodd" d="M8 5.754a2.246 2.246 0 1 0 0 4.492 2.246 2.246 0 0 0 0-4.492zM4.754 8a3.246 3.246 0 1 1 6.492 0 3.246 3.246 0 0 1-6.492 0z"/>
                </svg>
                Ændre</button>
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
                <input type="button" id="submit-offer" value="Send tilbud">
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