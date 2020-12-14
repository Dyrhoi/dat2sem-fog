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
<%@ page import="domain.carport.Shed" %>
<div class="container" id="changeInfoContainer"
    data-shed-minLength="${Shed.minLength}"
    data-shed-maxLength="${Shed.maxLength}"
    data-shed-maxWidth="${Shed.maxWidth}"
    data-shed-minWidth="${Shed.minWidth}"
    data-carport-minAngle="${Carport.minAngle}"
    data-carport-maxAngle="${Carport.maxAngle}">
    <form method="post" action="${pageContext.request.contextPath}/sales/orders/">
        <div id="orderInfo">
            <h3><small class="text-muted">#</small><c:out value="${requestScope.order.uuid}"/></h3>
            <div id="carport" class="form-group">
                <label class="control-label" for="carport">Carport: </label>
                <select class="change-input" name="carport-length">
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
                <select class="change-input" name="carport-width">
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
            </div>
            <p>Overdække:
                <select name="roof-type" class="change-input-height" id="roof-type">
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
                <span id="angle">
                    <c:choose>
                        <c:when test="${requestScope.order.carport.roofAngle == 0}">
                            <span id="angle-text">N/A</span>
                        </c:when>
                        <c:otherwise>
                            <select name="roof-angle" class="change-input" id="roof-angle">
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
                </span>
            </p>
            <p>Tagmateriale:
                <span id="roof-material">
                    <c:choose>
                        <c:when test="${requestScope.order.carport.roof == 'FLAT'}">
                            <c:forEach var="item" items="${requestScope.roofMaterials}" varStatus="loop">
                                <c:if test="${item.type == 'FLAT'}">
                                    <c:choose>
                                        <c:when test="${item == requestScope.roof_material}">
                                            <option value="${loop.index}" selected="selected"><c:out value="${item.name}"/> </option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${loop.index}"><c:out value="${item.name}"/> </option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <select name="roof_angled_material" class="change-input-height" id="roof_angled_material">
                                <c:forEach var="item" items="${requestScope.roofMaterials}" varStatus="loop">
                                    <c:if test="${item.type == 'ANGLED'}">
                                        <c:choose>
                                            <c:when test="${item == requestScope.roof_material}">
                                                <option value="${loop.index}" selected="selected"><c:out value="${item.name}"/> </option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${loop.index}"><c:out value="${item.name}"/> </option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </c:otherwise>
                    </c:choose>
                </span>
            </p>
            <p>Shed size:
                <span id="shed">
                <c:choose>
                    <c:when test="${requestScope.order.shed.length != null}">
                        <select class="change-input" name="shed-length" id="shed-length">
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
                        <span id="shed-text">X</span>
                        <select class="change-input" name="shed-width" id="shed-width">
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
                        <button type="button" class="btn btn-outline-danger" id="removeShedBtn" onclick="removeShed()">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
                                <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"></path>
                                <path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4L4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"></path>
                            </svg>
                            Fjern skur
                        </button>
                    </c:when>
                    <c:otherwise>
                        <span id="shed-text">N/A</span>
                        <button type="button" class="btn btn-outline-success" id="addShedBtn" onclick="addShed()">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-plus-circle" viewBox="0 0 16 16">
                                <path fill-rule="evenodd" d="M8 15A7 7 0 1 0 8 1a7 7 0 0 0 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"></path>
                                <path fill-rule="evenodd" d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"></path>
                            </svg>
                            Tilføj skur
                        </button>
                    </c:otherwise>
                </c:choose>
                </span>
            </p>
            <button class="btn btn-success" type="submit">Gem</button>
            <button class="btn btn-danger" type="button">Annuller</button>
        </div>
    </form>
</div>