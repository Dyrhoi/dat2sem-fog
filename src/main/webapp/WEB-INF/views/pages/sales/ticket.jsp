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
<div>
    <section>
        <div class="container">
            <a id="arrow" href="${pageContext.request.contextPath}/sales/orders/${requestScope.order.uuid}"><span class="material-icons-outlined">arrow_back</span></a>
            <h1>Kunde samtale</h1>
            <p class="mb-0">
                Dette er samtalen med kunden.
            </p>
        </div>
    </section>
    <section>
        <jsp:include page="/WEB-INF/views/pages/ticket/_slug.jsp" />
    </section>
</div>
