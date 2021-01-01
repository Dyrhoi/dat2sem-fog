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
            <h1>Carport Ordre <span class="badge badge-warning">Afventer</span></h1>
            <div class="row">
                <div class="col-sm">
                    <p>Ordre Nr.: <strong>#${requestScope.order.uuid}</strong></p>
                </div>
                <div class="col-sm">
                    <p>Ordre afgivet: <strong>#${Util.formatDateTime(requestScope.order.date)}</strong></p>
                </div>
            </div>
        </div>
    </section>
    <section class="bg-primary text-light" id="your_carport">
        <div class="container">
            <div class="row">
                <div class="col d-flex align-items-center">
                    <div>
                        <h2>Din carport</h2>
                        <p class="mb-0">Her kan du se din genereret carport plan-tegning.</p>
                    </div>
                </div>
                <div class="col">
                    <div id="svg-request-updater">
                        ${requestScope.svgCarportDrawing}
                    </div>
                </div>
            </div>
        </div>
    </section>
    <jsp:include page="/WEB-INF/views/pages/ticket/_slug.jsp" />
</div>
