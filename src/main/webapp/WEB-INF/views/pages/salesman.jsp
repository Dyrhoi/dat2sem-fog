<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Jack
  Date: 01/12/2020
  Time: 12:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container-xl">
    <section>
        <div class=>
            <h1>Sælger side</h1>
            <p class="lead">Her kan I slå carport ordre op, og finde specifikationer</p>
        </div>
    </section>
    <table class="table table-bordered">
        <thead class="thead-light">
        <tr>
            <th scope="col" class="text-nowrap">Ordre id</th>
            <th scope="col" class="text-nowrap">Navn</th>
            <th scope="col" class="text-nowrap">Email</th>
            <th scope="col" class="text-nowrap">Telefon</th>
            <th scope="col" class="text-nowrap">Dato</th>
            <th scope="col" class="text-nowrap">Sælger</th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${requestScope.orders != null}">
            <c:forEach var="order" items="${requestScope.orders}" varStatus="loop">
                <tr class="d-lg-table-row">
                    <th scope="row" class="text-nowrap user-id"><a href="${pageContext.request.contextPath}/salesman/orders/${order.uuid}"><c:out value="${order.uuid}"/></a></th>
                    <td class="text-nowrap"><c:out value="${order.customer.fullName}" /></td>
                    <td class="text-nowrap"><c:out value="${order.customer.email}" /></td>
                    <td class="text-nowrap"><c:out value="${order.customer.phone}" /></td>
                    <td class="text-nowrap"><c:out value="24/12-2020" /></td>
                    <td colspan="text-nowrap"><c:out value="Ikke tildelt"/></td>
                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>
</div>
