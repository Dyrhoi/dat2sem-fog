<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Mathias
  Date: 07-12-2020
  Time: 18:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container-lg d-flex">
    <div class="customer bg-light">
        <p>
            <h4>Brugerinfo</h4>
        </p>
        <p>
            Kunde: <c:out value="${order.customer.firstname}"/> <c:out value="${order.customer.lastname}"/>
        </p>
        <p>
            Email: <c:out value="${order.customer.email}"/>
        </p>
        <p>
            Telefon: <c:out value="${order.customer.phone}"/>
        </p>
        <p>
            Adresse: <c:out value="${order.customer.address.address}"/>
        </p>
        <p>
            By: <c:out value="${order.customer.address.city}"/>
        </p>
        <p>
            Postnummer: <c:out value="${order.customer.address.postalCode}"/>
        </p>
    </div>
</div>