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
            <h1>Tak for din ordre!</h1>
            <p>
                Dit ordrenummer er <b>#<c:out value="${requestScope.order.uuid}"/></b>.
                <br>
                Du kan se ordre status, samt få kundeservice via din private nøgle. <a href="<c:url value="/order/my-order/${requestScope.order.token}"/>">Klik her.</a>
            </p>
        </section>
    </div>
</div>