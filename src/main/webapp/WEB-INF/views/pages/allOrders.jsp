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
        <div>
            <c:choose>
                <c:when test="${requestScope.salesRepId != null}">
                    <h1>Dine sager</h1>
                </c:when>
                <c:otherwise>
                    <h1>Alle ordre</h1>
                </c:otherwise>
            </c:choose>
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
            <c:if test="${requestScope.salesRepId == null}">
                <th scope="col" class="text-nowrap">Sælger</th>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:if test="${requestScope.orders != null}">
            <c:choose>
                <c:when test="${requestScope.salesRepId != null}">
                    <c:forEach var="order" items="${requestScope.orders}" varStatus="loop">
                        <c:if test="${order.salesRepresentative.id == requestScope.salesRepId}">
                            <tr class="d-lg-table-row">
                            <th scope="row" class="text-nowrap user-id"><a href="${pageContext.request.contextPath}/sales/orders/${order.uuid}"><c:out value="${order.uuid}"/></a></th>
                            <td class="text-nowrap"><c:out value="${order.customer.fullName}" /></td>
                            <td class="text-nowrap"><c:out value="${order.customer.email}" /></td>
                            <td class="text-nowrap"><c:out value="${order.customer.phone}" /></td>
                            <td class="text-nowrap"><c:out value="${order.date}" /></td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <c:forEach var="order" items="${requestScope.orders}" varStatus="loop">
                        <tr class="d-lg-table-row">
                            <th scope="row" class="text-nowrap user-id"><a href="${pageContext.request.contextPath}/sales/orders/${order.uuid}"><c:out value="${order.uuid}"/></a></th>
                            <td class="text-nowrap"><c:out value="${order.customer.fullName}" /></td>
                            <td class="text-nowrap"><c:out value="${order.customer.email}" /></td>
                            <td class="text-nowrap"><c:out value="${order.customer.phone}" /></td>
                            <td class="text-nowrap"><c:out value="${order.date}" /></td>
                            <td colspan="text-nowrap">
                                <c:choose>
                                    <c:when test="${order.salesRepresentative == null}">
                                        <form method="post" action="${pageContext.request.contextPath}/sales/orders" id="rep-form">
                                            <a href="javascript:{}" onclick="document.getElementById('rep-form').submit(); return false;">Tag ordre</a>
                                            <input type="hidden" name="uuid" value="${order.uuid}"/>
                                            <input hidden name="action" value="update-salesrep">
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${order.salesRepresentative.fullName}"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </c:if>
        </tbody>
    </table>
</div>
