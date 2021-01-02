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
                                            <a href="javascript:{}" onclick="document.getElementById('rep-form').submit(); return false;"><c:out value="Tag ordre"/></a>
                                            <input type="hidden" name="uuid" value="${order.uuid}"/>
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