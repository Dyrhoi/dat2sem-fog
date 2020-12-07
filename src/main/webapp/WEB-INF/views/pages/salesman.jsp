<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Jack
  Date: 01/12/2020
  Time: 12:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">
    <section>
        <div class=>
            <h1>Sælger side</h1>
            <p class="lead">Her kan I slå carport ordre op, og finde specifikationer</p>
        </div>
    </section>
</div>
<table class="table table-bordered">
    <thead class="thead-light">
    <tr>
        <th scope="col" class="text-nowrap">Ordre id</th>
        <th scope="col" class="text-nowrap">Navn</th>
        <th scope="col" class="text-nowrap">Email</th>
        <th scope="col" class="text-nowrap">Telefon</th>
        <th scope="col" class="text-nowrap">Dato</th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${requestScope.orders != null}">
        <c:forEach var="user" items="${requestScope.orders}">
            <tr class="d-lg-table-row">
                <th scope="row" class="text-nowrap user-id"><c:out value="${order.id}" /></th>
                <td class="text-nowrap"><c:out value="${order.name}" /></td>
                <td class="text-nowrap"><c:out value="${order.email}" /></td>
                <td class="text-nowrap"><c:out value="${order.phone}" /></td>
                <td class="text-nowrap"><c:out value="${order.date}" /></td>
            </tr>
        </c:forEach>
    </c:if>
    </tbody>
</table>