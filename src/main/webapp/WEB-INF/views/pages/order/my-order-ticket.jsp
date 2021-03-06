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
            <h1>Support Samtale</h1>
            <p class="mb-0">
                <span class="badge badge-dyrhoi border" style="background-color:${requestScope.order.status.getColorRGBA(0.2)}; border-color:${requestScope.order.status.colorRGBA} !important;">${requestScope.order.status.name}</span>
                <strong>#${requestScope.order.uuid}</strong>.
                Oprettet ${Util.formatDateTime(requestScope.order.date)}
            </p>
        </div>
    </section>
    <section>
        <jsp:include page="/WEB-INF/views/pages/ticket/_slug.jsp" />
    </section>
</div>

