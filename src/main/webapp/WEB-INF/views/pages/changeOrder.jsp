<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Mathias
  Date: 13-12-2020
  Time: 15:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="domain.carport.Carport" %>
<%@ page import="domain.shed.Shed" %>
<div class="container">
    <form method="post" action="${pageContext.request.contextPath}/sales/orders/">
        <div class="col-8" id="orderInfo">
            <h3><small class="text-muted">#</small><c:out value="${requestScope.order.uuid}"/></h3>
            <p>Carport:
                <select name="carport-length">
                    <c:forEach var="i" begin="${Carport.minLength}" end="${Carport.maxLength}" step="30">
                        <c:choose>
                            <c:when test="${requestScope.order.carport.length == i}">
                                <option value="${i}" selected="selected"><c:out value="${i}"/> </option>
                            </c:when>
                            <c:otherwise>
                                <option value="${i}"><c:out value="${i}"/> </option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
                X
                <select name="carport-width">
                    <c:forEach var="i" begin="${Carport.minWidth}" end="${Carport.maxWidth}" step="30">
                        <c:choose>
                            <c:when test="${requestScope.order.carport.width == i}">
                                <option value="${i}" selected="selected"><c:out value="${i}"/> </option>
                            </c:when>
                            <c:otherwise>
                                <option value="${i}"><c:out value="${i}"/> </option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>
            </p>
            <p>Overdække:
                <select name="roof-type">
                    <c:choose>
                        <c:when test="${requestScope.order.carport.roof == 'FLAT'}">
                            <option selected="selected" value="FLAT">FLAT</option>
                            <option value="ANGLED">ANGLED</option>
                        </c:when>
                        <c:otherwise>
                            <option value="FLAT">FLAT</option>
                            <option selected="selected" value="ANGLED">ANGLED</option>
                        </c:otherwise>
                    </c:choose>
                </select>
            </p>
            <p>Tag vinkel:
                <c:choose>
                    <c:when test="${requestScope.order.carport.roofAngle == 0}">
                        N/A
                    </c:when>
                    <c:otherwise>
                        <select name="roof-angle">
                            <c:forEach var="i" begin="${Carport.minAngle}" end="${Carport.maxAngle}" step="5">
                                <c:choose>
                                    <c:when test="${requestScope.order.carport.roofAngle == i}">
                                        <option value="${i}" selected="selected"><c:out value="${i}"/></option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${i}"><c:out value="${i}"/> </option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </c:otherwise>
                </c:choose>
            </p>
            <p>Tagmateriale:
                    <c:choose>
                        <c:when test="${requestScope.order.carport.roof == 'FLAT'}">
                            <select class="form-control" id="roof_angled_material" name="roof_flat_material">
                                <option value="1" selected="selected"><c:out value="${requestScope.roof_material}"/> </option>
                            </select>
                        </c:when>
                        <c:otherwise>
                            <select name="roof_angled_material">
                                <c:forEach var="item" items="${requestScope.roofMaterials}" varStatus="loop">
                                    <c:choose>
                                        <c:when test="${item == requestScope.roof_material}">
                                            <option value="${loop.index}" selected="selected"><c:out value="${item}"/> </option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${loop.index}"><c:out value="${item}"/> </option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </c:otherwise>
                    </c:choose>
            </p>
            <p>Shed size:
                <c:choose>
                    <c:when test="${requestScope.order.shed.length != null}">
                        <select name="shed-length">
                            <c:forEach var="i" begin="${Shed.minLength}" end="${Shed.maxLength}" step="30">
                                <c:choose>
                                    <c:when test="${requestScope.order.shed.length == i}">
                                        <option value="${i}" selected="selected"><c:out value="${i}"/> </option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${i}"><c:out value="${i}"/> </option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                        X
                        <select name="shed-width">
                            <c:forEach var="i" begin="${Shed.minWidth}" end="${Shed.maxWidth}" step="30">
                                <c:choose>
                                    <c:when test="${requestScope.order.shed.width == i}">
                                        <option value="${i}" selected="selected"><c:out value="${i}"/> </option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${i}"><c:out value="${i}"/> </option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                        <button type="button">Fjern skur</button>
                    </c:when>
                    <c:otherwise>
                        N/A <button type="button">Tilføj skur</button>
                    </c:otherwise>
                </c:choose>
            </p>
            <button class="btn btn-success" type="submit">Gem</button>
            <button class="btn btn-danger" type="button">Annuller</button>
        </div>
    </form>
</div>
