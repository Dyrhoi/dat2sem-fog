<%@ taglib prefix="d" tagdir="/WEB-INF/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Windowshoi
  Date: 04/01/2021
  Time: 22.25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="my-order-nav navbar navbar-light navbar-expand-lg">
    <div class="container">
        <div class="border-bottom w-100">
            <ul class="navbar-nav">
                <li class="nav-item pl-0">
                    <d:router-link to="/order/my-order/${requestScope.order.token}" className="nav-link" exact="true">
                        <span class="material-icons-outlined">import_contacts</span>
                        Ordre information</d:router-link>
                </li>
                <li class="nav-item">
                    <d:router-link to="/order/my-order/${requestScope.order.token}/offers" className="nav-link">
                        <span class="material-icons-outlined">local_offer</span>
                        Mine Tilbud
                    </d:router-link>
                </li>
                <li class="nav-item">
                    <d:router-link to="/order/my-order/${requestScope.order.token}/ticket" className="nav-link">
                        <span class="material-icons-outlined">question_answer</span>
                        Support Samtale
                    </d:router-link>
                </li>
                <li class="nav-item" ${requestScope.order.status.id == 4 ? '' : 'data-toggle="tooltip" title="Din materialeliste bliver tilgængelig når ordren er betalt."'}>
                    <d:router-link to="/order/my-order/${requestScope.order.token}/material-list" className="nav-link ${requestScope.order.status.id == 4 ? '' : 'disabled'}">
                        <span class="material-icons-outlined">description</span>
                        Materialeliste
                    </d:router-link>
                </li>
            </ul>
        </div>
    </div>
</nav>
